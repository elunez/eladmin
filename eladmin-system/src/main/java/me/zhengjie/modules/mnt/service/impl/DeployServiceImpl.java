package me.zhengjie.modules.mnt.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.mnt.domain.App;
import me.zhengjie.modules.mnt.domain.Deploy;
import me.zhengjie.modules.mnt.domain.DeployHistory;
import me.zhengjie.modules.mnt.domain.ServerDeploy;
import me.zhengjie.modules.mnt.repository.DeployRepository;
import me.zhengjie.modules.mnt.service.*;
import me.zhengjie.modules.mnt.service.dto.*;
import me.zhengjie.modules.mnt.service.mapper.DeployMapper;
import me.zhengjie.modules.mnt.util.ExecuteShellUtil;
import me.zhengjie.modules.mnt.util.ScpClientUtil;
import me.zhengjie.modules.mnt.websocket.MsgType;
import me.zhengjie.modules.mnt.websocket.SocketMsg;
import me.zhengjie.modules.mnt.websocket.WebSocketServer;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeployServiceImpl implements DeployService {

	private final String FILE_SEPARATOR = File.separatorChar + "";

	private final DeployRepository deployRepository;

	private final DeployMapper deployMapper;

	private final ServerDeployService serverDeployService;

	private final DeployHistoryService deployHistoryService;

	public DeployServiceImpl(DeployRepository deployRepository, DeployMapper deployMapper, ServerDeployService serverDeployService, DeployHistoryService deployHistoryService) {
		this.deployRepository = deployRepository;
		this.deployMapper = deployMapper;
		this.serverDeployService = serverDeployService;
		this.deployHistoryService = deployHistoryService;
	}


	@Override
	public Object queryAll(DeployQueryCriteria criteria, Pageable pageable) {
		Page<Deploy> page = deployRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
		return PageUtil.toPage(page.map(deployMapper::toDto));
	}

	@Override
	public List<DeployDto> queryAll(DeployQueryCriteria criteria) {
		return deployMapper.toDto(deployRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
	}

	@Override
	public DeployDto findById(Long id) {
		Deploy deploy = deployRepository.findById(id).orElseGet(Deploy::new);
		ValidationUtil.isNull(deploy.getId(), "Deploy", "id", id);
		return deployMapper.toDto(deploy);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DeployDto create(Deploy resources) {
		return deployMapper.toDto(deployRepository.save(resources));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(Deploy resources) {
		Deploy deploy = deployRepository.findById(resources.getId()).orElseGet(Deploy::new);
		ValidationUtil.isNull(deploy.getId(), "Deploy", "id", resources.getId());
		deploy.copy(resources);
		deployRepository.save(deploy);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(Long id) {
		deployRepository.deleteById(id);
	}

	@Override
	public void deploy(String fileSavePath, Long id) {
		deployApp(fileSavePath, id);
	}

	/**
	 * @param fileSavePath 本机路径
	 * @param id ID
	 * @return string
	 */
	private String deployApp(String fileSavePath, Long id) {

		DeployDto deploy = findById(id);
		if (deploy == null) {
			sendMsg("部署信息不存在", MsgType.ERROR);
			throw new BadRequestException("部署信息不存在");
		}
		AppDto app = deploy.getApp();
		if (app == null) {
			sendMsg("包对应应用信息不存在", MsgType.ERROR);
			throw new BadRequestException("包对应应用信息不存在");
		}
		int port = app.getPort();
		//这个是服务器部署路径
		String uploadPath = app.getUploadPath();
		StringBuilder sb = new StringBuilder();
		String msg;
		Set<ServerDeployDto> deploys = deploy.getDeploys();
		for (ServerDeployDto deployDTO : deploys) {
			String ip = deployDTO.getIp();
			ExecuteShellUtil executeShellUtil = getExecuteShellUtil(ip);
			//判断是否第一次部署
			boolean flag = checkFile(executeShellUtil, app);
			//第一步要确认服务器上有这个目录
			executeShellUtil.execute("mkdir -p " + uploadPath);
			//上传文件
			msg = String.format("登陆到服务器:%s", ip);
			ScpClientUtil scpClientUtil = getScpClientUtil(ip);
			log.info(msg);
			sendMsg(msg, MsgType.INFO);
			msg = String.format("上传文件到服务器:%s<br>目录:%s下", ip, uploadPath);
			sendMsg(msg, MsgType.INFO);
			scpClientUtil.putFile(fileSavePath, uploadPath);
			if (flag) {
				sendMsg("停止原来应用", MsgType.INFO);
				//停止应用
				stopApp(port, executeShellUtil);
				sendMsg("备份原来应用", MsgType.INFO);
				//备份应用
				backupApp(executeShellUtil, ip, app.getDeployPath(), app.getName(), app.getBackupPath(), id);
			}
			sendMsg("部署应用", MsgType.INFO);
			//部署文件,并启动应用
			String deployScript = app.getDeployScript();
			executeShellUtil.execute(deployScript);

			sendMsg("启动应用", MsgType.INFO);
			String startScript = app.getStartScript();
			executeShellUtil.execute(startScript);
			//只有过5秒才能知道到底是不是启动成功了。
			sleep(5);
			boolean result = checkIsRunningStatus(port, executeShellUtil);
			sb.append("服务器:").append(deployDTO.getName()).append("<br>应用:").append(app.getName());
			sendResultMsg(result, sb);
		}
		return "部署结束";
	}

	private void sleep(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void backupApp(ExecuteShellUtil executeShellUtil, String ip, String fileSavePath, String appName, String backupPath, Long id) {
		String deployDate = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN);
		StringBuilder sb = new StringBuilder();
		if (!backupPath.endsWith(FILE_SEPARATOR)) {
			backupPath += FILE_SEPARATOR;
		}
		backupPath += appName + FILE_SEPARATOR + deployDate + "\n";
		sb.append("mkdir -p ").append(backupPath);
		sb.append("mv -f ").append(fileSavePath);
		if (!fileSavePath.endsWith(FILE_SEPARATOR)) {
			sb.append(FILE_SEPARATOR);
		}
		sb.append(appName).append(" ").append(backupPath);
		log.info("备份应用脚本:" + sb.toString());
		executeShellUtil.execute(sb.toString());
		//还原信息入库
		DeployHistory deployHistory = new DeployHistory();
		deployHistory.setAppName(appName);
		deployHistory.setDeployUser(SecurityUtils.getUsername());
		deployHistory.setIp(ip);
		deployHistory.setDeployId(id);
		deployHistoryService.create(deployHistory);
	}

	/**
	 * 停App
	 *
	 * @param port 端口
	 * @param executeShellUtil /
	 */
	private void stopApp(int port, ExecuteShellUtil executeShellUtil) {
		//发送停止命令
		executeShellUtil.execute(String.format("lsof -i :%d|grep -v \"PID\"|awk '{print \"kill -9\",$2}'|sh", port));

	}

	/**
	 * 指定端口程序是否在运行
	 *
	 * @param port 端口
	 * @param executeShellUtil /
	 * @return true 正在运行  false 已经停止
	 */
	private boolean checkIsRunningStatus(int port, ExecuteShellUtil executeShellUtil) {
		String statusResult = executeShellUtil.executeForResult(String.format("fuser -n tcp %d", port));
		return !"".equals(statusResult.trim());
	}

	private void sendMsg(String msg, MsgType msgType) {
		try {
			WebSocketServer.sendInfo(new SocketMsg(msg, msgType), "deploy");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String serverStatus(Deploy resources) {
		Set<ServerDeploy> serverDeploys = resources.getDeploys();
		App app = resources.getApp();
		for (ServerDeploy serverDeploy : serverDeploys) {
			StringBuilder sb = new StringBuilder();
			ExecuteShellUtil executeShellUtil = getExecuteShellUtil(serverDeploy.getIp());
			sb.append("服务器:").append(serverDeploy.getName()).append("<br>应用:").append(app.getName());
			boolean result = checkIsRunningStatus(app.getPort(), executeShellUtil);
			if (result) {
				sb.append("<br>正在运行");
				sendMsg(sb.toString(), MsgType.INFO);
			} else {
				sb.append("<br>已停止!");
				sendMsg(sb.toString(), MsgType.ERROR);
			}
			log.info(sb.toString());
		}
		return "执行完毕";
	}

	private boolean checkFile(ExecuteShellUtil executeShellUtil, AppDto appDTO) {
		String sb = "find " +
				appDTO.getDeployPath() +
				" -name " +
				appDTO.getName();
		return executeShellUtil.executeShell(sb);
	}

	/**
	 * 启动服务
	 * @param resources /
	 * @return /
	 */
	@Override
	public String startServer(Deploy resources) {
		Set<ServerDeploy> deploys = resources.getDeploys();
		App app = resources.getApp();
		for (ServerDeploy deploy : deploys) {
			StringBuilder sb = new StringBuilder();
			ExecuteShellUtil executeShellUtil = getExecuteShellUtil(deploy.getIp());
			//为了防止重复启动，这里先停止应用
			stopApp(app.getPort(), executeShellUtil);
			sb.append("服务器:").append(deploy.getName()).append("<br>应用:").append(app.getName());
			sendMsg("下发启动命令", MsgType.INFO);
			executeShellUtil.execute(app.getStartScript());
			//停止3秒，防止应用没有启动完成
			sleep(3);
			boolean result = checkIsRunningStatus(app.getPort(), executeShellUtil);
			sendResultMsg(result, sb);
			log.info(sb.toString());
		}
		return "执行完毕";
	}

	/**
	 * 停止服务
	 * @param resources /
	 * @return /
	 */
	@Override
	public String stopServer(Deploy resources) {
		Set<ServerDeploy> deploys = resources.getDeploys();
		App app = resources.getApp();
		for (ServerDeploy deploy : deploys) {
			StringBuilder sb = new StringBuilder();
			ExecuteShellUtil executeShellUtil = getExecuteShellUtil(deploy.getIp());
			sb.append("服务器:").append(deploy.getName()).append("<br>应用:").append(app.getName());
			sendMsg("下发停止命令", MsgType.INFO);
			//停止应用
			stopApp(app.getPort(), executeShellUtil);
			sleep(1);
			boolean result = checkIsRunningStatus(app.getPort(), executeShellUtil);
			if (result) {
				sb.append("<br>关闭失败!");
				sendMsg(sb.toString(), MsgType.ERROR);
			} else {
				sb.append("<br>关闭成功!");
				sendMsg(sb.toString(), MsgType.INFO);
			}
			log.info(sb.toString());
		}
		return "执行完毕";
	}

	@Override
	public String serverReduction(DeployHistory resources) {
		Long deployId = resources.getDeployId();
		Deploy deployInfo = deployRepository.findById(deployId).orElseGet(Deploy::new);
		Timestamp deployDate = resources.getDeployDate();
		App app = deployInfo.getApp();
		if (app == null) {
			sendMsg("应用信息不存在：" + resources.getAppName(), MsgType.ERROR);
			throw new BadRequestException("应用信息不存在：" + resources.getAppName());
		}
		String backupPath = app.getBackupPath();
		if (!backupPath.endsWith(FILE_SEPARATOR)) {
			backupPath += FILE_SEPARATOR;
		}
		backupPath += resources.getAppName() + FILE_SEPARATOR + deployDate;
		//这个是服务器部署路径
		String deployPath = app.getDeployPath();
		String ip = resources.getIp();
		ExecuteShellUtil executeShellUtil = getExecuteShellUtil(ip);
		String msg;

		msg = String.format("登陆到服务器:%s", ip);
		log.info(msg);
		sendMsg(msg, MsgType.INFO);
		sendMsg("停止原来应用", MsgType.INFO);
		//停止应用
		stopApp(app.getPort(), executeShellUtil);
		//删除原来应用
		sendMsg("删除应用", MsgType.INFO);
		//考虑到系统安全性，必须限制下操作目录
		String path = "/opt";
		if (!deployPath.startsWith(path)) {
			throw new BadRequestException("部署路径必须在opt目录下：" + deployPath);
		}
		executeShellUtil.execute("rm -rf " + deployPath + FILE_SEPARATOR + resources.getAppName());

		//还原应用
		sendMsg("还原应用", MsgType.INFO);
		executeShellUtil.execute("cp -r " + backupPath + "/. " + deployPath);
		sendMsg("启动应用", MsgType.INFO);
		executeShellUtil.execute(app.getStartScript());
		//只有过5秒才能知道到底是不是启动成功了。
		sleep(5);
		boolean result = checkIsRunningStatus(app.getPort(), executeShellUtil);
		StringBuilder sb = new StringBuilder();
		sb.append("服务器:").append(ip).append("<br>应用:").append(resources.getAppName());
		sendResultMsg(result, sb);
		return "";
	}

	private ExecuteShellUtil getExecuteShellUtil(String ip) {
		ServerDeployDto serverDeployDTO = serverDeployService.findByIp(ip);
		if (serverDeployDTO == null) {
			sendMsg("IP对应服务器信息不存在：" + ip, MsgType.ERROR);
			throw new BadRequestException("IP对应服务器信息不存在：" + ip);
		}
		return new ExecuteShellUtil(ip, serverDeployDTO.getAccount(), serverDeployDTO.getPassword());
	}

	private ScpClientUtil getScpClientUtil(String ip) {
		ServerDeployDto serverDeployDTO = serverDeployService.findByIp(ip);
		if (serverDeployDTO == null) {
			sendMsg("IP对应服务器信息不存在：" + ip, MsgType.ERROR);
			throw new BadRequestException("IP对应服务器信息不存在：" + ip);
		}
		return ScpClientUtil.getInstance(ip, serverDeployDTO.getPort(), serverDeployDTO.getAccount(), serverDeployDTO.getPassword());
	}

	private void sendResultMsg(boolean result, StringBuilder sb) {
		if (result) {
			sb.append("<br>启动成功!");
			sendMsg(sb.toString(), MsgType.INFO);
		} else {
			sb.append("<br>启动失败!");
			sendMsg(sb.toString(), MsgType.ERROR);
		}
	}
}

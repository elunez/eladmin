/*
 *  Copyright 2019-2025 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.modules.maint.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.maint.domain.App;
import me.zhengjie.modules.maint.domain.Deploy;
import me.zhengjie.modules.maint.domain.DeployHistory;
import me.zhengjie.modules.maint.domain.ServerDeploy;
import me.zhengjie.modules.maint.repository.DeployRepository;
import me.zhengjie.modules.maint.service.DeployHistoryService;
import me.zhengjie.modules.maint.service.DeployService;
import me.zhengjie.modules.maint.service.ServerDeployService;
import me.zhengjie.modules.maint.service.dto.AppDto;
import me.zhengjie.modules.maint.service.dto.DeployDto;
import me.zhengjie.modules.maint.service.dto.DeployQueryCriteria;
import me.zhengjie.modules.maint.service.dto.ServerDeployDto;
import me.zhengjie.modules.maint.service.mapstruct.DeployMapper;
import me.zhengjie.modules.maint.util.ExecuteShellUtil;
import me.zhengjie.modules.maint.util.ScpClientUtil;
import me.zhengjie.modules.maint.websocket.MsgType;
import me.zhengjie.modules.maint.websocket.SocketMsg;
import me.zhengjie.modules.maint.websocket.WebSocketServer;
import me.zhengjie.utils.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeployServiceImpl implements DeployService {

	private final String FILE_SEPARATOR = "/";
	private final DeployRepository deployRepository;
	private final DeployMapper deployMapper;
	private final ServerDeployService serverDeployService;
	private final DeployHistoryService deployHistoryService;
	/**
	 * 循环次数
	 */
	private final Integer count = 30;


	@Override
	public PageResult<DeployDto> queryAll(DeployQueryCriteria criteria, Pageable pageable) {
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
	public void create(Deploy resources) {
		deployRepository.save(resources);
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
	public void delete(Set<Long> ids) {
		for (Long id : ids) {
			deployRepository.deleteById(id);
		}
	}

	@Override
	public void deploy(String fileSavePath, Long id) {
		deployApp(fileSavePath, id);
	}

	/**
	 * @param fileSavePath 本机路径
	 * @param id ID
	 */
	private void deployApp(String fileSavePath, Long id) {

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
			executeShellUtil.execute("mkdir -p " + app.getUploadPath());
			executeShellUtil.execute("mkdir -p " + app.getBackupPath());
			executeShellUtil.execute("mkdir -p " + app.getDeployPath());
			//上传文件
			msg = String.format("登陆到服务器:%s", ip);
			ScpClientUtil scpClientUtil = getScpClientUtil(ip);
			log.info(msg);
			sendMsg(msg, MsgType.INFO);
			msg = String.format("上传文件到服务器:%s<br>目录:%s下，请稍等...", ip, uploadPath);
			sendMsg(msg, MsgType.INFO);
			scpClientUtil.putFile(fileSavePath, uploadPath);
			if (flag) {
				sendMsg("停止原来应用", MsgType.INFO);
				//停止应用
				stopApp(port, executeShellUtil);
				sendMsg("备份原来应用", MsgType.INFO);
				//备份应用
				backupApp(executeShellUtil, ip, app.getDeployPath()+FILE_SEPARATOR, app.getName(), app.getBackupPath()+FILE_SEPARATOR, id);
			}
			sendMsg("部署应用", MsgType.INFO);
			//部署文件,并启动应用
			String deployScript = app.getDeployScript();
			executeShellUtil.execute(deployScript);
			sleep(3);
			sendMsg("应用部署中，请耐心等待部署结果，或者稍后手动查看部署状态", MsgType.INFO);
			int i  = 0;
			boolean result = false;
			// 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
			while (i++ < count){
				result = checkIsRunningStatus(port, executeShellUtil);
				if(result){
					break;
				}
				// 休眠6秒
				sleep(6);
			}
			sb.append("服务器:").append(deployDTO.getName()).append("<br>应用:").append(app.getName());
			sendResultMsg(result, sb);
			executeShellUtil.close();
		}
	}

	private void sleep(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			log.error(e.getMessage(),e);
		}
	}

	private void backupApp(ExecuteShellUtil executeShellUtil, String ip, String fileSavePath, String appName, String backupPath, Long id) {
		String deployDate = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN);
		StringBuilder sb = new StringBuilder();
		backupPath += appName + FILE_SEPARATOR + deployDate + "\n";
		sb.append("mkdir -p ").append(backupPath);
		sb.append("mv -f ").append(fileSavePath);
		sb.append(appName).append(" ").append(backupPath);
		log.info("备份应用脚本:" + sb.toString());
		executeShellUtil.execute(sb.toString());
		//还原信息入库
		DeployHistory deployHistory = new DeployHistory();
		deployHistory.setAppName(appName);
		deployHistory.setDeployUser(SecurityUtils.getCurrentUsername());
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
		String result = executeShellUtil.executeForResult(String.format("fuser -n tcp %d", port));
		return result.indexOf("/tcp:")>0;
	}

	private void sendMsg(String msg, MsgType msgType) {
		try {
			WebSocketServer.sendInfo(new SocketMsg(msg, msgType), "deploy");
		} catch (IOException e) {
			log.error(e.getMessage(),e);
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
			executeShellUtil.close();
		}
		return "执行完毕";
	}

	private boolean checkFile(ExecuteShellUtil executeShellUtil, AppDto app) {
		String deployPath = app.getDeployPath();
		String appName = app.getName();
		// 使用安全的命令执行方式，避免直接拼接字符串，https://github.com/elunez/eladmin/issues/873
		String[] command = {"find", deployPath, "-name", appName};
		String result = executeShellUtil.executeForResult(Arrays.toString(command));
		return result.contains(appName);
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
			sleep(3);
			sendMsg("应用启动中，请耐心等待启动结果，或者稍后手动查看运行状态", MsgType.INFO);
			int i  = 0;
			boolean result = false;
			// 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
			while (i++ < count){
				result = checkIsRunningStatus(app.getPort(), executeShellUtil);
				if(result){
					break;
				}
				// 休眠6秒
				sleep(6);
			}
			sendResultMsg(result, sb);
			log.info(sb.toString());
			executeShellUtil.close();
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
			executeShellUtil.close();
		}
		return "执行完毕";
	}

	@Override
	public String serverReduction(DeployHistory resources) {
		Long deployId = resources.getDeployId();
		Deploy deployInfo = deployRepository.findById(deployId).orElseGet(Deploy::new);
		String deployDate = DateUtil.format(resources.getDeployDate(), DatePattern.PURE_DATETIME_PATTERN);
		App app = deployInfo.getApp();
		if (app == null) {
			sendMsg("应用信息不存在：" + resources.getAppName(), MsgType.ERROR);
			throw new BadRequestException("应用信息不存在：" + resources.getAppName());
		}
		String backupPath = app.getBackupPath()+FILE_SEPARATOR;
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
		executeShellUtil.execute("rm -rf " + deployPath + FILE_SEPARATOR + resources.getAppName());
		//还原应用
		sendMsg("还原应用", MsgType.INFO);
		executeShellUtil.execute("cp -r " + backupPath + "/. " + deployPath);
		sendMsg("启动应用", MsgType.INFO);
		executeShellUtil.execute(app.getStartScript());
		sendMsg("应用启动中，请耐心等待启动结果，或者稍后手动查看启动状态", MsgType.INFO);
		int i  = 0;
		boolean result = false;
		// 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
		while (i++ < count){
			result = checkIsRunningStatus(app.getPort(), executeShellUtil);
			if(result){
				break;
			}
			// 休眠6秒
			sleep(6);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("服务器:").append(ip).append("<br>应用:").append(resources.getAppName());
		sendResultMsg(result, sb);
		executeShellUtil.close();
		return "";
	}

	private ExecuteShellUtil getExecuteShellUtil(String ip) {
		ServerDeployDto serverDeployDTO = serverDeployService.findByIp(ip);
		if (serverDeployDTO == null) {
			sendMsg("IP对应服务器信息不存在：" + ip, MsgType.ERROR);
			throw new BadRequestException("IP对应服务器信息不存在：" + ip);
		}
		return new ExecuteShellUtil(ip, serverDeployDTO.getAccount(), serverDeployDTO.getPassword(),serverDeployDTO.getPort());
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

	@Override
	public void download(List<DeployDto> queryAll, HttpServletResponse response) throws IOException {
		List<Map<String, Object>> list = new ArrayList<>();
		for (DeployDto deployDto : queryAll) {
			Map<String,Object> map = new LinkedHashMap<>();
			map.put("应用名称", deployDto.getApp().getName());
			map.put("服务器", deployDto.getServers());
			map.put("部署日期", deployDto.getCreateTime());
			list.add(map);
		}
		FileUtil.downloadExcel(list, response);
	}
}

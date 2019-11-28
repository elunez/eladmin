package me.zhengjie.modules.mnt.util;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * 执行shell命令
 * @author: ZhangHouYing
 * @date: 2019/8/10
 */
@Slf4j
public class ExecuteShellUtil {

	private String ipAddress;

	private String username;

	private String password;

	public final int DEFAULT_SSH_PORT = 22;

	private Vector<String> stdout;

	public ExecuteShellUtil(final String ipAddress, final String username, final String password) {
		this.ipAddress = ipAddress;
		this.username = username;
		this.password = password;
		stdout = new Vector<String>();
	}

	public int execute(final String command) {
		int returnCode = 0;
		JSch jsch = new JSch();
		try {
			Session session = jsch.getSession(username, ipAddress, DEFAULT_SSH_PORT);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect(30000);

			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			channel.setInputStream(null);
			BufferedReader input = new BufferedReader(new InputStreamReader(channel.getInputStream()));

			channel.connect();
			log.info("The remote command is: " + command);

			String line;
			while ((line = input.readLine()) != null) {
				stdout.add(line);
			}
			input.close();

			if (channel.isClosed()) {
				returnCode = channel.getExitStatus();
			}
			channel.disconnect();
			session.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnCode;
	}

	public boolean executeShell(String command) {
		int result = execute(command);
		for (String str : stdout) {
			log.info(str);
		}
		if (result == 0) {
			return true;
		} else {
			return false;
		}
	}
	public String executeForResult(String command) {
		execute(command);
		StringBuilder sb = new StringBuilder();
		for (String str : stdout) {
			sb.append(str);
			log.info(str);
		}
		return sb.toString();
	}

	/**
	 * 返回值有三条就代表成功了，2条是没启动，多余三条代表脚本有问题
	 * @param command
	 * @return
	 */
	public int checkAppStatus(String command) {
		execute(command);
		for (String str : stdout) {
			log.info(str);
		}
		return stdout.size();
	}

}

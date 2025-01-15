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
package me.zhengjie.modules.maint.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.maint.domain.enums.DataTypeEnum;
import me.zhengjie.utils.CloseUtil;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author /
 */
@Slf4j
public class SqlUtils {

	/**
	 * 获取数据源
	 *
	 * @param jdbcUrl /
	 * @param userName /
	 * @param password /
	 * @return DataSource
	 */
	private static DataSource getDataSource(String jdbcUrl, String userName, String password) {
		DruidDataSource druidDataSource = new DruidDataSource();
		String className;
		try {
			className = DriverManager.getDriver(jdbcUrl.trim()).getClass().getName();
		} catch (SQLException e) {
			throw new RuntimeException("Get class name error: =" + jdbcUrl);
		}
		if (StringUtils.isEmpty(className)) {
			DataTypeEnum dataTypeEnum = DataTypeEnum.urlOf(jdbcUrl);
			if (null == dataTypeEnum) {
				throw new RuntimeException("Not supported data type: jdbcUrl=" + jdbcUrl);
			}
			druidDataSource.setDriverClassName(dataTypeEnum.getDriver());
		} else {
			druidDataSource.setDriverClassName(className);
		}

		// 去掉不安全的参数
		jdbcUrl = sanitizeJdbcUrl(jdbcUrl);

		druidDataSource.setUrl(jdbcUrl);
		druidDataSource.setUsername(userName);
		druidDataSource.setPassword(password);
		// 配置获取连接等待超时的时间
		druidDataSource.setMaxWait(3000);
		// 配置初始化大小、最小、最大
		druidDataSource.setInitialSize(1);
		druidDataSource.setMinIdle(1);
		druidDataSource.setMaxActive(1);

		// 如果链接出现异常则直接判定为失败而不是一直重试
		druidDataSource.setBreakAfterAcquireFailure(true);
		try {
			druidDataSource.init();
		} catch (SQLException e) {
			log.error("Exception during pool initialization", e);
			throw new RuntimeException(e.getMessage());
		}

		return druidDataSource;
	}

	private static Connection getConnection(String jdbcUrl, String userName, String password) {
		DataSource dataSource = getDataSource(jdbcUrl, userName, password);
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (Exception ignored) {}
		try {
			int timeOut = 5;
			if (null == connection || connection.isClosed() || !connection.isValid(timeOut)) {
				log.info("connection is closed or invalid, retry get connection!");
				connection = dataSource.getConnection();
			}
		} catch (Exception e) {
			log.error("create connection error, jdbcUrl: {}", jdbcUrl);
			throw new RuntimeException("create connection error, jdbcUrl: " + jdbcUrl);
		} finally {
			CloseUtil.close(connection);
		}
		return connection;
	}

	private static void releaseConnection(Connection connection) {
		if (null != connection) {
			try {
				connection.close();
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
	}

	public static boolean testConnection(String jdbcUrl, String userName, String password) {
		Connection connection = null;
		try {
			connection = getConnection(jdbcUrl, userName, password);
			if (null != connection) {
				return true;
			}
		} catch (Exception e) {
            log.error("Get connection failed:{}", e.getMessage());
		} finally {
			releaseConnection(connection);
		}
		return false;
	}

	public static String executeFile(String jdbcUrl, String userName, String password, File sqlFile) {
		Connection connection = getConnection(jdbcUrl, userName, password);
		try {
			batchExecute(connection, readSqlList(sqlFile));
		} catch (Exception e) {
			log.error("sql脚本执行发生异常:{}",e.getMessage());
			return e.getMessage();
		}finally {
			releaseConnection(connection);
		}
		return "success";
	}

	/**
	 * 批量执行sql
	 * @param connection /
	 * @param sqlList /
	 */
	public static void batchExecute(Connection connection, List<String> sqlList) {
		try (Statement st = connection.createStatement()) {
			for (String sql : sqlList) {
				// 去除末尾的分号
				if (sql.endsWith(";")) {
					sql = sql.substring(0, sql.length() - 1);
				}
				// 检查 SQL 语句是否为空
				if (!sql.trim().isEmpty()) {
					st.addBatch(sql);
				}
			}
			st.executeBatch();
		} catch (SQLException e) {
			log.error("SQL脚本批量执行发生异常: {}，错误代码: {}", e.getMessage(), e.getErrorCode());
		}
	}

	/**
	 * 将文件中的sql语句以；为单位读取到列表中
	 * @param sqlFile /
	 * @return /
     */
	private static List<String> readSqlList(File sqlFile) {
		List<String> sqlList = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = Files.newBufferedReader(sqlFile.toPath(), StandardCharsets.UTF_8)) {
			String line;
			while ((line = reader.readLine()) != null) {
				log.info("line: {}", line);
				sb.append(line.trim());

				if (line.trim().endsWith(";")) {
					sqlList.add(sb.toString());
					// 清空 StringBuilder
					sb.setLength(0);
				} else {
					// 在行之间加一个空格
					sb.append(" ");
				}
			}
			if (sb.length() > 0) {
				sqlList.add(sb.toString().trim());
			}
		} catch (Exception e) {
			log.error("读取SQL文件时发生异常: {}", e.getMessage());
		}
		return sqlList;
	}

	/**
	 * 去除不安全的参数
	 * @param jdbcUrl /
	 * @return /
	 */
	private static String sanitizeJdbcUrl(String jdbcUrl) {
		// 定义不安全参数和其安全替代值
		String[][] unsafeParams = {
				// allowLoadLocalInfile：允许使用 LOAD DATA LOCAL INFILE，可能导致文件泄露
				{"allowLoadLocalInfile", "false"},
				// allowUrlInLocalInfile：允许在 LOAD DATA LOCAL INFILE 中使用 URL，可能导致未经授权的文件访问
				{"allowUrlInLocalInfile", "false"},
				// autoDeserialize：允许自动反序列化对象，可能导致反序列化漏洞
				{"autoDeserialize", "false"},
				// allowNanAndInf：允许使用 NaN 和 Infinity 作为数字值，可能导致不一致的数据处理
				{"allowNanAndInf", "false"},
				// allowMultiQueries：允许在一个语句中执行多个查询，可能导致 SQL 注入攻击
				{"allowMultiQueries", "false"},
				// allowPublicKeyRetrieval：允许从服务器检索公钥，可能导致中间人攻击
				{"allowPublicKeyRetrieval", "false"}
		};

		// 替换不安全的参数
		for (String[] param : unsafeParams) {
			jdbcUrl = jdbcUrl.replaceAll("(?i)" + param[0] + "=true", param[0] + "=" + param[1]);
		}
		return jdbcUrl;
	}
}

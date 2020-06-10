/*
 *  Copyright 2019-2020 Zheng Jie
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
package me.zhengjie.modules.mnt.util;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import javax.sql.DataSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author /
 */
@Slf4j
public class SqlUtils {

	public static final String COLON = ":";

	private static volatile Map<String, DruidDataSource> map = new HashMap<>();

	private static String getKey(String jdbcUrl, String username, String password) {
		StringBuilder sb = new StringBuilder();
		if (!StringUtils.isEmpty(username)) {
			sb.append(username);
		}
		if (!StringUtils.isEmpty(password)) {
			sb.append(COLON).append(password);
		}
		sb.append(COLON).append(jdbcUrl.trim());

		return SecureUtil.md5(sb.toString());
	}

	/**
	 * 获取数据源
	 *
	 * @param jdbcUrl /
	 * @param userName /
	 * @param password /
	 * @return DataSource
	 */
	private static DataSource getDataSource(String jdbcUrl, String userName, String password) {
		String key = getKey(jdbcUrl, userName, password);
		if (!map.containsKey(key) || null == map.get(key)) {
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


			druidDataSource.setUrl(jdbcUrl);
			druidDataSource.setUsername(userName);
			druidDataSource.setPassword(password);
			// 配置获取连接等待超时的时间
			druidDataSource.setMaxWait(3000);
			// 配置初始化大小、最小、最大
			druidDataSource.setInitialSize(1);
			druidDataSource.setMinIdle(1);
			druidDataSource.setMaxActive(1);

			// 配置间隔多久才进行一次检测需要关闭的空闲连接，单位是毫秒
			druidDataSource.setTimeBetweenEvictionRunsMillis(50000);
			// 配置一旦重试多次失败后等待多久再继续重试连接，单位是毫秒
			druidDataSource.setTimeBetweenConnectErrorMillis(18000);
			// 配置一个连接在池中最小生存的时间，单位是毫秒
			druidDataSource.setMinEvictableIdleTimeMillis(300000);
			// 这个特性能解决 MySQL 服务器8小时关闭连接的问题
			druidDataSource.setMaxEvictableIdleTimeMillis(25200000);

			try {
				druidDataSource.init();
			} catch (SQLException e) {
				log.error("Exception during pool initialization", e);
				throw new RuntimeException(e.getMessage());
			}
			map.put(key, druidDataSource);
		}
		return map.get(key);
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
		}
		return connection;
	}

	private static void releaseConnection(Connection connection) {
		if (null != connection) {
			try {
				connection.close();
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				log.error("connection close error：" + e.getMessage());
			}
		}
	}


	public static void closeResult(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
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
			log.info("Get connection failed:" + e.getMessage());
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
	public static void batchExecute(Connection connection, List<String> sqlList) throws SQLException {
			Statement st = connection.createStatement();
			for (String sql : sqlList) {
				if (sql.endsWith(";")) {
					sql = sql.substring(0, sql.length() - 1);
				}
				st.addBatch(sql);
			}
			st.executeBatch();
	}

	/**
	 * 将文件中的sql语句以；为单位读取到列表中
	 * @param sqlFile /
	 * @return /
	 * @throws Exception e
	 */
	private static List<String> readSqlList(File sqlFile) throws Exception {
		List<String> sqlList = Lists.newArrayList();
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(sqlFile), StandardCharsets.UTF_8))) {
			String tmp;
			while ((tmp = reader.readLine()) != null) {
				log.info("line:{}", tmp);
				if (tmp.endsWith(";")) {
					sb.append(tmp);
					sqlList.add(sb.toString());
					sb.delete(0, sb.length());
				} else {
					sb.append(tmp);
				}
			}
			if (!"".endsWith(sb.toString().trim())) {
				sqlList.add(sb.toString());
			}
		}

		return sqlList;
	}

}

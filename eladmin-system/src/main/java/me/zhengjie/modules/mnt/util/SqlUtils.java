package me.zhengjie.modules.mnt.util;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
	 * @param jdbcUrl
	 * @param userName
	 * @param password
	 * @return
	 */
	private static DataSource getDataSource(String jdbcUrl, String userName, String password) {
		String key = getKey(jdbcUrl, userName, password);
		if (!map.containsKey(key) || null == map.get(key)) {
			DruidDataSource druidDataSource = new DruidDataSource();

			String className = null;
			try {
				className = DriverManager.getDriver(jdbcUrl.trim()).getClass().getName();
			} catch (SQLException e) {
				throw new RuntimeException("Get class name error: =" + jdbcUrl);
			}
			if (StringUtils.isEmpty(className)) {
				DataTypeEnum dataTypeEnum = DataTypeEnum.urlOf(jdbcUrl);
				if (null == dataTypeEnum ) {
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
		} catch (Exception e) {
			connection = null;
		}
		try {
			if (null == connection || connection.isClosed() || !connection.isValid(5)) {
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
				connection = null;
			} catch (Exception e) {
				e.printStackTrace();
				log.error("connection close error", e.getMessage());
			}
		}
	}


	public static void closeResult(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean testConnection(String jdbcUrl, String userName, String password) {
		Connection connection = null;
		try {
			connection = getConnection(jdbcUrl,  userName,  password);
			if (null != connection) {
				return true;
			}
		}catch (Exception e){
			log.info("Get connection failed:",e.getMessage());
		}finally {
			releaseConnection(connection);
		}
		return false;
	}

	public JdbcTemplate jdbcTemplate(String jdbcUrl, String userName, String password) {
		DataSource dataSource = getDataSource(jdbcUrl,  userName,  password);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setFetchSize(1000);
		return jdbcTemplate;
	}

}

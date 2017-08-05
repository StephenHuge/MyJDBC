package com.jdbc.mytools;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.jdbc.common.CommonJDBC;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 关于JDBC的一个自己完成的工具类
 * @author Stephen Huge
 * @date 2017年8月2日
 *
 */
/**
 * 
 * @author Administrator
 * 
 * @date 2017年8月6日 上午12:08:27
 */
/**
 * 
 * @author Administrator
 * 
 * @date 2017年8月6日 上午12:08:29
 */
/**
 * 
 * @author Administrator
 * 
 * @date 2017年8月6日 上午1:22:00
 */
public class MyJDBCTools {

	public static final boolean NOT_AUTO_COMMIT = false;

	public static final boolean AUTO_COMMIT = true;

	/**
	 * 	开始数据库事务： 取消默认提交
	 * @param connection 正在使用中的Connection
	 */
	public static void beginTransaction(Connection connection) {
		if(connection != null) {
			try {
				connection.setAutoCommit(NOT_AUTO_COMMIT);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 提交事务
	 * @param connection 正在使用中的Connection
	 */
	public static void commit(Connection connection) {
		if(connection != null) {
			try {
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 回滚事务
	 * @param connection 正在使用中的Connection
	 */ 
	public static void rollback(Connection connection) {
		if(connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 根据之前获取Connection的方法抽取出的一个工具类，但是仅仅只是第一个版本，
	 * 随着后面学习新的知识例如数据库连接池等，会对其进行不断地迭代更新。
	 * 
	 * @return 一个Connection
	 * @version 1.0
	 * @deprecated 使用 {@link #getConnection()} 代替
	 */
	public static Connection getConnectionV1() {

		String username = null;
		String password = null;
		String jdbcUrl = null;
		String driverClass = null;

		Properties properties = new Properties();

		InputStream inStream = CommonJDBC.class
				.getClassLoader()
				.getResourceAsStream("jdbc.properties");

		Connection connection = null;

		try {
			properties.load(inStream);

			username = properties.getProperty("username");
			password = properties.getProperty("password");
			jdbcUrl = properties.getProperty("jdbcUrl");
			driverClass = properties.getProperty("driverClass");

			Class.forName(driverClass);

			connection = DriverManager
					.getConnection(jdbcUrl, username, password);

		} catch (Exception e) {
			e.printStackTrace();
		}		
		return connection;
	}


	private static DataSource dataSource = null;
	/**
	 * 数据库连接池应只被初始化一次. 
	 */
	static{
		dataSource = new ComboPooledDataSource("c3p0_prop");
	}

	/**
	 * 通过C3P0数据库连接池获取Connection对象
	 * @return 一个Connection对象
	 * @throws Exception 当获取Connection对象发生异常时，抛出该异常
	 */
	public static Connection getConnection() throws Exception {
		return dataSource.getConnection();
	}

	/**
	 * 释放数据库连接时的ResultSet， PreparedStatement和Connection
	 * 
	 * @param rs 数据库连接时的ResultSet
	 * @param ps 数据库连接时的PreparedStatement
	 * @param connection 数据库连接时的Connection
	 */
	public static void releaseDB(ResultSet rs, PreparedStatement ps, Connection connection) {
		if(rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(ps != null) {
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 释放数据库连接时的 PreparedStatement和Connection，其具体实现是
	 * {@code releaseDB(ResultSet rs, PreparedStatement ps, Connection connection)}
	 * 
	 * @param ps 数据库连接时的PreparedStatement
	 * @param connection 数据库连接时的Connection
	 */
	public static void releaseDB(PreparedStatement ps, Connection connection) {
		releaseDB(null, ps, connection);
	}
	/**
	 * 释放数据库连接时的 Connection，其具体实现是
	 * {@code releaseDB(ResultSet rs, PreparedStatement ps, Connection connection)}
	 * 
	 * @param connection 数据库连接时的Connection
	 */	
	public static void releaseDB(Connection connection) {
		releaseDB(null, null, connection);
	}

}

package com.jdbc.mytools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 关于JDBC的一个自己完成的工具类
 * @author Stephen Huge
 * @date 2017年8月2日
 *
 */
public class MyJDBCTools {
	
	
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

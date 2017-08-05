package com.jdbc.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

import com.jdbc.common.CommonJDBC;
import com.jdbc.mytools.MyJDBCTools;

/**
 * 关于事务: 
 * 1. 如果多个操作中每个操作使用的是自己的单独的连接, 则无法保证事务，即必须使用同一个connection； 
 * 2. 具体步骤: 
 * 		1). 事务操作开始前, 开始事务:取消 Connection 的默认提交行为； 
 *  	2). 如果事务的操作都成功,则提交事务；
 *  	3). 回滚事务: 若出现异常, 则在 catch 块中回滚事务。
 *  3. 测试事务的隔离级别 在 JDBC 程序中可以通过 Connection 的 setTransactionIsolation 来设置事务的隔离级别。
 * @author Administrator
 * 
 * @date 2017年8月5日 下午5:44:29
 */
public class CommonTransaction {
	
	public static final boolean NOT_AUTO_COMMIT = false;
	
	public static final boolean AUTO_COMMIT = true;
	
	/**
	 * 关于事务的一个基础测试方法。
	 */
	@Test
	public void myTransaction() {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "INSERT singer(name, bestsong) "
					+ "VALUES(?, ?)";
		try {
			connection = CommonJDBC.getConnectionV1();
			
			connection.setAutoCommit(NOT_AUTO_COMMIT);	// 开始事务: 取消默认提交
			
			ps = connection.prepareStatement(sql);
			
			ps.setObject(1, "王力宏");
			ps.setObject(2, "我们的歌");
			ps.execute();
			
			ps.setObject(1, "五月天");
			ps.setObject(2, "如烟");
			ps.execute();
			
			connection.commit();	// 提交事务
			
					
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				connection.rollback();	// 回滚事务
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally {
			MyJDBCTools.releaseDB(ps, connection);
		}
				
	}
	
	
}

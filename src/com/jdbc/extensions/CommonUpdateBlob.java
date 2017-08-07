package com.jdbc.extensions;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.jdbc.mytools.MyJDBCTools;


public class CommonUpdateBlob {
	
	/**
	 * 插入 BLOB 类型的数据必须使用 PreparedStatement：因为 BLOB 类型的数据时无法使用字符串拼写的。
	 * 在这儿直接使用了QueryRunner，因为QueryRunner封装的就是PreparedStatement。
	 */
	@Test
	public void myWriteBlob() {
		
		Connection connection = null;
		
		String sql = "UPDATE singer SET avatar = ? WHERE name = ?";
		try {
			connection = MyJDBCTools.getConnection();
			QueryRunner qr = new QueryRunner();
			
			InputStream inStream = new FileInputStream("Jolin Cai.png");
			
			qr.update(connection, sql, inStream, "Jolin Cai");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyJDBCTools.releaseDB(connection);
		}
	}
	
	/**
	 * 读取 blob 数据，通过ScalarHandle获得一个byte[]类型的对象，然后通过IO流读取该对象，将其输出为一个
	 * 叫做“蔡依林.png”的图片。
	 */
	@Test
	public void myReadBlob() {
		Connection connection = null;
		
		String sql = "SELECT avatar "
					+ "	FROM singer WHERE name = ?"; 
		
		try {
			connection = MyJDBCTools.getConnection();
			QueryRunner qr = new QueryRunner();
			ResultSetHandler<Object> rsh = new ScalarHandler();
			
			//此处返回的是一个byte[]型的对象，通过使用输出流读取它，将它转化为一个png格式的图片。
			byte[] pic =  (byte[]) qr.query(connection, sql, rsh, "Jolin Cai");
			
			@SuppressWarnings("resource")
			OutputStream out = new FileOutputStream("蔡依林.png");
			
			System.out.println(pic.length);
			
			out.write(pic, 0, pic.length);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyJDBCTools.releaseDB(connection);
		}
	}
}

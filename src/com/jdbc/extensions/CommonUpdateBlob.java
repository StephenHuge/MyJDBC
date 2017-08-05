package com.jdbc.extensions;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import com.jdbc.mytools.MyJDBCTools;

public class CommonUpdateBlob {
	
	@Test
	public void myWriteBlob() {
		
		Connection connection = null;
		
		String sql = "UPDATE singer SET avatar = ? WHERE name = ?";
		try {
			connection = MyJDBCTools.getConnection();
			QueryRunner qr = new QueryRunner();
			
			InputStream inStream = new FileInputStream("Jolin Cai.jpg");
			
			qr.update(connection, sql, inStream, "Jolin Cai");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyJDBCTools.releaseDB(connection);
		}
	}
	
	public void myReadBlob() {
		
	}
}

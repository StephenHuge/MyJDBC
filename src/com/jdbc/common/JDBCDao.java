package com.jdbc.common;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.jdbc.mytools.MyJDBCTools;

/**
 * 在上面{@code CommonJDBC}中我们学习了JDBC的5个基本属性：
 * Connection、 Statement、PreparedStatement、ResultSet和ResultSetMetaData。
 * 通过操作这5个属性，我们知道了如何对数据库进行基本的CRUD操作，在这个类中我们将对如何对数据库的CRUD操作
 * 进行较为详细的说明。具体的说，CRUD操作在业务代码中一般都会抽象成一个Dao类。
 * 
 * 所谓Dao类，即为Data Acess Object，是访问数据信息的类，对数据可以进行CRUD操作。
 * 其中不应包含任何业务逻辑。
 * 
 * 在Dao类中有四个方法，分别是：
 * 1. 包含insert update delete 操作的update方法，传入的是SQL语句和跟它对应的占位符参数；
 * 2. 将一个对象写入数据库的write方法，和update方法的区别是它的参数是一个对象；
 * 3. 查询一条记录的get方法，其中有两种方法，在读取数据之后，可以分别使用
 * 		a. 构造函数创建对象； 
 * 		b. 反射创建对象。
 * 两种方法相比，第二种适用范围更广，所以第一种方法被@Deprecated 修饰，仅供参考。
 * 4. 查询多条记录的getForList方法（是get方法的超集）；
 * 5. 查询一条记录中某个属性的getForField方法（是get方法的子集）。
 * 
 * @author Administrator
 * 
 * @date 2017年8月3日 下午10:27:47
 */
public class JDBCDao {
	
	/**
	 * 使用可变参数对SQL进行更新。
	 * @param sql 一般是带占位符的SQL语句
	 * @param args 可变参数的数组，用来填充SQL语句中的占位符
	 */
	public void update(String sql, Object... args) {
		
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = CommonJDBC.getConnectionV1();
			ps = connection.prepareStatement(sql);
			
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);	//遍历args占位符数组，为每个占位符赋值，占位符位置初始值为1
			}
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyJDBCTools.releaseDB(ps, connection);
		}
	}
	
	public void write() {}
	
	@Deprecated
	public void getByConstructor() {}
	
	@Deprecated
	public void getByReflection() {}
	
	public void getByReflectionWithList() {}
	
	public void getForList() {}

	public void getForField() {}
}

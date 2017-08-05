package com.jdbc.dbutilstest;

import java.security.Signer;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import com.jdbc.common.Singer;
import com.jdbc.mytools.MyJDBCTools;

/**
 * 其实比较常用的有{@code QueryRunner}类和{@code ResultSetHandle}接口。
 * 
 * 它们的具体作用如下：
 * 我们知道，对数据库的操作一般分为两大类：更新（update）和查询(select)。
 * 
 * 更新的一般流程是：先创建一个Connection，然后通过Connection获得PreparedStatement，最后PreparedStatement
 * 执行SQL语句完成更新。这其中PreparedStatement的executeUpdate()方法执行后，一般返回值是一个数字，源代码中的原话是
 * " either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 
 * for SQL statements that return nothing"。意思是返回的值要么是SQL数据操作语言（DML）语句的行数，要么因为SQL声明
 * 没有返回任何东西而返回0。
 * 而查询的流程大体上和更新相同，也是需要先创建一个Connection，然后通过Connection获得PreparedStatement，最后
 * PreparedStatement执行SQL语句完成查询。不同的是其中PreparedStatement的executeQuery()方法执行后返回的是一个
 * ResultSet也就是结果集对象。那么这些和DBUtils包又有什么关系呢？？
 * 
 * 我们上面提到的{@code QueryRunner}类和{@code ResultSetHandle}接口其实就是为了对上述过程进行封装简化而出现的，
 * 其实有了Connection之后，在逐个创建PreparedStatement、ResultSet并且调用它们的各种方法是比较麻烦的，而且很多时候
 * 步骤是完全相同而没有必要的。
 * 相对于更新操作（update），我们在得到Connection之后，其实只需要把它和SQL语句中的占位符传进一个方法，让它执行，如果出错就抛出
 * 异常就可以了，这其实就是{@code QueryRunner}中的{# update(Connection conn, String sql, Object... params)}
 * 方法；
 * 相对于查询(select)方法，情况会稍微复杂一点，但也完全在理解范围之内。我们会同时用到{@code QueryRunner}类和
 * {@code ResultSetHandle}接口，具体方法是调用
 * {@code link QueryRunner #query(Connection conn, String sql, ResultSetHandler<T> rsh,Object... params)}，
 * 传入的参数就是一个 ResultSetHandle实现类，其实现类会对ResutSet对象进行读取和"加工"。
 * 
 * 由于大部分时候，对数据"加工"的方式会很固定，所以ResultSetHandle的实现类一般功能会很固定。而之前在方法
 * {@code com.jdbc.common.JDBCDao# getByReflection(Class<T>, String, Object...)}中，我们将ResultSet对象中的值经过
 * 反射变成了一个T类型的对象，在{# getForList(Class<T> clazz, String sql, Object... args)}方法中，将ResutSet对象中的值经过
 * 反射变成了一个成员类型为T的List对象。其实这两个方法对应的就是ResultSetHandle接口的实现类{@code BeanHandle}和{@code BeanListHandle}。
 * 
 * 其它的实现类还有很多，例如{@code ArrayHandler}、{@code MapHandler}以及它们对应的集合类等。
 * 
 * 
 * @author Administrator
 * 
 * @date 2017年8月5日 下午10:58:49
 */
public class CommonDBUtilsTest {

	/**
	 * 使用QueryRunner测试数据库更新（update）操作。
	 */
	@Test
	public void testQueryRunnerUpdate() {
		Connection connection = null;

		String sql = "DELETE FROM singer WHERE id >= ?";
		try {
			connection = MyJDBCTools.getConnection();

			QueryRunner qr = new QueryRunner();

			qr.update(connection, sql, 9);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyJDBCTools.releaseDB(connection);
		}
	}

	/**
	 * 使用QueryRunner和BeanHandle测试数据库查询（select）操作。
	 */
	@Test
	public void testQueryRunnerSelectWithBeanHandle() {
		Connection connection = null;

		String sql = "SELECT id, name, bestsong "
					+ "	FROM singer WHERE id = ?";
		try {
			connection = MyJDBCTools.getConnection();

			QueryRunner qr = new QueryRunner();
			ResultSetHandler<Singer> beanHandle = new BeanHandler<>(Singer.class);

			Singer singer = qr.query(connection, sql, beanHandle, 2);

			System.out.println(singer);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyJDBCTools.releaseDB(connection);
		}
	}

	/**
	 * 使用QueryRunner和BeanListHandle测试数据库查询（select）操作。
	 */
	@Test
	public void testQueryRunnerSelectWithBeanListHandle() {
		Connection connection = null;

		String sql = "SELECT id, name, bestsong "
					+ "	FROM singer ";
		try {
			connection = MyJDBCTools.getConnection();

			QueryRunner qr = new QueryRunner();
			ResultSetHandler<List<Singer>> beanListHandle = new BeanListHandler<>(Singer.class);

			List<Singer> singers = qr.query(connection, sql, beanListHandle);

			System.out.println(singers);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyJDBCTools.releaseDB(connection);
		}
	}

	/**
	 *  使用QueryRunner和MapHandle测试数据库查询（select）操作。
	 */
	@Test
	public void testQueryRunnerSelectWithMapHandle() {
		Connection connection = null;

		String sql = "SELECT id, name, bestsong "
					+ "	FROM singer WHERE id = ?";
		try {
			connection = MyJDBCTools.getConnection();

			QueryRunner qr = new QueryRunner();
			ResultSetHandler<Map<String, Object>> beanHandle = new MapHandler();

			Map<String, Object> map = qr.query(connection, sql, beanHandle, 2);

			System.out.println(map);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyJDBCTools.releaseDB(connection);
		}

	}

	
	/**
	 * 使用QueryRunner和MapListHandle测试数据库查询（select）操作。
	 */
	@Test
	public void testQueryRunnerSelectWithMapListHandle() {
		Connection connection = null;

		String sql = "SELECT id, name, bestsong "
					+ "	FROM singer";
		try {
			connection = MyJDBCTools.getConnection();

			QueryRunner qr = new QueryRunner();
			ResultSetHandler<List<Map<String, Object>>> beanHandle = new MapListHandler();

			List<Map<String, Object>> maps = qr.query(connection, sql, beanHandle);

			System.out.println(maps);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyJDBCTools.releaseDB(connection);
		}

	}

}

package com.jdbc.connectionpool;

import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestConnectionPool {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMyDBCP() {
		Connection connection = CommonConnectionPool.myDBCP();
		System.out.println(connection);
	}
	
	@Test
	public void testMyDBCPWithDataSourceFactory() {
		Connection connection = CommonConnectionPool.myDBCPWithDataSourceFactory();
		System.out.println(connection);
	}

	@Test
	public void testMyC3P0() {
		Connection connection = CommonConnectionPool.myC3P0();
		System.out.println(connection);
	}
	
	@Test
	public void testMyC3P0WithXML() {
		Connection connection = CommonConnectionPool.myC3P0WithXML();
		System.out.println(connection);
	}
}

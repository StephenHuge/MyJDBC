package com.jdbc.common;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JDBCDaoTest {

	@Test
	public void testUpdate() {
		String sql = "INSERT INTO singer(name, bestsong) "
				+ "VALUES(?, ?)";
		JDBCDao.update(sql, "Jolin Cai", "日不落");
	}

	@Test
	public void testWrite() {
		Singer eason = new Singer("陈奕迅", "十年");
		JDBCDao.write(eason);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetByConstructor() {
		String sql = "SELECT id, name, bestsong "
				+ "	FROM singer WHERE bestsong = ?";
		Singer singer = (Singer) JDBCDao.getByConstructor(sql, "美人鱼");
		
		System.out.println(singer);
	}

	@Test
	public void testGetByReflectionWithoutList() {
		String sql = "SELECT id, name, bestsong bestSong"
				+ "	FROM singer WHERE bestsong = ?";
		Singer singer = JDBCDao.getByReflectionWithoutList(Singer.class, sql, "美人鱼");
		
		System.out.println(singer);
	}

	@Test
	public void testGetByReflection() {
		String sql = "SELECT id, name, bestsong bestSong"
				+ "	FROM singer";
		List<Singer> singers = JDBCDao.getForList(Singer.class, sql);
		
		System.out.println(singers);
	}

	@Test
	public void testGetForList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetForField() {
		fail("Not yet implemented");
	}

}

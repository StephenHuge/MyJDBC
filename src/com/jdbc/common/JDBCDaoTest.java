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
				+ "	FROM singer WHERE bestsong = ?";
		Singer singer = JDBCDao.getByReflection(Singer.class, sql, "日不落");
		
		System.out.println(singer);
	}

	@Test
	public void testGetForList() {
		String sql = "SELECT id, name, bestsong bestSong"
				+ "	FROM singer WHERE id > ?";
		List<Singer> singers = JDBCDao.getForList(Singer.class, sql, 1);
		
		System.out.println(singers);
	}

	@Test
	public void testGetForField() {
		String sql = "SELECT name"
				+ "	FROM singer WHERE bestsong = ?";
		String name = JDBCDao.getForField(sql, "美人鱼");
		
		System.out.println(name);
	}

}

package com.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

import com.utils.JDBCUtils;

/**
 * MySql的增删改查操作。
 *
 */
public class JDBCDemo02 {


	
	/**
	 * 添加用户。
	 */
	@Test
	public void addUser(){
		
		Connection conn = null;
		Statement statement = null;
		
		try{
			
			conn = JDBCUtils.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate("INSERT INTO table_user VALUES(null,'Lucy',22,'云南省昆明市五华区世纪生活园')");
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally{	
			/* 关闭连接、传输对象和结果集。 */
			JDBCUtils.close(conn, statement, null);
		}
		
		JDBCUtils.close(conn, statement, null);
	}

	
	/**
	 * 修改用户信息。
	 */
	@Test
	public void editUser(){
		
		Connection conn = null;
		Statement statement = null;
		
		try{
			
			conn = JDBCUtils.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate("UPDATE table_user set age = 20 where name='Lily'");
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally{	
			/* 关闭连接、传输对象和结果集。 */
			JDBCUtils.close(conn, statement, null);
		}
		
		JDBCUtils.close(conn, statement, null);
	}
	
	/**
	 * 删除用户
	 */
	@Test
	public void delUser(){
		
		Connection conn = null;
		Statement statement = null;
		
		try{
			
			conn = JDBCUtils.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM table_user where name = 'Lucy'");
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally{	
			/* 关闭连接、传输对象和结果集。 */
			JDBCUtils.close(conn, statement, null);
		}
		
		JDBCUtils.close(conn, statement, null);
	}
	
	/**
	 * 查找用户。
	 */
	@Test
	public void findUser(){
		
		Connection conn = null;
		Statement statement = null;
		ResultSet reSet = null;
		try{
			
			conn = JDBCUtils.getConnection();
			statement = conn.createStatement();
			reSet = statement.executeQuery("select * from table_user");
			
			while(reSet.next()){
				
				String Id = reSet.getString("Id");
				String name = reSet.getString("Name");
				String Age = reSet.getString("Age");
				String Address = reSet.getString("Address");
				
				System.out.println(Id + "  " + name +"  "+ Age +"  "+ Address + "  ");
			}
			
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally{	
			/* 关闭连接、传输对象和结果集。 */
			JDBCUtils.close(conn, statement, reSet);
		}
		
		JDBCUtils.close(conn, statement, reSet);
	}
	
	
	
}




















package com.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.pool.MyPool;

/**
 * 测试 JDBC	的连接池。
 *
 */
public class JDBCDemo03 {

	public static void main(String[] args) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet reSet = null;
		MyPool pool = new MyPool();
		
		try{
			conn = pool.getConnection();
			ps = conn.prepareStatement("SELECT * from users");
			reSet = ps.executeQuery();
			
			while (reSet.next()) {
				String name = reSet.getString("name");
				System.out.println(name);
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			
			if(reSet!=null){
				try {
					reSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					reSet = null;
				}
			}
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					ps = null;
				}
			}
			
			/* 将连接归还连接池。因为使用了动态代理，所以这里只要调用colse()方法就能实现。 */
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					conn = null;
				}
			}
		}
		
		
	}
	
	
}

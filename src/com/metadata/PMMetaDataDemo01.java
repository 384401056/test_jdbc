package com.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 参数元数据。
 *
 */
public class PMMetaDataDemo01 {
public static void main(String[] args) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ComboPooledDataSource source = new ComboPooledDataSource();
		
		try {
			
			conn = source.getConnection();
			ps = conn.prepareStatement("SELECT * FROM users where name=? and password=?");
			ps.setString(1, "kkk");
			ps.setString(2, "kkk");
			rs = ps.executeQuery();
			
			/*获取参数元数据。*/
			ParameterMetaData pm = ps.getParameterMetaData();
			
			/*获取参数个数。*/
			System.err.println(pm.getParameterCount());
			
			/*获取参数的类型，但是MySql不支持获取参数类型，所以执行不了。*/
//			System.out.println(pm.getParameterTypeName(1));
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					/* 手动将其引用指向null,java的内存清理机制最终会对其清除 */
					rs = null;
				}
			}
			
			if(ps!=null){
				try {
					ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					/* 手动将其引用指向null,java的内存清理机制最终会对其清除 */
					ps = null;
				}
			}
			
			if(conn!=null){
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					/* 手动将其引用指向null,java的内存清理机制最终会对其清除 */
					conn = null;
				}
			}
		}
		
		
	}
}

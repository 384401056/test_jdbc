package com.metadata;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 结果集元数据。
 *
 */
public class RSMetaDataDemo01 {
	
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ComboPooledDataSource source = new ComboPooledDataSource();
		
		try {
			
			conn = source.getConnection();
			ps = conn.prepareStatement("SELECT * FROM users");
			rs = ps.executeQuery();
			
			/*获取结果集元数据。*/
			ResultSetMetaData resMetaData =  rs.getMetaData();
//			
//			/*结果集的列数*/
//			System.out.println(resMetaData.getColumnCount());
//			/*结果集的列名*/
//			System.out.println(resMetaData.getColumnName(2));
//			/*结果集中列的类型名*/
//			System.out.println(resMetaData.getColumnTypeName(1));
			
			
			/**
			 * 根据结果集元数据和结果集中的内容打印一个表出来。
			 */
			
			
			
			System.out.println("---------------------------------------------------------------");

			int columnCount = resMetaData.getColumnCount();
			for(int i=1;i<=columnCount;i++){
				System.out.print("  "+resMetaData.getColumnName(i)+"\t");
			}
			
			System.out.println();
			System.out.println("---------------------------------------------------------------");
			
			while(rs.next()){
				/*在不知道结果集字段名的情况下，输入出结果集。*/
				for(int i=1;i<=columnCount;i++){
					Object obj = (Object)rs.getObject(i);
					System.out.print("  "+obj+"\t");
				}
				System.out.println();
			}
			
			
			System.out.println("---------------------------------------------------------------");
			
			
			
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

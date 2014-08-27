package com.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mchange.v2.c3p0.ComboPooledDataSource;


/**
 * 数据库元数据测试类
 *
 */
public class DataBaseMetaDataDemo01 {
	
	public static void main(String[] args) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ComboPooledDataSource source = new ComboPooledDataSource();
		
		try {
			
			conn = source.getConnection();
			ps = conn.prepareStatement("SELECT * FROM users");
			rs = ps.executeQuery();
			
//			while(rs.next()){
//				System.out.println( rs.getString("name") );
//			}
			/*获取数据库元数据*/
			DatabaseMetaData metadata = (DatabaseMetaData) conn.getMetaData();
			
			/*获取数据连接的url*/
			System.out.println(metadata.getURL());
			
			/*获取数据库的主键信息*/
			rs = metadata.getPrimaryKeys(null, null, "users");
			
			while(rs.next()){
				Short cseq = rs.getShort("KEY_SEQ"); /*所在列的序号*/
				String cName = rs.getString("COLUMN_NAME");/*列名*/
				System.out.println(cseq+":"+cName);
			}
			
			/*获取数据库中所有的表。*/
			rs = metadata.getTables(null, null, "%", new String[]{"TABLE"});
			
			while(rs.next()){
				String tableName = rs.getString("TABLE_NAME");
				System.out.println(tableName);
			}
			
			
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

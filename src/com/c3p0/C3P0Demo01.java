package com.c3p0;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Demo01 {
	public static void main(String[] args) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet reSet  = null;

		try {
			
			/* C3P0开源数据连接池 */
//			ComboPooledDataSource dataSource = new ComboPooledDataSource();
//			dataSource.setDriverClass("com.mysql.jdbc.Driver");
//			dataSource.setJdbcUrl("jdbc:mysql://localhost/test");
//			dataSource.setUser("root");
//			dataSource.setPassword("root");
			
			
			/* 
			 * 通过配置文件来配置C3P0连接池。 
			 * 1.建立C3P0的XML配置文件。文件名为：从c3p0-config.xml，配置项参与上面的 setXXX 首字母小写
			 * 2.new 出ComboPooledDataSource 即可。
			 */
			ComboPooledDataSource dataSource = new ComboPooledDataSource();

			/* 得到数据连接。 */
			conn = dataSource.getConnection();
			ps = conn.prepareStatement("SELECT * FROM users");
			reSet = ps.executeQuery();
			
			while(reSet.next()){
				
				System.out.println(reSet.getString("name"));

			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(reSet!=null){
				try {
					reSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					/* 手动将其引用指向null,java的内存清理机制最终会对其清除 */
					reSet = null;
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

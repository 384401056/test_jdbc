package com.dbcp;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;


/**
 * 使用开源的数据连接池 DBCP
 *
 */
public class DBCPDemo01 {
	
	

	public static void main(String[] args) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet reSet  = null;
		
		/* 开源数据连接池 */
//		BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//		dataSource.setUrl("jdbc:mysql://localhost/test");
//		dataSource.setUsername("root");
//		dataSource.setPassword("root");
		
		
		try {
		
			/* 通过配置文件方式来设置DBCP,注意配置文件的key写法要与上面的setXXXX相对应，首字母改为小写 */
			Properties prop = new Properties();
			prop.load(new FileReader(DBCPDemo01.class.getClassLoader().getResource("dbcp.properties").getPath()));
			BasicDataSourceFactory factory = new BasicDataSourceFactory();
			BasicDataSource dataSource = (BasicDataSource)factory.createDataSource(prop);
			

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














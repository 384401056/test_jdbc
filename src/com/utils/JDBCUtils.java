package com.utils;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {

	private static Properties propert = null;
	
	private JDBCUtils() {
		
	}
	
	/*载入配置文件*/
	static {
		try{
			
			propert = new Properties();
			propert.load(new FileReader(JDBCUtils.class.getClassLoader().getResource("config.properties").getPath())) ;
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取连接
	 */
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		
		Class.forName(propert.getProperty("driver"));
		String mySqlStr = propert.getProperty("mySqlStr");
		return DriverManager.getConnection(mySqlStr);
	}
	
	/**
	 * 关闭连接
	 */
	public static void close(Connection conn,Statement statement,ResultSet reSet){
		
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
		
		if(statement!=null){
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				/* 手动将其引用指向null,java的内存清理机制最终会对其清除 */
				statement = null;
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

package com.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

/**
 * 六个步骤实现JDBC连接数据库。
 */
public class JDBCDemo01 {

	public static void main(String[] args) {
		
		Connection conn = null;
		Statement stat= null;
		ResultSet resultSet= null;
		
		try{
			/* 1.注册数据库驱动, 不同的数据库要导入不同的驱动jar包。Driver就是这个包中的类。*/
			/*---这种方式有两个不好的地方：
			 * 		1）查看Driver的源码，我们会发现在 new Driver()的时候，其实已经注册了一次驱动了。这样就造成注册了两次驱动。
			 * 		2）这样写，让程序导入了com.mysql.jdbc.Driver包，程序的可扩展性下降了。如果要使用其它的数据库就要修改程序代码了。
			 *
	*		DriverManager.registerDriver(new Driver());
			 *
			 * 基于以上的考量，所以最后选择使用下面的方法。 通过反射机制来加载一个，给定全路径名的类。这样只要把字符串写入配置文件中，就可以不修改源代码了。*/	
			Class.forName("com.mysql.jdbc.Driver");
			
			/* 2.连接数据库 */
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world?user=root&password=root");
					
			/* 其它数据库的URL写法。
			 * 
			 * Oracle数据库的写法： "jdbc:oracle:thin:@localhost:1521:sid"
			 * SqlServer数据库的写法："jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=sid"
			 */
			
			/* 3.获取传输器对象 */
			stat = conn.createStatement();
			
			/* 4.利用传输器将sql语句传输到数据库中去执行，并返回一个结果集。 */
			resultSet =  stat.executeQuery("SELECT * from city");
			
			/* 5.遍历结果集，取出数据。 */
			while (resultSet.next()){
				
				String name = resultSet.getString("Name");
				String CountryCode = resultSet.getString("CountryCode");
				String District = resultSet.getString("District");
				String PhoneNumber = resultSet.getString("PhoneNumber");
				
				System.out.println(name+"    "+CountryCode+"    "+District+"    "+PhoneNumber);
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			
			/* 6.关闭连接、传输对象和结果集。 */

			if(resultSet!=null){
				try {
					resultSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					
					/* 手动将其引用指向null,java的内存清理机制最终会对其清除 */
					resultSet = null;
				}
			}
			
			if(stat!=null){
				try {
					stat.close();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					
					/* 手动将其引用指向null,java的内存清理机制最终会对其清除 */
					stat = null;
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



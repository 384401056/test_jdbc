package com.pool;

import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.sql.DataSource;


/**
 * JDBC	连接池
 *
 */
public class MyPool implements DataSource {
	
	/* 存放连接的List */
	private static List<Connection> pool = new LinkedList<Connection>();

	/* 生成连接，并放入连接池中。 */
	static{
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			for(int i=0;i<5;i++){
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test?user=root&password=root");
				pool.add(conn);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		
		if(pool.size()==0){
			for(int i=0;i<3;i++){
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test?user=root&password=root");
				pool.add(conn);
				
			}
		}
		
		final Connection conn = pool.remove(0);
		
		/**
		 * 使用动态代理来优化Connection的关闭方法，让它实现调用close()方法也能交还连接。
		 */
		Connection proxy = (Connection)Proxy.newProxyInstance(Connection.class.getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler() {
			
			@Override
			public Object invoke(Object object, Method method, Object[] arg)
					throws Throwable {
				
				/* 如果调用的是Connectio类的close()方法，则执行归还连接的过程。 */
				if("close".equals(method.getName())){
					retConn(conn);
					return null;
				}
				/* 调用的不是close()方法，则还是该执行什么就执行什么方法，该返回什么就返回什么。 */
				else{
					return method.invoke(conn, arg);
				}
			}
		});
		
		
		System.out.println("获取了一个连接，还剩"+pool.size()+"个连接。");
		
		/* 从池中移除一个连接，并将此Connection返回给调用者 */
		return proxy;
	}
	
	/**
	 * 用完连接后，交还Conncetion的方法.
	 */
	private void retConn(Connection conn){
		try {
			
			/*如果连接不为空，并且没有被关闭，则再将此Connection加入连接池。*/
			if(conn!=null && !conn.isClosed()){
				pool.add(conn);
				
				System.out.println("归还了一个连接，还剩"+pool.size()+"个连接。");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	
	
	
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		
	}

	@Override
	public void setLoginTimeout(int arg0) throws SQLException {
		
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return null;
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		return null;
	}

}

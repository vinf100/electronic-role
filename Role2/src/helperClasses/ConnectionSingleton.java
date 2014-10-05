package helperClasses;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.servlet.http.HttpServlet;

public class ConnectionSingleton {
	private static ConnectionSingleton instance = null;
	private Connection conn = null;
	InputStream input = null;
	
	private ConnectionSingleton(HttpServlet servlet) {
		
		try{
			Properties properties = new Properties();
			properties.load(
					servlet.getServletContext()
					.getResourceAsStream("/WEB-INF/config.properties"));
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	
			conn = DriverManager.getConnection(properties.getProperty("dburl"), properties.getProperty("dbuser"),properties.getProperty("dbpass"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public Connection getConn(){
		return instance.conn;
	}
	public static ConnectionSingleton getInstance(HttpServlet servlet) {
		if(instance == null || instance.conn == null){
			instance = new ConnectionSingleton(servlet);
		}
		return instance;
	}
	

}

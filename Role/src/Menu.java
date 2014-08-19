import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Menu extends HttpServlet {
	public Connection setUpConnection(Connection conn,String url)throws SQLException{
		try{
		Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			System.out.println("\n\nClass Not Found");
		}
	
		conn = DriverManager.getConnection(url);
		return conn;
	}
	public boolean Check(Connection conn, String pass, String user) throws SQLException{
		boolean correct = false;
		PreparedStatement prepStmt;
		String query = "SELECT user, pass FROM teachers WHERE user = '?' AND pass = '?'";
		prepStmt = conn.prepareStatement(query);
		ResultSet rs = prepStmt.executeQuery();
		if (rs == null){
			correct = false;
		}else{
			correct = true;
		}
		prepStmt.clearParameters();
		return correct;
	} 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			if(Check(conn,username,password)){
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		}

}

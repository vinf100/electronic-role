package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TakeRole extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		String url = null;
		HttpSession session = request.getSession(false);
		try {
			PrintWriter out = response.getWriter();
			Connection conn = null;
			conn =  Menu.setUpConnection(conn, url);
			String query = "SELECT * FROM ?";
			PreparedStatement prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1,(String) session.getAttribute("ClassName"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Menu extends HttpServlet {
	public static Connection setUpConnection(Connection conn, String url)
			throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("\n\nClass Not Found");
		}

		conn = DriverManager.getConnection(url);
		return conn;
	}

	public boolean checkAndCreateSession(Connection conn, String pass,
			String user, HttpSession session, ResultSet rs, PreparedStatement prepStmt) throws SQLException {
		boolean correct = false;
	
		String query = "SELECT username, password FROM teachers WHERE username = '?' AND password = '?'";
		prepStmt = conn.prepareStatement(query);
		prepStmt.setString(1, user);
		prepStmt.setString(2, pass);
		rs = prepStmt.executeQuery(query);
		if (rs == null) {
			correct = false;
		} else {

			session.setAttribute("ClassName", rs.getString("Class Name"));
			correct = true;
		}
		prepStmt.clearParameters();
		return correct;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = null;
		HttpSession session = request.getSession(true);
		Connection conn = null;
		try {
			conn = setUpConnection(conn,url);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ResultSet rs = null;
		PreparedStatement prepStmt = null;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			if (checkAndCreateSession(conn, username, password, session, rs, prepStmt)) {
				RequestDispatcher rd = request
						.getRequestDispatcher("MenuSuccess.jsp");
				rd.forward(request, response);
			} else {
				// send to error page
				session.invalidate();
				RequestDispatcher rd = request
						.getRequestDispatcher("MenuFail.jsp");
				rd.forward(request, response);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					try {
						prepStmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}

}

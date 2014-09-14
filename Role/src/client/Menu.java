package client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Menu extends HttpServlet {
	public static Connection setUpConnection(Connection conn, String url)
			throws SQLException, InstantiationException, IllegalAccessException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		conn = DriverManager.getConnection(url, "Role", "vinkf088vinkf088");
		return conn;
	}

	public boolean checkAndCreateSession(Connection conn, String pass,
			String user, HttpSession session, ResultSet rs, Statement stmt) {
		boolean correct = false;

		String query = "SELECT username, password, classname FROM roleproject.teacherlogin WHERE username = '"
				+ user + "' AND password='" + pass + "';";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if (rs == null) {
				correct = false;
			} else {
				rs.next();
				session.setAttribute("classname", (String) rs.getString("classname"));
				correct = true;
			}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				if (!(rs == null)) {
					rs.close();
				}
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return correct;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if(session != null){
			doPost(request,response);
		}else{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/Role/login.html");
			dispatcher.forward(request,response);
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(ProcessRoleRequest.getFormattedStringDate());
		String url = "jdbc:mysql://localhost:3306";
		HttpSession session = request.getSession(true);
		try {
			Connection conn = null;
			conn = setUpConnection(conn, url);
			ResultSet rs = null;
			Statement stmt = null;
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			if (checkAndCreateSession(conn, password, username, session, rs,
					stmt)) {
				RequestDispatcher rd = request
						.getRequestDispatcher("Success.jsp");
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
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

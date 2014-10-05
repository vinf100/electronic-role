package client;

import helperClasses.ConnectionSingleton;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessLoginRequest extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static boolean checkAndCreateSession(Connection conn, String pass,
			String user, ResultSet rs,
			PreparedStatement prepstmt, String table, HttpServletRequest request) {
		boolean correct = false;

		String query = "SELECT username, password, classname FROM rollproject."
				+ table + " WHERE username = ? AND password=?;";
		try {
			prepstmt = conn.prepareStatement(query);
			prepstmt.setString(1, user);
			prepstmt.setString(2, pass);
			rs = prepstmt.executeQuery();
			if (!rs.isBeforeFirst()) {
				correct = false;
			} else {
				rs.next();
				HttpSession session = request.getSession(true);
				session.setAttribute("classname",
						(String) rs.getString("classname"));
				correct = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				prepstmt.close();
				if (!(rs == null)) {
					rs.close();
				}
				prepstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return correct;
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			System.out.println(ProcessRoleRequest.getFormattedStringDate());

			Connection conn = null;
			ConnectionSingleton connection = ConnectionSingleton
					.getInstance(this);
			conn = connection.getConn();
			ResultSet rs = null;
			PreparedStatement prepstmt = null;
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			if (checkAndCreateSession(conn, password, username, rs,
					prepstmt, "teachers", request)) {
				System.out.println("Success");
				RequestDispatcher rd = request
						.getRequestDispatcher("/Menu.jsp");
				rd.forward(request, response);
			} else {
				// send to error page
				System.out.println("Fail");
				RequestDispatcher rd = request
						.getRequestDispatcher("/client/LoginFail.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

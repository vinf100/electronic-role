package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TakeRole extends HttpServlet {
	public void writeValues(String url, HttpSession session, ResultSet rs,
			PrintWriter out, PreparedStatement prepStmt) {
		Connection conn = null;
		try {
			conn = Menu.setUpConnection(conn, url);

			String query = "SELECT * FROM ?";
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, (String) session.getAttribute("ClassName"));
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				out.println("<table><tr><td>" + rs.getString("firstname")
						+ "</td><td>" + rs.getString("lastname") + "</td></tr></table>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
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

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ResultSet rs = null;
		String url = null;
		PreparedStatement prepStmt = null;
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		out.println("<html> <head> <title> Take Role </title> </head> <body>"
				+ "<form action = '/Role/ProcessRoleRequest' method = 'POST'>");
		writeValues(url, session, rs, out, prepStmt);
	}
}

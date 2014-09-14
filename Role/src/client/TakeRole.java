package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TakeRole extends HttpServlet {
	public void writeValues(String url, HttpSession session, ResultSet rs,
			PrintWriter out, Statement stmt) throws SQLException,
			InstantiationException, IllegalAccessException {
		Connection conn = null;
		conn = Menu.setUpConnection(conn, url);
		String query = "SELECT * FROM roleproject."
				+ (String) session.getAttribute("classname") + ";";
		stmt = conn.prepareStatement(query);
		rs = stmt.executeQuery(query);

		try {
			Integer personNum = 1;
			String personNumString;
			out.println("<table border='1' align=decimal>");
			while (rs.next()) {
				System.out.println(personNum);
				personNumString = Integer.toString(personNum);
				
				out.println("<tr ><td>"
						+ rs.getString("firstname") + " " + rs.getString("lastname") 
						+ "</td>"
						+ "</td><td>Present: <input type = 'radio' name = 'person"
						+ personNumString + "' value = 'Present'></td><td>Late: <input type = 'radio' name = 'person"
						+ personNumString + "' value = 'Late'></td><td>Sick: <input type = 'radio' name = 'person"
						+ personNumString + "' value = 'Sick'></td><td>Other: <input type = 'radio' name = 'person"
						+ personNumString + "' value = 'Other'></td></tr>");
				personNum++;
			}
			out.println("</table>");
			out.println("<input type = 'submit'>");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ResultSet rs = null;
		String url = "jdbc:mysql://localhost:3306";
		Statement stmt = null;
		HttpSession session = request.getSession(false);
		if (session == null) {
			RequestDispatcher rd = request.getRequestDispatcher("login.html");
			rd.forward(request, response);
		}
		PrintWriter out = response.getWriter();
		out.println("<html> <head> <title> Take Role </title> </head> <body>"
				+ "<form action = '/Role/ProcessRoleRequest' method = 'GET'>");
		try {
			writeValues(url, session, rs, out, stmt);
			out.println("<form></body></html>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

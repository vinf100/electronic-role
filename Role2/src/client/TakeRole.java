package client;

import helperClasses.ConnectionSingleton;
import helperClasses.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TakeRole extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void writeValues(String url, HttpSession session, ResultSet rs,
			PrintWriter out, Statement stmt) throws Exception {
		Connection conn = null;

		ConnectionSingleton connection = ConnectionSingleton.getInstance(this);
		conn = connection.getConn();
		String query = "SELECT * FROM rollproject.students WHERE classname = ' "
				+ session.getAttribute("classname") + ";";
		stmt = conn.prepareStatement(query);
		rs = stmt.executeQuery(query);

		try {
			Integer personNum = 1;
			String personNumString;
			out.println("<table border='2' align=decimal id = 'takeRoll'>");
			while (rs.next()) {
				personNumString = Integer.toString(personNum);

				out.println("<tr ><td>"
						+ rs.getString("firstname")
						+ " "
						+ rs.getString("lastname")
						+ "</td>"
						+ "</td><td>Present: <input type = 'radio' name = 'person"
						+ personNumString
						+ "' value = 'Present'></td><td>Late: <input type = 'radio' name = 'person"
						+ personNumString
						+ "' value = 'Late'></td><td>Sick: <input type = 'radio' name = 'person"
						+ personNumString
						+ "' value = 'Sick'></td><td>Other: <input type = 'radio' name = 'person"
						+ personNumString + "' value = 'Other'></td></tr>");
				personNum++;
			}
			out.println("</table>");
			out.println("<input type = 'submit'>");
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

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ResultSet rs = null;
		String url = "jdbc:mysql://localhost:3306";
		Statement stmt = null;
		HttpSession session = request.getSession(false);
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = formatter.format(date);
		PrintWriter out = response.getWriter();
		out.println("<html> <head> <link rel = 'stylesheet' type = 'text/css' href = 'CSS/client/takeRole.css'><title> Take Role </title> </head> <body>");
		out.println("<link rel = 'stylesheet' type = 'text/css' href = 'CSS/topnav.css'/>"
				+ "<div>	<table> "
				+ "<tr><td><a href='/Role/Menu.jsp' target='_top'>HOME</a></td>"
				+ "<td><a href='/Role/TakeRole'>TAKE ROLE</a></td>"
				+ "<td><a href='/Role/DisplayStudentRecords'>STUDENT RECORDS</a></td>"
				+ "<td><a href = '/Role/Logout'>LOGOUT</a></td></tr>"
				+ "<tr></table></div>");

		out.println("<h1> Take The Roll");
		out.print("<h3>" + strDate + "</h3>");
		out.println("<form action = '/Role/ProcessRoleRequest' method = 'GET'>");
		try {
			writeValues(url, session, rs, out, stmt);
			out.println("</form></body></html>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

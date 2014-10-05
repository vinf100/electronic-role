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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DisplayStudentRecords extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected synchronized void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Connection conn = ConnectionSingleton.getInstance(this).getConn();
		try {

			// Initialize connection to database

			// Get Student Names
			Statement stmtStudents = conn.createStatement();
			String studentsQuery = "SELECT firstname,lastname FROM rollproject.students where classname = "
					+ (String) session.getAttribute("classname");
			ResultSet rsStudents = stmtStudents.executeQuery(studentsQuery);
			PrintWriter out = response.getWriter();

			out.println("<html><head><title>Student Records</title> </head><body>");
			out.println("<link rel = 'stylesheet' type = 'text/css' href = 'CSS/topnav.css'/>"
					+ "<div>	<table> "
					+ "<tr><td><a href='/Role/Menu.jsp' target='_top'>HOME</a></td>"
					+ "<td><a href='/Role/TakeRole'>TAKE ROLE</a></td>"
					+ "<td><a href='/Role/DisplayStudentRecords'>STUDENT RECORDS</a></td>"
					+ "<td><a href = '/Role/Logout'>LOGOUT</a></td></tr>"
					+ "<tr></table></div>");
			// Write Top of table
			out.println("<table border = '1'>");
			out.println("<tr><td>Date</td>");
			while (rsStudents.next()) {
				String name = rsStudents.getString("firstname") + " "
						+ rsStudents.getString("lastname");
				out.println("<td>" + name + "</td>");
			}
			Date date = new Date();
			Calendar cal = new GregorianCalendar();
			int i = 0;
			try {
				while (i <= 30) {
					cal.setTime(date);
					cal.add(Calendar.DAY_OF_MONTH, -i);
					String dateItWas = ProcessRoleRequest.formatGivenDate(cal
							.getTime());
					SimpleDateFormat formatter = new SimpleDateFormat(
							"dd/MM/yyyy");
					String strDate = formatter.format(cal.getTime());
					SimpleDateFormat weekFormatter = new SimpleDateFormat("u");
					String weekDay = weekFormatter.format(cal.getTime());
					Statement stmt = conn.createStatement();
					String studentRecords = "SELECT isHere FROM rollproject.dayrecords WHERE classname = "
							+ (String) session.getAttribute("classname")
							+ " AND Date =" + dateItWas;
					ResultSet rs;
					rs = stmt.executeQuery(studentRecords);

					out.println("<tr><td>" + strDate + "</td>");
					rsStudents.first();
					if (rs.isBeforeFirst()) {
						while (rs.next()) {
							out.println("<td>" + rs.getString("isHere")
									+ "</td>");
						}
					}else{
						out.println("<td>N/A</td>");
						rsStudents.next();
					}
					out.println("</tr");

					if (weekDay.equals("1")) {
						i += 3;
					} else {
						i++;
					}
					rs.close();
					stmt.close();
				}
			} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
				try {
					while (rsStudents.next()) {
						out.println("<td>N/A</td>");

					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rsStudents.close();
			stmtStudents.close();
			out.println("<br/> <a href = '/Role/Menu.jsp'>Back to menu</a>");
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}

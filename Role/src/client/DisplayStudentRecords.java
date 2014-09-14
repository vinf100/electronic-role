package client;

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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DisplayStudentRecords extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			// Check if session is valid
			if (session == null) {
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/Role/login.html");
				dispatcher.forward(request, response);
			}
			// Initialize connection to database
			Connection conn = null;
			String url = "jdbc:mysql://localhost:3306/roleproject";
			conn = Menu.setUpConnection(conn, url);
			// //////////////////////////////////////
			Statement stmtStudents = conn.createStatement();
			String studentsQuery = "SELECT firstname,lastname FROM roleproject."
					+ session.getAttribute("classname");
			ResultSet rsStudents = stmtStudents.executeQuery(studentsQuery);
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>Student Records</title> </head><body> <table border = '1'>"
					);
			out.println("<tr><td>Date</td>");
			while (rsStudents.next()) {
				String name = rsStudents.getString("firstname") + " " 
						+ rsStudents.getString("lastname");
				out.println("<td>" + name + "</td>");
			}
			Date date = new Date();
			Calendar cal = new GregorianCalendar();

			for (int i = 0; i < 30; i++) {
				cal.setTime(date);
				cal.add(Calendar.DAY_OF_MONTH, -i);
				String dateItWas = ProcessRoleRequest.formatGivenDate(cal
						.getTime());
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String strDate = formatter.format(date);
				Statement stmt = conn.createStatement();
				String studentRecords = "SELECT isHere FROM roleproject."
						+ session.getAttribute("classname") + dateItWas;
				ResultSet rs;
				try{
				rs  = stmt.executeQuery(studentRecords);
				}catch(SQLException e){
					break;
				}
				out.println("<tr><td>" + strDate + "</td>");
				while (rs.next()) {
					out.println("<td>" + rs.getString("isHere") + "</td>");
				}
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}

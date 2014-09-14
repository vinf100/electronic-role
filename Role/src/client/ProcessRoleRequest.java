package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessRoleRequest extends HttpServlet {
	public static String getFormattedStringDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date todayDate = new Date();
		String stringTodayDate = format.format(todayDate);
		String fullyFormattedDate = stringTodayDate.substring(0, 2)
				+ stringTodayDate.substring(3, 5)
				+ stringTodayDate.substring(6, 10);
		return fullyFormattedDate;
	}
	public static String formatGivenDate(Date date){
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String stringTodayDate = format.format(date);
		String fullyFormattedDate = stringTodayDate.substring(0, 2)
				+ stringTodayDate.substring(3, 5)
				+ stringTodayDate.substring(6, 10);
		return fullyFormattedDate;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		if (session == null) {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("login.html");
			dispatcher.forward(request, response);
			System.out.println("Session is null");
		}
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/roleproject";
		Statement stmtSelect = null;
		Statement stmtCreate = null;
		Statement stmtInsert = null;
		String SelectQuery = "Select * FROM roleproject."+ session.getAttribute("classname");

		ResultSet rs = null;
		try {

			conn = Menu.setUpConnection(conn, url);
			stmtSelect = conn.createStatement();
			stmtCreate = conn.createStatement();
			stmtInsert = conn.createStatement();
			rs = stmtSelect.executeQuery(SelectQuery);
			if (!rs.isClosed()) {
				System.out.println("test success");
			} else {
				System.out.println("test failed");
			}
			Enumeration<?> names = request.getParameterNames();
			String queryCreateTable = "CREATE TABLE "
					+ session.getAttribute("classname")
					+ getFormattedStringDate()
					+ "("
					+ "firstname varchar(20), lastname varchar(20), ishere varchar(8));";
			stmtCreate.executeUpdate(queryCreateTable);

			Enumeration<?> namesTest = request.getParameterNames();
			
			while (names.hasMoreElements()) {
				rs.next();
				String parameterName = (String) names.nextElement();
				String InsertQuery;
				System.out.println(request.getParameter(parameterName));
				System.out.println(parameterName);
				if (request.getParameter(parameterName).equals("Present")) {
					InsertQuery = "INSERT INTO " + "roleproject."
							+ (String) session.getAttribute("classname")
							+ getFormattedStringDate() + " values('"
							+ rs.getString("firstName") + "','"
							+ rs.getString("lastname") + "','Present'" + ");";
					System.out.println("Execute Update");
					stmtInsert.executeUpdate(InsertQuery);
				} else if (request.getParameter(parameterName).equals("Late")) {
					InsertQuery = "INSERT INTO " + "roleproject."
							+ (String) session.getAttribute("classname")
							+ getFormattedStringDate() + " values('"
							+ rs.getString("firstName") + "','"
							+ rs.getString("lastname") + "','Late'" + ");";
					System.out.println("Execute Update");
					stmtInsert.executeUpdate(InsertQuery);

				} else if (request.getParameter(parameterName).equals("Sick")) {
					InsertQuery = "INSERT INTO " + "roleproject."
							+ (String) session.getAttribute("classname")
							+ getFormattedStringDate() + " values('"
							+ rs.getString("firstName") + "','"
							+ rs.getString("lastname") + "','Sick'" + ");";
					System.out.println("Execute Update");
					stmtInsert.executeUpdate(InsertQuery);

				} else {
					InsertQuery = "INSERT INTO " + "roleproject."
							+ (String) session.getAttribute("classname")
							+ getFormattedStringDate() + " values('"
							+ rs.getString("firstName") + "','"
							+ rs.getString("lastname") + "','Other'" + ");";
					System.out.println("Execute Update");
					stmtInsert.executeUpdate(InsertQuery);

				}

				System.out.println("While looped closed");
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmtCreate.close();
				stmtInsert.close();
				stmtSelect.close();
				conn.close();
				System.out.println("rs closed in try");
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("RoleRequestSuccess.jsp");
		dispatcher.forward(request, response);
	}
}

package client;

import helperClasses.ConnectionSingleton;
import helperClasses.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	private static final long serialVersionUID = 1L;

	public static String getFormattedStringDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date todayDate = new Date();
		String stringTodayDate = format.format(todayDate);
		String fullyFormattedDate = stringTodayDate.substring(0, 2)
				+ stringTodayDate.substring(3, 5)
				+ stringTodayDate.substring(6, 10);
		return fullyFormattedDate;
	}

	public static String formatGivenDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String stringTodayDate = format.format(date);
		String fullyFormattedDate = stringTodayDate.substring(0, 2)
				+ stringTodayDate.substring(3, 5)
				+ stringTodayDate.substring(6, 10);
		return fullyFormattedDate;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Connection conn = null;
		Statement stmtSelect = null;
		Statement stmtCreate = null;
		PreparedStatement stmtInsert = null;
		String SelectQuery = "Select * FROM rollproject.students where classname = '" + session.getAttribute("classname")  ;
		
		ResultSet rs = null;
		try {

			ConnectionSingleton connection = ConnectionSingleton
					.getInstance(this);
			conn = connection.getConn();
			stmtSelect = conn.createStatement();
			stmtCreate = conn.createStatement();

			rs = stmtSelect.executeQuery(SelectQuery);

			Enumeration<?> names = request.getParameterNames();
			while (names.hasMoreElements()) {
				rs.next();
				String parameterName = (String) names.nextElement();
				String InsertQuery;
				if (parameterName == null || parameterName.equals(""))
					System.out.println("Parameter name is null");
				;
				System.out.println(parameterName);
				System.out.println(request.getParameter(parameterName));

				if (request.getParameter(parameterName).equals("Present")) {
					InsertQuery = "INSERT INTO rollproject.dayrecords values(?, ?, ?, ?);";
					System.out.println("Execute Update");
					stmtInsert = conn.prepareStatement(InsertQuery);
					stmtInsert.setString(1, getFormattedStringDate());
					stmtInsert.setString(2, rs.getString("firstname"));
					stmtInsert.setString(3, rs.getString("lastname"));
					stmtInsert.setString(4, "Present");
					stmtInsert.executeUpdate();
				} else if (request.getParameter(parameterName).equals("Late")) {
					InsertQuery = "INSERT INTO "
							+ "rollproject.dayrecords values(?,?,?,?);";
					System.out.println("Execute Update");
					stmtInsert = conn.prepareStatement(InsertQuery);

					stmtInsert.setString(1, getFormattedStringDate());
					stmtInsert.setString(2, rs.getString("firstname"));
					stmtInsert.setString(3, rs.getString("lastname"));
					stmtInsert.setString(4, "Sick");
					stmtInsert.executeUpdate();

				} else if (request.getParameter(parameterName).equals("Sick")) {
					InsertQuery = "INSERT INTO "
							+ "rollproject.dayrecords values(?, ?, ?,? " + ");";
					System.out.println("Execute Update");
					stmtInsert = conn.prepareStatement(InsertQuery);
					stmtInsert.setString(1, getFormattedStringDate());
					stmtInsert.setString(2, rs.getString("firstname"));
					stmtInsert.setString(3, rs.getString("lastname"));
					stmtInsert.setString(4, "Sick");
					stmtInsert.executeUpdate();
				} else {
					InsertQuery = "INSERT INTO "
							+ "rollproject.dayrecords values(?,?,?,?);";
					System.out.println("Execute Update");
					stmtInsert = conn.prepareStatement(InsertQuery);

					stmtInsert.setString(1, getFormattedStringDate());
					stmtInsert.setString(2, rs.getString("firstname"));
					stmtInsert.setString(3, rs.getString("lastname"));
					stmtInsert.setString(4, "Other");
					stmtInsert.executeUpdate();
				}

				System.out.println("While looped closed");
			}
			rs.close();
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/WEB-INF/client/duplicateEntry.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmtCreate.close();
				stmtInsert.close();
				stmtSelect.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!response.isCommitted()) {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("client/RoleRequestSuccess.jsp");
			dispatcher.forward(request, response);
		}
	}
}

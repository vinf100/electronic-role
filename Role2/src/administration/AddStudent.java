package administration;

import helperClasses.ConnectionSingleton;
import helperClasses.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddStudent
 */
public class AddStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			String query = "INSERT INTO rollproject.students (firstname, lastname, class) VALUES(?,?, ?);";
			Connection conn = ConnectionSingleton.getInstance(this).getConn();
			PreparedStatement prepstmt = conn.prepareStatement(query);
			prepstmt.setString(1, request.getParameter("firstname"));
			prepstmt.setString(2, request.getParameter("lastname"));
			prepstmt.setString(3, request.getParameter("classname"));
			prepstmt.executeUpdate();
			prepstmt.clearParameters();
			prepstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/WEB-INF/admin/FailPage.jsp");
			dispatcher.forward(request, response);
		}
		if (!response.isCommitted()) {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/WEB-INF/admin/SuccessPage.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}

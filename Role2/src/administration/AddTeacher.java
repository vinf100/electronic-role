package administration;

import helperClasses.ConnectionSingleton;
import helperClasses.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PreparedStatement initPreparedStatement(Connection conn, String query) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement(query);
			return prepStmt;
		} catch (SQLException e) {
			return null;
		}

	}


	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection conn = ConnectionSingleton.getInstance(this).getConn();
		String query = "INSERT INTO rollproject.teachers (firstname, lastname, username, password, classname) VALUES (?,?,?,?,?)";
		PreparedStatement prepStmt = initPreparedStatement(conn, query);
		System.out.println(request.getParameter("password"));
		System.out.println(request.getParameter("passwordre"));
		if (!request.getParameter("password").equals(
				request.getParameter("passwordre"))) {
			PrintWriter out = response.getWriter();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/AddTeacher.jsp");
			out.println("The password and the reconfirmed password are not the same");
			dispatcher.include(request,response);
			return;
		}
		try {
			prepStmt.setString(1, request.getParameter("firstname"));
			prepStmt.setString(2, request.getParameter("lastname"));
			prepStmt.setString(3, request.getParameter("username"));
			prepStmt.setString(4, request.getParameter("password"));
			prepStmt.setString(5, request.getParameter("classname"));
			prepStmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/WEB-INF/admin/FailPage.jsp");
			dispatcher.forward(request, response);
		} finally {
			PreparedStatement stmt[] = { prepStmt };
			Util.close(stmt);
		}
		if (!response.isCommitted()) {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/WEB-INF/admin/SuccessPage.jsp");
			dispatcher.forward(request, response);

		}
	}

}

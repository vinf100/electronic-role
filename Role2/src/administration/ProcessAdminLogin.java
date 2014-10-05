package administration;

import helperClasses.ConnectionSingleton;
import helperClasses.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ProcessAdminLogin
 */
public class ProcessAdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			HttpSession session = Util.checkSession(request, response, "/adminLogin.html");
			if(session == null){
				return;
			}
			String password = request.getParameter("password");
			String username = request.getParameter("username");
			Properties prop = new Properties();
			prop.load(getServletContext().getResourceAsStream(
					"/WEB-INF/config.properties"));
			synchronized (this) {
				if (prop.getProperty("username").equals(username)
						&& prop.getProperty("password").equals(password)) {
					request.getSession(true);	
					System.out.println("In if");
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("/adminMenu.jsp");
					dispatcher.forward(request, response);

				} else {
					System.out.println("In else");
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("/WEB-INF/admin/adminLoginFailed.jsp");
					dispatcher.forward(request, response);

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package client;

import helperClasses.Util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = Util
				.checkSession(request, response, "login.html");

		if (session != null) {
			session.invalidate();
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("login.html");
			dispatcher.forward(request, response);
		}
	}

}

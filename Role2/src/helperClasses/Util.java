package helperClasses;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Util {
	public static HttpSession checkSession(HttpServletRequest request,
			HttpServletResponse response, String url) throws ServletException,
			IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		}
		return session;
	}
	public static void checkSessionJsp(HttpSession session, HttpServletRequest request, HttpServletResponse response, String url){
		if(session.isNew()){
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void close(ResultSet rs[], PreparedStatement stmt[]) {
		try {
			int i = 0;
			while(rs.length < i){
				rs[i].close();
				i++;
			}
			int j = 0;
			while(stmt.length < j){
				stmt[j].close();
				j++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close( PreparedStatement stmt[]) {
		try {
			int j = 0;
			while(stmt.length < j){
				stmt[j].close();
				j++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

package administration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionFilterAdmin implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws java.io.IOException, ServletException {
		System.out.println("In Filter");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);
		if (session == null || session.isNew()) {
			RequestDispatcher dispatcher = req
					.getRequestDispatcher("/adminLogin.html");
			dispatcher.forward(request,response);
			System.out.println("In if");
		} else {
			System.out.println("In else");
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
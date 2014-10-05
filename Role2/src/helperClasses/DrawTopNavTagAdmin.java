package helperClasses;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class DrawTopNavTagAdmin extends SimpleTagSupport {
	@Override
	public void doTag() throws IOException {
	JspWriter out = getJspContext().getOut();
		out.println("<link rel = 'stylesheet' type = 'text/css' href = 'CSS/topnav.css'/>" +
				"<div>	<table> " +
				"<tr><td><a href='/Role/AdminMenu.jsp' target='_top'>HOME</a></td>" +
				"<td><a href='/Role/AddClass.jsp'>ADD CLASS</a></td>" +
				"<td><a href='/Role/AddStudent.jsp'>ADD STUDENT</a></td>" +
				"<td><a href='/Role/AddTeacher.jsp'>ADD TEACHER</a></td>" +
				"<td><a href='/Role/LogoutAdmin'>LOGOUT</a></td></tr>" +
				"<tr></table></div>");
	
	}
}

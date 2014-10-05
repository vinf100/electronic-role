package helperClasses;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class DrawTopNavTag extends SimpleTagSupport {
	@Override
	public void doTag() throws IOException {
		JspWriter out = getJspContext().getOut();
		out.println("<link rel = 'stylesheet' type = 'text/css' href = 'CSS/topnav.css'/>" +
				"<div>	<table> " +
				"<tr><td><a href='/Role/Menu.jsp' target='_top'>HOME</a></td>" +
				"<td><a href='/Role/TakeRole'>TAKE ROLE</a></td>" +
				"<td><a href='/Role/DisplayStudentRecords'>STUDENT RECORDS</a></td>" +
				"<td><a href = '/Role/Logout'>LOGOUT</a></td></tr>" +
				"<tr></table></div>");
	}
}

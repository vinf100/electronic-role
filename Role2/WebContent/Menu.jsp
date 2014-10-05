
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="custom" uri="/WEB-INF/custom.tld"%>
<%@ page import="helperClasses.Util"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="CSS/client/clientMenu.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Menu</title>


</head>
<body>
	
	<custom:TopNav />
	<h1>Welcome!</h1>
	<h3>This is the main menu of the eRoll.</h3>
	<br />
	<h3>You can access all of the menus from this page</h3>
	<p>To take the role for this day, click on the following link.</p>
	<a href="/Role/TakeRole">Take the Role</a>
	<p>To see student records for the last 30 days, click on the
		following link.</p>
	<a href="/Role/DisplayStudentRecords">See student records</a>
</body>
</html>
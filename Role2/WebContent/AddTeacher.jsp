<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="helperClasses.Util"%>
<%@ taglib prefix="custom" uri="/WEB-INF/custom.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


	<custom:TopNavAdmin />
	<form action="/Role/AddTeacher" method="POST">
		first name:<input name="firstname" /><br /> last name:<input
			name="lastname" /><br /> class name:<input name="classname" /><br />
		username:<input name="username" /><br /> password:<input
			type="password" name="password" /><br /> reconfirm password:<input
			type="password" name="passwordre"><br /> <input
			type="submit" />
	</form>

</body>
</html>
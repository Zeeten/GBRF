<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Likes</title>
</head>
<body>
	<center>
		<H1>Thanks for Your Likes You win Awards</H1>
		<table border="1">
			<tr>
				<td>You First Like is:</td>
				<td><%=request.getParameter("likes1")%></td>
			</tr>
			<tr>
				<td>You Second Like is:</td>
				<td><%=request.getParameter("likes2")%></td>
			</tr>
			<tr>
				<td>You Third Like is:</td>
				<td><%=request.getParameter("likes3")%></td>
			</tr>
		</table>
	</center>
</body>
</html>
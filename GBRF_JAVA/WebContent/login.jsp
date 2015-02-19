<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Like Page for Creative Book</title>
</head>
<body background="img/bg/bg.jpg">
<body>
	<center>
		<H1>Login</H1>
		<FORM ACTION="LoginCtl" METHOD="post">
			<table>
				<div style="color: red">
					<%
						if (request.getAttribute("message") != null)
							out.println(request.getAttribute("message"));
						else
							out.println("");
					%>
				</div>
				<tr>
					<td>User ID</td>
					<td><input name="login" type="text"></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input name="password" type="password"></td>
				</tr>
				<tr>
					<td>Book ID</td>
					<td><input name="bookId" type="text"></td>
				</tr>
				<tr>
				<td align="center" colspan="2"><input name="operation" value="SignIn"
						type="submit"></td>
				</tr>
			</table>
		</Form>
	</center>
</html>
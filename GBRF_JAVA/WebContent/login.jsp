<%@page import="com.ncs.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
<style type="text/css">
body {
	color: white;
}

td {
	font-size: 20px;
}

h1 {
	font-size: 35px;
}

H2 {
	font-size: 20px;
}
</style>
</head>

<body background="img/bg/bg.jpg">
	<center>
		<h1>Login</h1>

		<H2>
			<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
			</font>
		</H2>
		<FORM ACTION="LoginCtl" METHOD="post">
			<table>
				<tr>
					<td>Email :</td>
					<td><input name="email" type="text"></td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font></td>
				</tr>
				<tr>
					<td>Password :</td>
					<td><input name="password" type="password"></td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>
				<tr>
					<td colspan="2" align="right"><input name="operation"
						value="SignIn" type="submit"></td>
				</tr>
			</table>
		</form>
	</center>
</html>
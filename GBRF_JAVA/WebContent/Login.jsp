<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Like Page for Creative Book</title>
</head>
<body>
	<center>
		<H1>Login</H1>

		<FORM ACTION="LoginCtl" METHOD="post">
			<table>
				<tr>
					<td>KIB User ID</td>
					<td><input name="login" type="text"></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input name="password" type="text"></td>
				</tr>
				<tr>
					<td>Book ID</td>
					<td><input name="bookId" type="text"></td>
				</tr>
				<tr>
					<td colspan="2"><input name="operation" value="SignIn"
						type="submit"></td>
				</tr>

			</table>
		</form>
	</center>
</html>
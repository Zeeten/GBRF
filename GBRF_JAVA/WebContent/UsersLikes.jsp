<%@page import="com.ncs.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Users Likes Page</title>
<style type="text/css">
body {
	color: white;
}

td {
	font-size: 15px;
}

th {
	background-color: #E95810;
}

table {
	background-color: #F58750;
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
<body>
	<center>
		<H1>Read and WIN</H1>
		<H2>Part - I</H2>
		<font color="red"><%=ServletUtility.getErrorMessage("like1", request)%></font>
		&emsp; <font color="red"><%=ServletUtility.getErrorMessage("like2", request)%></font>
		&emsp; <font color="red"><%=ServletUtility.getErrorMessage("like3", request)%></font>
		<FORM ACTION="UsersLikesCtl.do" METHOD="post">
			<table width="100%" align="center" border="1">
				<tr>
					<th>Chapter</th>
					<th>Like1</th>
					<th>Like2</th>
					<th>Like3</th>
				</tr>
				<tr>
					<td>Chapter 1: A Skit with my Friend Jaspal Bhatti</td>
					<td align="center"><input type="radio" name="like1"
						value="Chapter 1"></td>
					<td align="center"><input type="radio" name="like2"
						value="Chapter 1"></td>
					<td align="center"><input type="radio" name="like3"
						value="Chapter 1"></td>
				</tr>
				<tr>
					<td>Chapter 2: FM2 factor and Defending the Youth</td>
					<td align="center"><input type="radio" name="like1"
						value="Chapter 2"></td>
					<td align="center"><input type="radio" name="like2"
						value="Chapter 2"></td>
					<td align="center"><input type="radio" name="like3"
						value="Chapter 2"></td>
				</tr>
				<tr>
					<td>Chapter 3: The Indian Bucket System</td>
					<td align="center"><input type="radio" name="like1"
						value="Chapter 3"></td>
					<td align="center"><input type="radio" name="like2"
						value="Chapter 3"></td>
					<td align="center"><input type="radio" name="like3"
						value="Chapter 3"></td>
				</tr>
				<tr>
					<td>Chapter 4: India is a Land of Many Verticals</td>
					<td align="center"><input type="radio" name="like1"
						value="Chapter 4"></td>
					<td align="center"><input type="radio" name="like2"
						value="Chapter 4"></td>
					<td align="center"><input type="radio" name="like3"
						value="Chapter 4"></td>
				</tr>
				<tr>
					<td>Chapter 5: Creative Politics Creative India</td>
					<td align="center"><input type="radio" name="like1"
						value="Chapter 5"></td>
					<td align="center"><input type="radio" name="like2"
						value="Chapter 5"></td>
					<td align="center"><input type="radio" name="like3"
						value="Chapter 5"></td>
				</tr>
				<tr>
					<td>Chapter 6: Social-Anti-Social Balance</td>
					<td align="center"><input type="radio" name="like1"
						value="Chapter 6"></td>
					<td align="center"><input type="radio" name="like2"
						value="Chapter 6"></td>
					<td align="center"><input type="radio" name="like3"
						value="Chapter 6"></td>
				</tr>
				<tr>
					<td>Chapter 7: Virtue of Skill Development</td>
					<td align="center"><input type="radio" name="like1"
						value="Chapter 7"></td>
					<td align="center"><input type="radio" name="like2"
						value="Chapter 7"></td>
					<td align="center"><input type="radio" name="like3"
						value="Chapter 7"></td>
				</tr>
				<tr>
					<td>Chapter 8: Vox Populi</td>
					<td align="center"><input type="radio" name="like1"
						value="Chapter 8"></td>
					<td align="center"><input type="radio" name="like2"
						value="Chapter 8"></td>
					<td align="center"><input type="radio" name="like3"
						value="Chapter 8"></td>
				</tr>
				<tr>
					<td>Chapter 9: The 'KAUL Plan' for Raising 'CSDP & FO'</td>
					<td align="center"><input type="radio" name="like1"
						value="Chapter 9"></td>
					<td align="center"><input type="radio" name="like2"
						value="Chapter 9"></td>
					<td align="center"><input type="radio" name="like3"
						value="Chapter 9"></td>
				</tr>
			</table>
			<INPUT TYPE="submit" VALUE="Save" name="operation">
		</form>
	</center>
</html>
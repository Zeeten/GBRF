<%@page import="com.ncs.bean.LikesBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.ncs.model.LikesModel"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Read & Win Part-I Awards</title>
<style type="text/css">
body {
	color: white;
}

td {
	font-size: 15px;
	text-align: center;
}

h1 {
	font-size: 35px;
}

th {
	background-color: #E95810;
}

table {
	background-color: #F58750;
}

H2 {
	font-size: 20px;
}
</style>
</head>
<body background="img/bg/bg.jpg">
<body>
	<center>
		<h1>Read & Win</h1>
		<h2>Part-I Awards</h2>
		<table border="1" width="100%">
			<tr>
				<th>Id</th>
				<th>Date</th>
				<th>Email Id</th>
				<th>Book Name</th>
				<th>Book No</th>
				<th>Like 1</th>
				<th>Like 2</th>
				<th>Like 3</th>
			</tr>
			<%
				List list = (List) request.getAttribute("dtoList");
				Iterator it = list.iterator();
				while (it.hasNext()) {
					LikesBean bean = (LikesBean) it.next();
			%>
			<tr>
				<td><%=bean.getId()%></td>
				<td><%=bean.getDate()%></td>
				<td><%=bean.getEmail()%></td>
				<td><%=bean.getBookName()%></td>
				<td><%=bean.getBookNo()%></td>
				<td><%=bean.getLike1()%></td>
				<td><%=bean.getLike2()%></td>
				<td><%=bean.getLike3()%></td>
			</tr>
			<%
				}
			%>
		</table>
		<br> <a href="WelcomeCtl.do">Home</a>
	</center>
</body>
</html>
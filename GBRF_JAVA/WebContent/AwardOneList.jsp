<%@page import="com.ncs.bean.AwardOneBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Release & Buy Award List-I</title>
<style type="text/css">
body {
	color: white;
}

td {
	font-size: 15px;
	text-align: center;
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
	<center>
		<h1>Release & Buy</h1>
		<h2>Award List-I</h2>
		<table border="1" width="100%">
			<tr>
				<th>#</th>
				<th>Order Id</th>
				<th>Date Added</th>
				<th>Customer Id</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th>Telephone</th>
				<th>Payment Name</th>
				<th>Payment Method</th>
				<th>Payment Code</th>
				<th>Total</th>
				<th>Currency</th>
			</tr>
			<%
				List list = (List) request.getAttribute("dtoList");
				Iterator it = list.iterator();
				int index = 0;
				while (it.hasNext()) {
					index++;
					AwardOneBean bean = (AwardOneBean) it.next();
			%>
			<tr>
				<td><%=index%></td>
				<td><%=bean.getId()%></td>
				<td><%=bean.getDateAdded()%></td>
				<td><%=bean.getCustomerId()%></td>
				<td><%=bean.getFirstName()%></td>
				<td><%=bean.getLastName()%></td>
				<td><%=bean.getEmail()%></td>
				<td><%=bean.getTelephone()%></td>
				<td><%=bean.getPaymentFirstName() + " "
						+ bean.getPaymentLastName()%></td>
				<td><%=bean.getPaymentMethod()%></td>
				<td><%=bean.getPaymentCode()%></td>
				<td><%=bean.getTotal()%></td>
				<td><%=bean.getCurrencyCode()%></td>
			</tr>
			<%
				}
			%>
		</table>
	</center>
</body>
</html>
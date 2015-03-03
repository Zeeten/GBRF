<%@page import="com.ncs.bean.AwardOneBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Award List</title>
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

H2 {
	font-size: 20px;
}
</style>
</head>
<body  background="img/bg/bg.jpg">
<center>
		<h1>Award List</h1>
		<table border="1" width="100%">
			<tr>
				<th>Id</th>
				<th>Customer Id</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th>Telephone</th>
				<th>Payment First Name</th>
				<th>Payment Last Name</th>
				<th>Payment Method</th>
				<th>Payment Code</th>
				<th>Order Status Id</th>
				<th>Total</th>
				<th>Currency Id</th>
			   <th>Currency Code</th>
				<th>Currency Value</th>
				<th>Date Added</th>
			</tr>
			<%
				List list = (List) request.getAttribute("dtoList");
				Iterator it = list.iterator();
				while (it.hasNext()) {
					AwardOneBean bean = (AwardOneBean) it.next();
			%>
			<tr>
				<td><%=bean.getId()%></td>
				<td><%=bean.getCustomerId()%></td>
				<td><%=bean.getFirstName()%></td>
				<td><%=bean.getLastName()%></td>
				<td><%=bean.getEmail()%></td>
				<td><%=bean.getTelephone()%></td>
				<td><%=bean.getPaymentFirstName()%></td>
				<td><%=bean.getPaymentLastName()%></td>
				<td><%=bean.getPaymentMethod()%></td>
				<td><%=bean.getPaymentCode()%></td>
				<td><%=bean.getOrderStatusId()%></td>
				<td><%=bean.getTotal()%></td>
				<td><%=bean.getCurrencyId()%></td>
				<td><%=bean.getCurrencyCode()%></td>
				<td><%=bean.getCurrencyValue()%></td>
				<td><%=bean.getDateAdded()%></td>
			</tr>
			<%
				}
			%>
		</table>
	</center>
</body>
</html>
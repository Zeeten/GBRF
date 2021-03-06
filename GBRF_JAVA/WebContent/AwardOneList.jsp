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
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
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
<body background="img/bg/bg.jpg">
<div class="container">
<div class="row">
<center>
		<h1 class="center">Award List</h1>
		</center>
		<div class="table-responsive">
		<table class="table table-bordered">
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
		</div>
		</div>
</div>
</body>
</html>
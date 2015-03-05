<%@page import="com.ncs.bean.InitialAwardCorpusBean"%>
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
		<h1>Initial Award Corpus List</h1>
			</center>
		<div class="table-responsive">
		<table class="table table-bordered">
			<tr>
				<th>Id</th>
				<th>Amount</th>
				<th>No Of Award</th>
				<th>Total Amount</th>
				<th>Part</th>

			</tr>
			<%
				List list = (List) request.getAttribute("dtoList");
				Iterator it = list.iterator();
				while (it.hasNext()) {
					InitialAwardCorpusBean bean = (InitialAwardCorpusBean) it
							.next();
			%>
			<tr>
				<td><%=bean.getId()%></td>
				<td><%=bean.getAmount()%></td>
				<td><%=bean.getNoOfAward()%></td>
				<td><%=bean.getTotalAmount()%></td>
				<td><%=bean.getPart()%></td>
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
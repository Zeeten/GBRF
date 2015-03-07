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
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>
	$(function() {
		$("#includedContent").load("admin.jsp");
	});
</script>
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
<style type="text/css">
body {
	color: white;
}

</style>
</head>
<body background="img/bg/bgbooks.jpg">
	<div id="includedContent"></div>
	<div style="margin-top: 50px">
	<div class="container">
<div class="row">
<center>
		<h1 style="color: #fff">Initial Award Corpus List</h1>
			</center>
		<div class="table-responsive">
		<table class="table table-bordered" style="color: #fff">
			<tr>
				<th>Id</th>
				<th>Amount</th>
				<th>No Of Awards</th>
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
</div>
</body>
</html>
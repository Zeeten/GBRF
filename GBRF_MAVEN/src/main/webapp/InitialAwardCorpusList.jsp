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
</head>
<body>
	<div id="includedContent"></div>
	<div class="container">
<div class="row" style="margin-top: 90px">
			<img alt="" src="img/logo.png" class="col-xs-offset-1"
				style="height: 100px; width: 250px"> 
			<div class="col-xs-offset-4">
				<hr>
			</div>
<center>
		<h2 >Initial Award Corpus List</h2>
		<div class="table-responsive">
		<table class="table table-bordered table-hover" style="width: 70%" >
			<tr>
				<th>#</th>
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
				</center>
		</div>
		</div>
</body>
</html>
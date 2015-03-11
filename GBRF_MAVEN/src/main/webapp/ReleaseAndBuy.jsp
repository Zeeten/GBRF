<%@page import="com.ncs.bean.ReleaseAndBuyBean"%>
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
		<h1 style="color: #fff">Release And Buy List</h1>
			
		<div class="table-responsive">
		<table class="table table-bordered" style="color: #fff;width: 70%">
			<tr>
				<th>Top 10</th>
				<th>Amount</th>
			</tr>
			<%
				List list = (List) request.getAttribute("dtoList");
				Iterator it = list.iterator();
				while (it.hasNext()) {
					ReleaseAndBuyBean bean = (ReleaseAndBuyBean) it
							.next();
			%>
			<tr>
			   <td><%=bean.getTop10()%></td>
				<td><%=bean.getAmount()%></td>
			</tr>
			<%
				}
			%>
		</table>
		</div>
				</center>
		</div>
		</div>

</div>
</body>
</html>
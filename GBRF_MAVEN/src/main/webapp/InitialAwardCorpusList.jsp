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
		<img alt="" src="img/logo.png"  style="height: 100px;width: 250px">
		<span class=" col-md-offset-2 " style="font-size: 25pt;">Initial Award Corpus List</span>

					<div class=" col-md-offset-3 " style="margin-top: -30px" >
<hr>
</div>
			</div>

		<div class="col-md-offset-3 table-responsive">
		<table class="table table-bordered table-hover" style="width: 70%" >
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
</body>
</html>
<%@page import="com.ncs.bean.RegisterPrintedBookBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registered Books</title>
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
		<h1 style="color: #fff">Registered Books</h1>
	<div class="table-responsive">
		<table class="table table-bordered" style="color: #fff" >
			<tr>
				<th>Book Id</th>
				<th>Book Name</th>
				<th>Email ID</th>
				<th>Date Of Purchase</th>
				<th>Mobile No</th>
			</tr>
			<%
				List list = (List) request.getAttribute("dtoList");
				Iterator it = list.iterator();
				while (it.hasNext()) {
					RegisterPrintedBookBean bean = (RegisterPrintedBookBean) it.next();
			%>
			<tr>
				<td><%=bean.getBookId()%></td>
				<td><%=bean.getBookName()%></td>
				<td><%=bean.getEmail()%></td>
				<td><%=bean.getDate()%></td>
				<td><%=bean.getMobileno()%></td>
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
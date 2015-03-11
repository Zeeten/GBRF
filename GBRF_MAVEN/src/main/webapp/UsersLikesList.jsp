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
<title>Your Likes</title>
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
		<h1 style="color: #fff">User Likes List</h1>
								<FORM  METHOD="post" class="form-horizontal">
						<div class="row">
					<label for="inputBookId" class="control-label  col-xs-1"
						style="color: #fff">Book ID :</label>
					<div class="col-xs-2">
						<input type="text" class="form-control" name="bookId" id="bookId"
							style="background: transparent; color: #fff;"
							placeholder="Book ID">
					</div>
					
				<label for="inputemailId" class="control-label col-xs-1"
						style="color: #fff">Email ID :</label>
					<div class="col-xs-2">
						<input type="text" class="form-control" name="emailId" id="emailId"
							style="background: transparent; color: #fff;"
							placeholder="Email ID">
					</div>
		
				<label for="inputbookName" class="control-label col-xs-2"
						style="color: #fff">Book Name:</label>
					<div class="col-xs-2">
						<input type="text" class="form-control" name="bookName" id="bookName"
							style="background: transparent; color: #fff;"
							placeholder="Book Name">
				</div>
					<div class="col-xs-2" >
						<input name="operation" value="Search" type="submit"
							style="background: transparent; color: #fff; width: 130px; height: 30px">
							</div>
							</div>
		
		</FORM>
		<br>
		<div class="table-responsive">
		<table class="table table-bordered" style="color: #fff" >
			<tr>
				<th>Id</th>
				<th>Email Id</th>
				<th>Book Name</th>
				<th>Book No</th>
				<th>Like 1</th>
				<th>Like 2</th>
				<th>Like 3</th>
				<th>Date</th>
			</tr>
			<%
				List list = (List) request.getAttribute("dtoList");
				Iterator it = list.iterator();
				while (it.hasNext()) {
					LikesBean bean = (LikesBean) it.next();
			%>
			<tr>
				<td><%=bean.getId()%></td>
				<td><%=bean.getEmail()%></td>
				<td><%=bean.getBookName()%></td>
				<td><%=bean.getBookNo()%></td>
				<td><%=bean.getLike1()%></td>
				<td><%=bean.getLike2()%></td>
				<td><%=bean.getLike3()%></td>
				<td><%=bean.getDate()%></td>
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
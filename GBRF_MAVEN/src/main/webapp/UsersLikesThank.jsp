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
<title>Thank For Likes</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>
	$(function() {
		$("#includedContent").load("admin.jsp");
	});
</script>

</head>
<body >
<div id="includedContent"></div>
		<div class="row" style="margin-top: 90px">
			<img alt="" src="img/logo.png" class="col-xs-offset-1"
				style="height: 100px; width: 250px"> 
			<div class="col-xs-offset-4">
				<hr>
			</div>
	<center>
	<font color="green"><p><b>Thank you to select best 3 chapters.</b></p>
	<p><b>After April, 30 you will be able to see winner list.</b></p></font>
	</center> 
	</div>
</body>
</html>
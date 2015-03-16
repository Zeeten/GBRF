<%@page import="com.ncs.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forget Password</title>
<!-- Latest compiled and minified CSS -->
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="js/jquery-1.10.2.js"></script>
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link
	href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="http://blueimp.github.io/Gallery/css/blueimp-gallery.min.css">
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	</nav>
	<div class="container">
		<div class="row" style="margin-top: 90px">
			<img alt="" src="img/logo.png" class="col-xs-offset-1"
				style="height: 100px; width: 250px"> <a
				class="btn btn-info  col-xs-offset-4" href="LoginCtl"> <i
				class="glyphicon glyphicon-home"></i> Home
			</a> <a class="btn btn-info" href="RegisterPrintedBookCtl"> <i
				class="glyphicon glyphicon-user"></i> Register Printed Book
			</a>
			<div class="col-xs-offset-4">
				<hr>
			</div>
				<h2 class="col-xs-offset-2">Forget Password</h2>
				<H2>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font> <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H2>
				<FORM ACTION="ForgetPasswordCtl" METHOD="post"
					class="form-horizontal">
					<div class="row">
						<label for="inputEmail"
							class="control-label col-xs-offset-2 col-xs-2">Email :</label>
						<div class=" col-xs-3">
							<input type="email" class="form-control" id="email" name="email"
								placeholder="Email ID" > <font
								color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font>
						</div>
						
							<input name="operation" value="Send" type="submit"
								class="btn btn-success">
						
					</div>
				</form>
		</div>
	</div>
</html>
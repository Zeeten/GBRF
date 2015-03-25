<%@page import="com.ncs.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>
	$(function() {
		$("#includedContent").load("main.jsp");
	});
</script>
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">

</head>
<body>
<div id="includedContent"></div>
 
	<div class="container">

		<div class="row" style="margin-top: 90px">
		<img alt="" src="img/logo.png"  style="height: 100px;width: 272px">
		<span class=" col-md-offset-2 " style="font-size: 25pt;">Login</span>

					<div class=" col-md-offset-3 " style="margin-top: -40px" >
<hr>
</div>
				</div>
	<h3 class=" col-md-offset-4 "><font color="red" >  <%=ServletUtility.getErrorMessage(request)%>
					</font> <font color="green" > <%=ServletUtility.getSuccessMessage(request)%>
					</font></h3>
				<FORM ACTION="LoginCtl" METHOD="post" class="form-horizontal">
					<div class="form-group">
					<div class="col-md-1">
					</div>
						<label for="inputEmail"
							class="control-label col-md-3">Email *</label>
								<div class="col-md-3">
							<input type="email" class="form-control" id="email" name="email"
								placeholder="Email ID">
								 <font
								color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font>
						</div>
					</div>
					<div class="form-group ">
					<div class="col-md-1">
					</div>
						<label for="inputPassword"
							class="control-label col-md-3">Password *</label>
							<div class="col-md-3">
							<input type="password" class="form-control" name="password"
								id="password" placeholder="Password"> <font color="red">
								<%=ServletUtility.getErrorMessage("password", request)%></font>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-offset-4 col-md-7">
							<button name="operation" class="btn btn-success" value="Sign In" type="submit">
							<span class="btn-save-label">
						<i class="glyphicon glyphicon-ok-circle"></i>
						</span>
						Sign In
							</button>
							
							<a class="btn btn-info" href="ForgetPasswordCtl">
<i class="glyphicon glyphicon-edit"></i>
Forget Password
</a>
						</div>
						
					</div>
				</form>
			
		</div>


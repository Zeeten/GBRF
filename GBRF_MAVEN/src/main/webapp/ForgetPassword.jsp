<%@page import="com.ncs.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forget Password</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<style type="text/css">
body {
	color: white;
}
</style>
</head>

<body background="img/bg/bgbooks.jpg">
	<div class="container">
		<div class="row">
			<center>
				<h1>Forget Password</h1>

				<H2>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font> <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H2>
				<FORM ACTION="ForgetPasswordCtl" METHOD="post" class="form-horizontal">
				<div class="row">
						<label for="inputEmail"
							class="control-label col-xs-offset-2 col-xs-2"
							style="color: #fff">Email :</label>
						<div class=" col-xs-3">
							<input type="email" class="form-control" id="email" name="email"
								placeholder="Enter Email"
								style="background: transparent; color: #fff;"> <font
								color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font>
						</div>
				
						<div class="col-xs-1">
						<input name="operation" value="Send" type="submit"
							style="background: transparent; color: #fff; width: 130px; height: 30px">
						</div>
							</div>
				</form>
			</center>
		</div>
	</div>
</html>
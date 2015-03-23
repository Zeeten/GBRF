<%@page import="com.ncs.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forget Password</title>
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
		<img alt="" src="img/logo.png"  style="height: 100px;width: 240px">
		<span class=" col-md-offset-2 " style="font-size: 25pt;">Forget Password</span>

					<div class=" col-md-offset-3 " style="margin-top: -40px" >
<hr>
</div>
				</div>
<h3 class=" col-md-offset-4 "><font color="red" > <%=ServletUtility.getErrorMessage(request)%>
					</font> <font color="green" > <%=ServletUtility.getSuccessMessage(request)%>
					</font></h3>
				<FORM ACTION="ForgetPasswordCtl" METHOD="post"
					class="form-horizontal">
						<label for="inputEmail"
							class="control-label col-md-offset-2 col-md-2">Email </label>
						<div class=" col-md-3">
							<input type="email" class="form-control" id="email" name="email"
								placeholder="Email ID" > <font
								color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font>
						</div>
							<div class="form-group">
							<input name="operation" value="Send" type="submit"
								class="btn btn-success">
					</div>
						
				</form>
		</div>
</html>
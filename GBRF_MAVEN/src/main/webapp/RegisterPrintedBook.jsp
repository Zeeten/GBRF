<%@page import="com.ncs.util.HTMLUtility"%>
<%@page import="com.ncs.bean.UserBean"%>
<%@page import="com.ncs.bean.BooksBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.ncs.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register Print Book</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="js/bootstrap.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
  <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
	<link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" rel="stylesheet">
	<link rel="stylesheet" href="http://blueimp.github.io/Gallery/css/blueimp-gallery.min.css">
<script>
	$(function() {
		$("#includedContent").load("admin.jsp");
	});
</script>
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">

	<script>
$(function() {
    $('.date-picker').datepicker( {
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true,
        dateFormat: 'dd/mm/yy',
      
    });
});
</script>
</head>
<body >
	<div id="includedContent"></div>
		<div class="container">
		<div class="row" style="margin-top: 65px">

<%
		
				if(session.getAttribute("session")==null){
					%>

			<img alt="" src="img/logo.png" class="col-xs-offset-1" style="height: 100px;width: 250px">
		<a class="btn btn-info  col-xs-offset-4"  href="LoginCtl">
<i class="glyphicon glyphicon-home"></i>
 Home
</a>
	<a class="btn btn-info" href="ForgetPasswordCtl">
<i class="glyphicon glyphicon-edit"></i>
Forget Password
</a>
<div class="col-xs-offset-4" >
<hr>
</div>

<% }else{ %>

		<img alt="" src="img/logo.png" class="col-xs-offset-1" style="height: 100px;width: 250px">
		<div class="col-xs-offset-4" >
<hr>
</div>
<%} %>
		<h2 class="col-xs-offset-4">Register Printed Book</h2>
<h3 class="col-xs-offset-3"><font color="red" > <%=ServletUtility.getErrorMessage(request)%></font></h3>
			<FORM ACTION="RegisterPrintedBookCtl" METHOD="post" class="form-horizontal">

			<div class="form-group">
					<label for="inputBook" class="control-label col-xs-offset-2 col-xs-2">Book</label>
					<div class="col-xs-3">
						<select class="form-control" id="bookName" name="bookName">
							<option value="">--Select--</option>
							<%
								List list = ServletUtility.getList(request);
								Iterator it = list.iterator();
								while (it.hasNext()) {
									BooksBean bean = (BooksBean) it.next();
							%>
							<option value="<%=bean.getBookName()%>"><%=bean.getBookName()%></option>
							<%
								}
							%>
							
						</select> 
						<font color="red"> <%=ServletUtility.getErrorMessage("bookName", request)%></font>
					</div>
				</div>
				<div class="form-group">
					<label for="inputBookId" class="control-label col-xs-offset-2 col-xs-2">Book ID :</label>
					<div class="col-xs-3">
						<input type="text" class="form-control" name="bookId" id="bookId"
							placeholder="Book ID"> <font color="red"> <%=ServletUtility.getErrorMessage("bookId", request)%></font>
					</div>
				</div>
				
					<div class="form-group">
					<label for="inputDateOfPurchase" class="control-label col-xs-offset-2 col-xs-2">Date of Purchase :</label>
					<div class="col-xs-3">
						<input type="text"  name="dateofpurchase" id="dateofpurchase"
							 class="form-control date-picker"
							placeholder="Date of Purchase"><font color="red"> <%=ServletUtility.getErrorMessage("dateofpurchase", request)%></font>
					</div>
				</div>
				
				<%
		
				if(session.getAttribute("session")==null){
					%>
						<div class="form-group">
					<label for="inputfirstName" class="control-label col-xs-offset-2 col-xs-2"
					>First Name :</label>
					<div class="col-xs-3">
						<input type="text" class="form-control" name="firstName" id="firstName"
							placeholder="First Name"> <font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font>
					</div>
				</div>	
						<div class="form-group">
					<label for="inputlastName" class="control-label col-xs-offset-2 col-xs-2">Last Name :</label>
					<div class="col-xs-3">
						<input type="text" class="form-control" name="lastName" id="lastName"
							placeholder="Last Name"> <font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font>
					</div>
				</div>
					<div class="form-group">
					<label for="inputMobileNo" class="control-label col-xs-offset-2 col-xs-2">Mobile No :</label>
					<div class="col-xs-3">
						<input type="text" class="form-control" name="mobileNo" id="mobileNo"
							placeholder="Mobile No"> <font color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font>
					</div>
				</div>
						<div class="form-group">
					<label for="inputemail" class="control-label col-xs-offset-2 col-xs-2">Login ID :</label>
					<div class="col-xs-3">
						<input type="email" class="form-control" name="email" id="email"
							placeholder="Login ID"> <font color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font>
					</div>
				</div>
					<div class="form-group">
					<label for="inputpassword" class="control-label col-xs-offset-2 col-xs-2">Password :</label>
					<div class="col-xs-3">
						<input type="password" class="form-control" name="password" id="password"
							placeholder="Password"> <font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font>
					</div>
				</div>
				<%
				}
				%>
				<div class="form-group">
					<div class="col-xs-offset-5 ">
						<button name="operation" class="btn icon-btn-save btn-success" value="Register" type="submit">
						<span class="btn-save-label">
						<i class="glyphicon glyphicon-floppy-disk"></i>
						</span>
						save</button>
					<%
		
				if(session.getAttribute("session")!=null){
					%>
					<input name="operation" class="btn btn-info" value="My Registered Books" type="submit">
											<%
				}
				%>
					</div>
				</div>

				
			</FORM>
			</div>
					</div>

</body>
</html>
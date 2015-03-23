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
 <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
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
    $('.date-picker').datepicker( );
});
</script>
</head>
<body >
	<div id="includedContent"></div>
		<div class="container">
		
<%
		
				if(session.getAttribute("session")==null){
					%>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
	<div class="navbar-header" style="margin-top: 5px;margin-bottom: 5px">
				<!-- You'll want to use a responsive image option so this logo looks good on devices - I recommend using something like retina.js (do a quick Google search for it and you'll find it) -->
					        <button type="button" data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">

            <span class="sr-only">Toggle navigation</span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>

        </button>
					
					<img src="img/gbrflogo.png" style="height: 50px;" >
		
			</div>

			<div id="navbarCollapse" class="collapse navbar-collapse navbar-ex1-collapse">
			
			<ul class="nav navbar-nav">
			  <li><a href="LoginCtl"> HOME</a></li>
			  <li><a href="RegisterPrintedBookCtl"> 
 Register Printed Book</a></li>
   	<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Read and Like Part -I Award List<b class="caret"></b></a>
						<ul class="dropdown-menu">
	
										<li class="dropdown-submenu">
							<a tabindex="-1" href="#">Read and Like Award
									Part -I</a>
							 <ul class="dropdown-menu">
								<li><a href="PartOneBestChapterFirstListCtl">Best First Chapter User List</a></li>
							<li><a href="PartOneBestChapterSecondListCtl">Best Second Chapter User List</a></li>
							<li><a href="PartOneBestChapterThirdListCtl">Best Third Chapter User List</a></li>
							 </ul>
							</li>
										
						</ul></li>
 	  <li><a href="ReleaseAndBuyAwardListCtl"> Release and Buy Award List</a></li>
			  </ul>
			</div>
	</div>
	</nav>


<% } %>
	<div class="row" style="margin-top: 90px">
		<img alt="" src="img/logo.png"  style="height: 100px;width: 250px">
		<span class=" col-md-offset-2 " style="font-size: 25pt;">Register Printed Book</span>

					<div class=" col-md-offset-3 " style="margin-top: -40px" >
<hr>
</div>
				</div>
				<h3 class=" col-md-offset-4 "><font color="red" > <%=ServletUtility.getErrorMessage(request)%>
			</font></h3>
	
			<FORM ACTION="RegisterPrintedBookCtl" METHOD="post" class="form-horizontal">

			<div class="form-group">
					<label for="inputBook" class="control-label col-md-offset-3 col-md-2">Book</label>
					<div class="col-md-3">
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
					<label for="inputBookId" class="control-label col-md-offset-3 col-md-2">Book ID</label>
					<div class="col-md-3">
						<input type="text" class="form-control" name="bookId" id="bookId"
							placeholder="Book ID"> <font color="red"> <%=ServletUtility.getErrorMessage("bookId", request)%></font>
					</div>
				</div>
				
					<div class="form-group">
					<label for="inputDateOfPurchase" class="control-label col-md-offset-3 col-md-2">Date of Purchase</label>
					<div class="col-md-3">
						<input type="text"  name="dateofpurchase" id="dateofpurchase"
							 class="form-control date-picker"
							placeholder="Date of Purchase"><font color="red"> <%=ServletUtility.getErrorMessage("dateofpurchase", request)%></font>
					</div>
				</div>
				
				<%
		
				if(session.getAttribute("session")==null){
					%>
						<div class="form-group">
					<label for="inputfirstName" class="control-label col-md-offset-3 col-md-2"
					>First Name</label>
					<div class="col-md-3">
						<input type="text" class="form-control" name="firstName" id="firstName"
							placeholder="First Name" > <font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font>
					</div>
				</div>	
						<div class="form-group">
					<label for="inputlastName" class="control-label col-md-offset-3 col-md-2">Last Name</label>
					<div class="col-md-3">
						<input type="text" class="form-control" name="lastName" id="lastName"
							placeholder="Last Name"> <font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font>
					</div>
				</div>
					<div class="form-group">
					<label for="inputMobileNo" class="control-label col-md-offset-3 col-md-2">Mobile No</label>
					<div class="col-md-3">
						<input type="text" class="form-control" name="mobileNo" id="mobileNo"
							placeholder="Mobile No"> <font color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font>
					</div>
				</div>
						<div class="form-group">
					<label for="inputemail" class="control-label col-md-offset-3 col-md-2">Login ID</label>
					<div class="col-md-3">
						<input type="email" class="form-control" name="email" id="email"
							placeholder="Login ID"> <font color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font>
					</div>
				</div>
					<div class="form-group">
					<label for="inputpassword" class="control-label col-md-offset-3 col-md-2">Password</label>
					<div class="col-md-3">
						<input type="password" class="form-control" name="password" id="password"
							placeholder="Password"> <font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font>
					</div>
				</div>
				<%
				}
				%>
		
					<%
		
				if(session.getAttribute("session")!=null){
					%>
				<div class="form-group">
					<div class="col-md-offset-5 " >
						<button name="operation" style="margin-left: 20px" class="btn icon-btn-save btn-success" value="Register" type="submit">
						<span class="btn-save-label">
						<i class="glyphicon glyphicon-floppy-disk"></i>
						</span>
						save</button>
					<input name="operation" class="btn btn-info" value="My Registered Books" type="submit">
							</div>
				</div>					<%
				}else{
				%>
				<div class="form-group">
				<div class="col-md-offset-6 ">
						<button name="operation" class="btn icon-btn-save btn-success" value="Register" type="submit">
						<span class="btn-save-label">
						<i class="glyphicon glyphicon-floppy-disk"></i>
						</span>
						save</button>
					</div>
				</div>
				
				<%} %>
				

				
			</FORM>
			</div>

	

</body>
</html>
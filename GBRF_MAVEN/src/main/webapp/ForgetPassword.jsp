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
	<style type="text/css">
.dropdown-submenu {
    position:relative;
}
.dropdown-submenu>.dropdown-menu {
    top:0;
    left:100%;
    margin-top:-6px;
    margin-left:-1px;
    -webkit-border-radius:0 6px 6px 6px;
    -moz-border-radius:0 6px 6px 6px;
    border-radius:0 6px 6px 6px;
}
.dropdown-submenu:hover>.dropdown-menu {
    display:block;
}
.dropdown-submenu>a:after {
    display:block;
    content:" ";
    float:right;
    width:0;
    height:0;
    border-color:transparent;
    border-style:solid;
    border-width:5px 0 5px 5px;
    border-left-color:#cccccc;
    margin-top:5px;
    margin-right:-10px;
}
.dropdown-submenu:hover>a:after {
    border-left-color:#ffffff;
}
.dropdown-submenu.pull-left {
    float:none;
}
.dropdown-submenu.pull-left>.dropdown-menu {
    left:-100%;
    margin-left:10px;
    -webkit-border-radius:6px 0 6px 6px;
    -moz-border-radius:6px 0 6px 6px;
    border-radius:6px 0 6px 6px;
}
</style>
</head>
<body>
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
							<li><a href="ReadLikeAwardPartOneCtl">Best First Chapter User List</a></li>
							<li><a href="#">Best Second Chapter User List</a></li>
							<li><a href="#">Best Third Chapter User List</a></li>
							 </ul>
							</li>
										
						</ul></li>
 	  <li><a href="ReleaseAndBuyAwardListCtl"> Release and Buy Award List</a></li> 
			  </ul>
			</div>
	</div>
	</nav>
	<div class="container">
	<div class="row" style="margin-top: 90px">
	<div class="col-md-offset-1 col-md-4">
			<img alt="" src="img/logo.png"
				style="height: 100px; width: 250px">
				</div>
</div>
		<div class="row" >
<div class="col-md-4"></div>
<div class="col-md-8" >
<hr>
</div>
</div>

<div class="row" >
<div class="col-md-4"></div>
				<h2 class="col-md-8">Forget Password</h2>
				</div>
						<div class="row" >
<div class="col-md-4"></div>
				<h3 class="col-md-8"><font color="red" > <%=ServletUtility.getErrorMessage(request)%>
					</font> <font color="green" > <%=ServletUtility.getSuccessMessage(request)%>
					</font></h3>
			</div>
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
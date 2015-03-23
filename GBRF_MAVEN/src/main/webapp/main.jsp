<%@page import="com.ncs.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Main Page</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="js/jquery-1.10.2.js"></script>
<script src="js/bootstrap.js"></script>
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
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
 </body>
 </html>
 
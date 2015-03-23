<%@page import="com.ncs.bean.UserBean"%>
<html>
<head>
<title>Administrator Page</title>
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
<body >
<%
if(session.getAttribute("session")!=null){
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
			<div class="navbar-header" style="margin-top: 13px;">
          <!-- You'll want to use a responsive image option so this logo looks good on devices - I recommend using something like retina.js (do a quick Google search for it and you'll find it) -->
				<strong> <font color="white">GBRF : <%=( session.getAttribute("firstname"))+""+(session.getAttribute("lastname"))%></font>
				</strong>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
			<div id="navbarCollapse" class="collapse navbar-collapse navbar-ex1-collapse">
          <ul class="nav navbar-nav">
					<li><a href="WelcomeCtl.do"> HOME</a></li>
				<%
		
				if(session.getAttribute("email").equals("admin@nenosystems.com")){
					%>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Register Printed Book<b class="caret"></b></a>
              <ul class="dropdown-menu">
							<li><a href="RegisterPrintedBookCtl">Register Printed Book</a></li>
							<li><a href="MyRegisteredBooksCtl">My Registered Books</a></li>
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Read and Like<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="UsersLikesCtl">Read and Like Part -I</a></li>
							<li><a href="MyUsersLikesListCtl">My Read and Like</a></li>
						</ul></li>
					
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Awards<b class="caret"></b></a>
						<ul class="dropdown-menu">
						<!-- 	<li><a href="UsersLikesListOneCtl">Read and Like Award
									Part -I</a></li>
							<li><a href="UsersLikesListTwoCtl">Read and Like Award
									Part -II</a></li> -->
									<!-- 	<li><a href="ReadLikeAwardPartOneCtl">Read and Like Award
									Part -I</a></li> -->
									
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
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Award Scheme<b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="InitialAwardCorpusListCtl">Initial Award
									Corpus</a></li>
									<li><a href="ReleaseAndBuyCtl">Release and Buy</a></li>
						</ul></li>
						
						<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Admin<b class="caret"></b></a>
						<ul class="dropdown-menu">
						<li><a href="UsersLikesListCtl">Read and Like List</a></li>
									<li><a href="RegisteredBooksCtl">Registered Books</a></li>
						<li><a href="UserListCtl">User List</a></li>
						<li><a href="BookListCtl">Book List</a></li>
						<li><a href="BookChaptersCtl">Book Chapters List</a></li>
							<li class="dropdown-submenu">
							<a tabindex="-1" href="#">Future Usage</a>
							 <ul class="dropdown-menu">
							<li><a href="ReleaseAndBuyAwardListCtl">Release and Buy Award</a></li>
							<li><a href="UsersLikesPartTwoCtl">Read and Like Part -II</a></li>
							<li><a href="ReadLikeAwardPartTwoCtl">Read and Like Award
									Part -II</a></li>
							 </ul>
							</li>
						</ul></li>
							
						<%
				}else
				{
				%>
				
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Register Printed Book<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="RegisterPrintedBookCtl">Register Printed Book</a></li>
							<li><a href="MyRegisteredBooksCtl">My Registered Books</a></li>
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Read and Like<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="UsersLikesCtl">Read and Like Part -I</a></li>
							<li><a href="MyUsersLikesListCtl">My Read and Like</a></li>
						</ul></li>

					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Awards<b class="caret"></b></a>
						<ul class="dropdown-menu">
									<!-- 	<li><a href="ReadLikeAwardPartOneCtl">Read and Like Award
									Part -I</a></li> -->
									
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
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Award Scheme<b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="InitialAwardCorpusListCtl">Initial Award
									Corpus</a></li>
									<li><a href="ReleaseAndBuyCtl">Release and Buy</a></li>
						</ul></li>
							<% } %>
					<li><a href="LogoutCtl">Logout</a></li>
          </ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container -->
  </nav>
<%
				}
				%>
</body>
</html>
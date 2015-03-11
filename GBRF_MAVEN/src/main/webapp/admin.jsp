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
</head>
<body background="img/bg/bgbooks.jpg">
<%
if(session.getAttribute("session")!=null){
					%>
 <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
			<div class="navbar-header" style="margin-top: 13px;">
          <!-- You'll want to use a responsive image option so this logo looks good on devices - I recommend using something like retina.js (do a quick Google search for it and you'll find it) -->
				<strong> <font color="white">GBRF : <%=( session.getAttribute("firstname"))%></font>
				</strong>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
          <ul class="nav navbar-nav">
					<li><a href="WelcomeCtl.do"> HOME</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Register Printed Book<b class="caret"></b></a>
              <ul class="dropdown-menu">
							<li><a href="RegisterPrintedBookCtl">Register Printed Book</a></li>
							<li><a href="MyRegisteredBooksCtl">My Registered Books</a></li>
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Read and Like<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="UsersLikesCtl">Part -I</a></li>
							<li><a href="UsersLikesPartTwoCtl">Part -II</a></li>
							<li><a href="MyUsersLikesListCtl">My Read and Like</a></li>
						</ul></li>

					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Awards<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="AwardOneListCtl">Release and Buy Award</a></li>
							<li><a href="UsersLikesListOneCtl">Read and Like Award
									Part -I</a></li>
							<li><a href="UsersLikesListTwoCtl">Read and Like Award
									Part -II</a></li>
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
						<li><a href="UsersLikesListCtl">Read and Like</a></li>
							<li><a href="UsersLikesListOneCtl">Read and Like
									Part -I</a></li>
							<li><a href="UsersLikesListTwoCtl">Read and Like
									Part -II</a></li>
									<li><a href="RegisteredBooksCtl">Registered Books</a></li>
						<li><a href="UserListCtl">User List</a></li>
						</ul></li>
							
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
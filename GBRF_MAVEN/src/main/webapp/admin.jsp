<%@page import="com.ncs.bean.UserBean"%>
<html>
<head>
<title>Administrator Page</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
    <script src="js/jquery-1.10.2.js"></script>
    <script src="js/bootstrap.js"></script>
        <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
</head>
<body background="img/bg/bgbooks.jpg">

 <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
   <div class="navbar-header" style="margin-top: 13px;" >
          <!-- You'll want to use a responsive image option so this logo looks good on devices - I recommend using something like retina.js (do a quick Google search for it and you'll find it) -->
        <strong>
<font color="white" >Welcome :
			<%=((UserBean) session.getAttribute("user")).getFullName()%></font>
</strong>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
          <ul class="nav navbar-nav">
            <li ><a href="WelcomeCtl.do"> HOME</a></li>
          <li ><a href="UsersLikesCtl"> Users Likes Page</a></li>
           <li class="dropdown" >
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Users Likes List<b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="UsersLikesListCtl">All Users Likes List</a></li>
                <li><a href="UsersLikesListOneCtl">Users Likes List One</a></li>
                <li ><a href="UsersLikesListTwoCtl">Users Likes List Two</a></li>
              </ul>
            </li>
 
            <li ><a href="AwardOneListCtl">Award One List</a></li>
             <li ><a href="InitialAwardCorpusListCtl">Initial Award Corpus List</a></li>
              <li ><a href="LogoutCtl">Logout</a></li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container -->
  </nav>

</body>
</html>
<%@page import="com.ncs.util.HTMLUtility"%>
<%@page import="com.ncs.bean.ReadLikeAwardPartOneBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.ncs.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Best Third Chapter User Like List</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>
	$(function() {
		$("#includedContent").load("admin.jsp");
	});
</script>
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
<style type="text/css">
.table th {
   text-align: center;   
}
</style>
</head>
<body>
	<div id="includedContent"></div>

		<div class="container">
			<div class="row" style="margin-top: 90px">
	
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
	<div class="row" >
<div class="col-md-3"></div>
				<h2 class="col-md-8">Best Third Chapter User Like Award Part-I List  </h2>
				</div>
				<br>
						<FORM  METHOD="post" action="PartOneBestChapterThirdListCtl" class="form-inline">
							<jsp:useBean id="bean" class="com.ncs.bean.BooksBean"
			scope="request"></jsp:useBean>

	<%-- 	<%
			List bookList = (List) request.getAttribute("bookList");
		%>
			 <div class="form-group">
				<label for="inputname" class="control-label col-md-4" >Book Name</label>
					<div class="control-label col-md-2" >
					<%=HTMLUtility.getList("bookName",
					bean.getBookName(), bookList)%>
				</div>
</div>
<div class="form-group">
		<button name="operation" value="Search" type="submit" class="col-md-offset-1 btn btn-info" style="margin-top: 7px">
							<span class="btn-save-label">
						<i class="glyphicon glyphicon-search"></i>
						</span>
						Search
						</button>
</div>
		<br><br> --%>
		
								  <div class="form-group">
					<label for="inputBookId" class="control-label  col-md-4">Book No</label>
					<div class="col-md-2">
						<input type="text" class="form-control" name="bookId" id="bookId"
							placeholder="Book No">
					</div>
					</div>
					<div class="form-group">
				<label for="inputemailId" class="control-label col-md-4">Email ID</label>
					<div class="col-md-2">
						<input type="text" class="form-control" name="emailId" id="emailId"
							placeholder="Email ID">
					</div>
		</div>

				<div class="form-group">
						<button name="operation" value="Search" type="submit" class="col-md-offset-1 btn btn-info">
							<span class="btn-save-label">
						<i class="glyphicon glyphicon-search"></i>
						</span>
						Search
						</button>
						</div>
	
		<br>
		<br>
					<div class="table-responsive">
						<table class="table table-bordered table-hover text-center" >
							<tr >
								<th>#</th>
								<th>First Name</th>
								<th>Last Name</th>
								<th>Email</th>
								<th>Book No</th>
								<th>Submit Date</th>
							</tr>
							<%
							List partOneList = (List) request.getAttribute("dtoList");
							if(partOneList.size()==0 ||partOneList == null){
								%>	
												<tr>
					<td colspan="6"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
				</tr>	
							<% }
							%>
					
						<% 
							int pageNo = ServletUtility.getPageNo(request);
							int pageSize = ServletUtility.getPageSize(request);
							int index = ((pageNo - 1) * pageSize)+1 ;
								List list = (List) request.getAttribute("dtoList");
								Iterator it = list.iterator();
								while (it.hasNext()) {
								
									ReadLikeAwardPartOneBean partOnebean = (ReadLikeAwardPartOneBean) it
											
											.next();
							%>
							<tr>
								<td><%=index++%></td>
								<td><%=partOnebean.getFirstName()%></td>
								<td><%=partOnebean.getLastName()%></td>
								<td><%=partOnebean.getEmail()%></td>
								<td><%=partOnebean.getBookNo()%></td>
								<td><%=partOnebean.getSubmitDate()%></td>
							</tr>
							<%
								}
							%>
						</table>
									<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						value="Previous" class="btn btn-info"></td>
					<td align="right"><input type="submit" name="operation"
						value="Next" class="btn btn-info"></td>
				</tr>
			</table>
				<input type="hidden" name="pageNo" value="<%=pageNo%>"><input
				type="hidden" name="pageSize" value="<%=pageSize%>">
					</div>
												
		</FORM>
	
			</div>
		</div>

</body>
</html>
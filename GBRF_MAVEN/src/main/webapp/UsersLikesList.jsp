<%@page import="com.ncs.util.ServletUtility"%>
<%@page import="com.ncs.bean.LikesBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.ncs.model.LikesModel"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Likes</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>
	$(function() {
		$("#includedContent").load("admin.jsp");
	});
</script>
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">

</head>
<body>
	<div id="includedContent"></div>
	<div class="container">
<div class="row" style="margin-top: 90px">
		<div class="row" >
<div class="col-md-4"></div>
				<h2 class="col-md-8">My Reads and Likes List</h2>
				</div>
								<FORM  METHOD="post" class="form-inline">
                     <div class="form-group">
					<label for="inputBookId" class="control-label  col-md-4">Book ID</label>
					<div class="col-md-2">
						<input type="text" class="form-control" name="bookId" id="bookId"
							placeholder="Book ID">
					</div>
					</div>
					  <div class="form-group">
				<label for="inputemailId" class="control-label col-md-4"
						>Email ID</label>
					<div class="col-md-2">
						<input type="text" class="form-control" name="emailId" id="emailId"
							placeholder="Email ID">
					</div>
		</div>
		  <div class="form-group">
				<label for="inputbookName" class="control-label col-md-5">Book Name</label>
					<div class="col-md-2">
						<input type="text" class="form-control" name="bookName" id="bookName"
							placeholder="Book Name">
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
		
		<br><br>
		<div class="table-responsive">
		<table class="table table-bordered table-hover" >
			<tr>
				<th>#</th>
				<th>Email Id</th>
				<th>Book Name</th>
				<th>Book No</th>
				<th>Part I Like 1</th>
				<th>Part I Like 2</th>
				<th>Part I Like 3</th>
				<th>Part II Like 1</th>
				<th>Part II Like 2</th>
				<th>Part II Like 3</th>
				<th>Date</th>
			</tr>
			<%
							List userList = (List) request.getAttribute("dtoList");
							if(userList.size()==0 ||userList == null){
								%>	
						<tr>
					<td colspan="11"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
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
					LikesBean bean = (LikesBean) it.next();
			%>
			<tr>
				<td><%=index++%></td>
				<td><%=bean.getEmail()%></td>
				<td><%=bean.getBookName()%></td>
				<td><%=bean.getBookNo()%></td>
				<td><%=bean.getLike1()%></td>
				<td><%=bean.getLike2()%></td>
				<td><%=bean.getLike3()%></td>
				<td><%=bean.getLike4()%></td>
				<td><%=bean.getLike5()%></td>
				<td><%=bean.getLike6()%></td>
				<td><%=bean.getDate()%></td>
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
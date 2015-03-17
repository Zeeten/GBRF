<%@page import="com.ncs.util.ServletUtility"%>
<%@page import="com.ncs.bean.UserBean"%>
<%@page import="com.ncs.bean.RegisterPrintedBookBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User List</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>
	$(function() {
		$("#includedContent").load("admin.jsp");
	});
</script>
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">

</head>
<body >
	<div id="includedContent"></div>

		<div class="container">
		<center>
			<div class="row" style="margin-top: 90px">
					<h2 >User List</h2>
		
						<FORM  METHOD="post" action="UserListCtl"class="form-horizontal">
						<div class="row">
				<label for="inputname" class="control-label col-xs-offset-1 col-xs-2" >Book Name:</label>
					<div class="col-xs-2">
						<input type="text" class="form-control" name="name" id="name"
							placeholder="Name">
				</div>
			
				<label for="inputemailId" class="control-label col-xs-1">Email ID :</label>
					<div class="col-xs-2">
						<input type="text" class="form-control" name="emailId" id="emailId"
							placeholder="Email ID">
					</div>
		          <button name="operation" value="Search" type="submit" class="btn btn-info"  style="margin-left: -320px">
							<span class="btn-save-label">
						<i class="glyphicon glyphicon-search"></i>
						</span>
						Search
						</button>
							</div>

		<br>
					<div class="table-responsive"  style="width: 70%">
						<table class="table table-bordered table-hover">
							<tr>
								<th>#</th>
								<th>First Name</th>
								<th>Last Name</th>
								<th>Email ID</th>
								<th>Date</th>
							</tr>
							<%
							List userList = (List) request.getAttribute("dtoList");
							if(userList.size()==0 ||userList == null){
								%>	
												<tr>
					<td colspan="5"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
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
								
									UserBean bean = (UserBean) it
											
											.next();
							%>
							<tr>
								<td><%=index++%></td>
								<td><%=bean.getName()%></td>
								<td><%=bean.getSurname()%></td>
								<td><%=bean.getEmail()%></td>
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
			</center>
	</div>

</body>
</html>
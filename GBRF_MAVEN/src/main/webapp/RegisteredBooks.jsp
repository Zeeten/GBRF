<%@page import="com.ncs.util.ServletUtility"%>
<%@page import="com.ncs.bean.RegisterPrintedBookBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registered Books</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>
	$(function() {
		$("#includedContent").load("admin.jsp");
	});
</script>
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
<style type="text/css">
body {
	color: white;
}
</style>
</head>
<body background="img/bg/bgbooks.jpg">
	<div id="includedContent"></div>
		
	<div style="margin-top: 80px">
	
	<div class="container">
			<div class="row">
		
	<center>
		<h1 style="color: #fff">Registered Books</h1>
						<FORM  METHOD="post" action="RegisteredBooksCtl" class="form-horizontal">
						<div class="row">
					<label for="inputBookId" class="control-label  col-xs-1"
						style="color: #fff">Book ID :</label>
					<div class="col-xs-2">
						<input type="text" class="form-control" name="bookId" id="bookId"
							style="background: transparent; color: #fff;"
							placeholder="Book ID">
					</div>
					
				<label for="inputemailId" class="control-label col-xs-1"
						style="color: #fff">Email ID :</label>
					<div class="col-xs-2">
						<input type="text" class="form-control" name="emailId" id="emailId"
							style="background: transparent; color: #fff;"
							placeholder="Email ID">
					</div>
		
				<label for="inputbookName" class="control-label col-xs-2"
						style="color: #fff">Book Name:</label>
					<div class="col-xs-2">
						<input type="text" class="form-control" name="bookName" id="bookName"
							style="background: transparent; color: #fff;"
							placeholder="Book Name">
				</div>
					<div class="col-xs-2" >
						<input name="operation" value="Search" type="submit"
							style="background: transparent; color: #fff; width: 130px; height: 30px">
							</div>
							</div>
				
		<br>
	<div class="table-responsive">
						<table class="table table-bordered" style="color: #fff">
			<tr>
								<th>#</th>
				<th>Book Id</th>
				<th>Book Name</th>
				<th>Email ID</th>
								<th>Read Like Part I</th>
								<th>Read Like Part II</th>
				<th>Date Of Purchase</th>
			</tr>
								<%
							List userList = (List) request.getAttribute("dtoList");
							if(userList.size()==0 ||userList == null){
								%>	
												<tr>
					<td colspan="7"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
				</tr>	
								
							<% }
							%>
							
							<% 
							String rlPartI=null;
							String rlPartII=null;
							int pageNo = ServletUtility.getPageNo(request);
							int pageSize = ServletUtility.getPageSize(request);
							int index = ((pageNo - 1) * pageSize) + 1;
				List list = (List) request.getAttribute("dtoList");
				Iterator it = list.iterator();
				while (it.hasNext()) {
									RegisterPrintedBookBean bean = (RegisterPrintedBookBean) it
											.next();
									if(bean.getRlPartI() ){
										rlPartI="Complete";
									}else{
										rlPartI="Not Complete";
									}
									if(bean.getRlPartII() ){
										rlPartII="Complete";
									}else{
										rlPartII="Not Complete";
									}
			%>
			<tr>
								<td><%=index++%></td>
				<td><%=bean.getBookId()%></td>
				<td><%=bean.getBookName()%></td>
				<td><%=bean.getEmail()%></td>
								<td><%=rlPartI%></td>
								<td><%=rlPartII%></td>
				<td><%=bean.getDate()%></td>
			</tr>
			<%
				}
			%>
		</table>
						</table>
									<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						value="Previous" style="background: transparent; color: #fff;"></td>
					<td align="right"><input type="submit" name="operation"
						value="Next"  style="background: transparent; color: #fff;"></td>
				</tr>
			</table>
				<input type="hidden" name="pageNo" value="<%=pageNo%>"><input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</div>
								
		</FORM>
	</center>
	</div>
		</div>
		</div>

</body>
</html>
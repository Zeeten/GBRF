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
<title>Read Like Award Part One</title>
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
					<h1 style="color: #fff">Read Like Award Part I List</h1>
						<FORM  METHOD="post" action="ReadLikeAwardPartOneCtl"class="form-horizontal">
							<jsp:useBean id="bean" class="com.ncs.bean.BooksBean"
			scope="request"></jsp:useBean>

		<%
			List bookList = (List) request.getAttribute("bookList");
		%>
						<div class="row">
			
				<label for="inputname" class="control-label col-xs-2"
						style="color: #fff">Book Name:</label>
					<div class="col-xs-2">
					<%=HTMLUtility.getList("bookName",
					bean.getBookName(), bookList)%>
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
								<th>First Name</th>
								<th>Last Name</th>
								<th>Email</th>
								<th>Book Name</th>
								<th>Book No</th>
								<th>Amount</th>
								<th>Award Date</th>
								<th>Submit Date</th>
							</tr>
							<%
							List partOneList = (List) request.getAttribute("dtoList");
							if(partOneList.size()==0 ||partOneList == null){
								%>	
												<tr>
					<td colspan="9"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
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
								<td><%=partOnebean.getBookName()%></td>
								<td><%=partOnebean.getBookNo()%></td>
									<td><%=partOnebean.getAmount()%></td>
								<td><%=partOnebean.getAwardDate()%></td>
								<td><%=partOnebean.getSubmitDate()%></td>
							</tr>
							<%
								}
							%>
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
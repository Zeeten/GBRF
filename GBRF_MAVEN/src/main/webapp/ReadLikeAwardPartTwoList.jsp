<%@page import="com.ncs.util.HTMLUtility"%>
<%@page import="com.ncs.bean.ReadLikeAwardPartTwoBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.ncs.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Read Like Award Part Two</title>
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
			<div class="row"  style="margin-top: 90px">

					<h2 >Read Like Award Part II List</h2>
						<FORM  METHOD="post" action="ReadLikeAwardPartTwoCtl"class="form-horizontal">
							<jsp:useBean id="bean" class="com.ncs.bean.BooksBean"
			scope="request"></jsp:useBean>

		<%
			List bookList = (List) request.getAttribute("bookList");
		%>
						<div class="row">
			
				<label for="inputname" class="control-label col-xs-2">Book Name:</label>
					<div class="col-xs-2">
					<%=HTMLUtility.getList("bookName",
					bean.getBookName(), bookList)%>
				</div>

			<button name="operation" value="Search" type="submit" class="btn btn-info" >
							<span class="btn-save-label">
						<i class="glyphicon glyphicon-search"></i>
						</span>
						Search
						</button>
							</div>

		<br>
					<div class="table-responsive">
						<table class="table table-bordered table-hover">
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
								
									ReadLikeAwardPartTwoBean partOnebean = (ReadLikeAwardPartTwoBean) it
											
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
						value="Previous"class="btn btn-info"></td>
					<td align="right"><input type="submit" name="operation"
						value="Next"class="btn btn-info"></td>
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
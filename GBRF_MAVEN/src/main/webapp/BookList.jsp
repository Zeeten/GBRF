<%@page import="com.ncs.bean.BooksBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.ncs.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book List</title>
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

					<h1 class="col-xs-offset-4">Book List</h1>
						<FORM  METHOD="post" action="BookListCtl"class="form-horizontal">
						<div class="row">
			
				<label for="inputname" class="control-label col-xs-2">Book Name:</label>
					<div class="col-xs-2">
						<input type="text" class="form-control" name="bookName" id="bookName"
							placeholder="Book Name">
				</div>
		 <button name="operation" value="Search" type="submit" class="btn btn-info">
							<span class="btn-save-label">
						<i class="glyphicon glyphicon-search"></i>
						</span>
						Search
						</button>
							</div>

		<br>
					<div class="table-responsive">
						<table class="table table-bordered table-hover" style="width: 70%">
							<tr>
								<th>#</th>
								<th>Book Name</th>
								<th>No Of Chgapters</th>
							</tr>
							<%
							List bookList = (List) request.getAttribute("dtoList");
							if(bookList.size()==0 ||bookList == null){
								%>	
												<tr>
					<td colspan="3"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
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
								BooksBean bean = (BooksBean) it
											
											.next();
							%>
							<tr>
								<td><%=index++%></td>
								<td><%=bean.getBookName()%></td>
									<td><%=bean.getNoOfChapters()%></td>
							</tr>
							<%
								}
							%>
						</table>
									<table width="70%">
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
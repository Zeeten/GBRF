<%@page import="java.util.Iterator"%>
<%@page import="com.ncs.bean.BookChaptersBean"%>
<%@page import="com.ncs.util.ServletUtility"%>
<%@page import="com.ncs.util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Chapters List</title>
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
	<center>
		<div class="row" style="margin-top: 90px">

				<h1 >Book Chapters List</h1>
				<FORM METHOD="post" action="BookChaptersCtl" class="form-horizontal">
					<jsp:useBean id="bean" class="com.ncs.bean.BooksBean"
						scope="request"></jsp:useBean>

					<%
						List bookList = (List) request.getAttribute("bookchaptersList");
					%>
					<div class="row">

						<label for="inputname"
							class="control-label col-md-offset-1 col-md-2" >Book Name:</label>
						<div class="col-md-2">
							<%=HTMLUtility.getList("bookName", bean.getBookName(),
					bookList)%>
						</div>
						<button name="operation" value="Search" type="submit" style="margin-left: -580px"
							class="btn btn-info">
							<span class="btn-save-label"> <i
								class="glyphicon glyphicon-search"></i>
							</span> Search
						</button>
					</div>

					<br>
					<div class="table-responsive">
						<table class="table table-bordered table-hover" style="width: 70%">
							<tr>
								<th>#</th>
								<th>Chapter Name</th>

							</tr>
							<%
								List partOneList = (List) request.getAttribute("dtoList");
								if (partOneList.size() == 0 || partOneList == null) {
							%>
							<tr>
								<td colspan="9"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
							</tr>
							<%
								}
							%>

							<%
								int pageNo = ServletUtility.getPageNo(request);
								int pageSize = ServletUtility.getPageSize(request);
								int index = ((pageNo - 1) * pageSize) + 1;
								List list = (List) request.getAttribute("dtoList");
								Iterator it = list.iterator();
								while (it.hasNext()) {

									BookChaptersBean bookchapterbean = (BookChaptersBean) it

									.next();
							%>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter1()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter2()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter3()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter4()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter5()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter6()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter7()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter8()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter9()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter10()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter11()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter12()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter13()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter14()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter15()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter16()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter17()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter18()%></td>

							</tr>
							<tr>
								<td><%=index++%></td>
								<td><%=bookchapterbean.getChapter19()%></td>

							</tr>

							<%
								}
							%>
						</table>
						<table width="70%">
							<tr>
								<td><input type="submit" name="operation" value="Previous"
									class="btn btn-info"></td>
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
<%@page import="com.ncs.util.HTMLUtility"%>
<%@page import="com.ncs.util.ServletUtility"%>
<%@page import="com.ncs.bean.ReleaseAndBuyAwardListBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Award List</title>
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
	<div style="margin-top: 50px">
		<div class="container">
			<div class="row">
				<center>
					<h1 style="color: #fff">Release And Buy Award List</h1>
					<FORM METHOD="post" action="ReleaseAndBuyAwardListCtl"
						class="form-horizontal">
						<jsp:useBean id="bean" class="com.ncs.bean.BooksBean"
							scope="request"></jsp:useBean>

						<%
							List bookList = (List) request.getAttribute("bookList");
						%>
						<div class="row">

							<label for="inputname" class="control-label col-xs-2"
								style="color: #fff">Book Name:</label>
							<div class="col-xs-2">
								<%=HTMLUtility.getList("bookName", bean.getBookName(),
					bookList)%>
							</div>


							<div class="col-xs-2">
								<input name="operation" value="Search" type="submit"
									style="background: transparent; color: #fff; width: 130px; height: 30px">
							</div>
						</div>

						<br>

						<div class="table-responsive">
							<table class="table table-bordered"
								style="color: #fff; width: 70%">
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
										ReleaseAndBuyAwardListBean awardbean = (ReleaseAndBuyAwardListBean) it
												.next();
								%>
								<tr>
									<td><%=index++%></td>
									<td><%=awardbean.getFirstName()%></td>
									<td><%=awardbean.getLastName()%></td>
									<td><%=awardbean.getEmail()%></td>
									<td><%=awardbean.getBookName()%></td>
									<td><%=awardbean.getBookNo()%></td>
									<td><%=awardbean.getAmount()%></td>
									<td><%=awardbean.getAwardDate()%></td>
									<td><%=awardbean.getSubmitDate()%></td>
								</tr>
								<%
									}
								%>
							</table>
						</div>
				</center>
			</div>
		</div>

	</div>
</body>
</html>
<%@page import="com.ncs.bean.UserBean"%>
<%@page import="com.ncs.bean.BooksBean"%>
<%@page import="com.ncs.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
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
	<div style="margin-top: 100px">
		<center>

			<FORM ACTION="WelcomeCtl.do" METHOD="post" class="form-horizontal">

				<div class="form-group">
					<label for="inputBook" class="control-label col-xs-offset-3 col-xs-2"
						style="color: #fff">Book Name :</label>
					<div class="col-xs-3">
						<select class="form-control" id="bookName" name="bookName"
							style="background: transparent;">
							<option value="" style="background: transparent;">--Select--</option>
							<%
								List list = ServletUtility.getList(request);
								Iterator it = list.iterator();
								while (it.hasNext()) {
									BooksBean bean = (BooksBean) it.next();
							%>
							<option value="<%=bean.getBookName()%>"
								style="background: transparent;"><%=bean.getBookName()%></option>
							<%
								}
							%>
						</select> <font color="red"> <%=ServletUtility.getErrorMessage("bookName", request)%></font>
					</div>
				</div>
				<div class="form-group">
					<label for="inputBookNo" class="control-label col-xs-offset-3 col-xs-2"
						style="color: #fff">Book No :</label>
					<div class="col-xs-3">
						<input type="text" class="form-control" name="bookNo" id="bookNo"
							style="background: transparent; color: #fff;"
							placeholder="Book No"> <font color="red"> <%=ServletUtility.getErrorMessage("bookNo", request)%></font>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-offset-1 col-xs-10">
						<input name="operation" value="Go" type="submit"
							style="background: transparent; color: #fff; width: 130px; height: 30px">
					</div>
				</div>
			</form>
		</center>
	</div>
</html>
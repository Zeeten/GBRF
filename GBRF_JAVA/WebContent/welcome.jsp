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
<style type="text/css">
body {
	color: white;
}

td {
	font-size: 20px;
}

h1 {
	font-size: 35px;
}
</style>
</head>

<body background="img/bg/bg.jpg">
	<center>
		<h1>
			Welcome :
			<%=((UserBean) session.getAttribute("user")).getFullName()%></h1>

		<FORM ACTION="WelcomeCtl.do" METHOD="post">
			<table>
				<tr>
					<td>Book Name :</td>
					<td><select name="bookName">
							<option value="">--Select--</option>
							<%
								List list = ServletUtility.getList(request);
								Iterator it = list.iterator();
								while (it.hasNext()) {
									BooksBean bean = (BooksBean) it.next();
							%>
							<option value="<%=bean.getBookName()%>"><%=bean.getBookName()%></option>
							<%
								}
							%>
					</select></td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("bookName", request)%></font></td>
				</tr>
				<tr>
					<td>Book No. :</td>
					<td><input name="bookNo" type="text"></td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("bookNo", request)%></font></td>
				</tr>
				<tr>
					<td></td>
					<td colspan="2" align="left"><input name="operation"
						value="Go" type="submit"></td>
				</tr>
			</table>
		</form>
	</center>
</html>
<%@page import="com.ncs.util.HTMLUtility"%>
<%@page import="com.ncs.bean.UserBean"%>
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
<title>Register Print Book</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="js/bootstrap.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
  <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
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
	<script>
$(function() {
    $('.date-picker').datepicker( {
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true,
        dateFormat: 'dd/mm/yy',
      
    });
});
</script>
</head>
<body  background="img/bg/bgbooks.jpg">
	<div id="includedContent"></div>
	<div style="margin-top: 100px">
		<center>
<h2>

<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font></h2>
			<FORM ACTION="RegisterPrintedBookCtl" METHOD="post" class="form-horizontal">

			<div class="form-group">
					<label for="inputBook" class="control-label col-xs-offset-3 col-xs-2"
						style="color: #fff">Book</label>
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
							
						</select> 
						<font color="red"> <%=ServletUtility.getErrorMessage("bookName", request)%></font>
					</div>
				</div>
				<div class="form-group">
					<label for="inputBookId" class="control-label col-xs-offset-3 col-xs-2"
						style="color: #fff">Book ID :</label>
					<div class="col-xs-3">
						<input type="text" class="form-control" name="bookId" id="bookId"
							style="background: transparent; color: #fff;"
							placeholder="Book ID"> <font color="red"> <%=ServletUtility.getErrorMessage("bookId", request)%></font>
					</div>
				</div>
				
					<div class="form-group">
					<label for="inputDateOfPurchase" class="control-label col-xs-offset-3 col-xs-2"
						style="color: #fff">Date of Purchase :</label>
					<div class="col-xs-3">
						<input type="text"  name="dateofpurchase" id="dateofpurchase"
							style="background: transparent; color: #fff;" class="form-control date-picker"
							placeholder="Date of Purchase"><font color="red"> <%=ServletUtility.getErrorMessage("dateofpurchase", request)%></font>
					</div>
				</div>
				
				<%
		
				if(session.getAttribute("session")==null){
					%>
							
					<div class="form-group">
					<label for="inputMobileNo" class="control-label col-xs-offset-3 col-xs-2"
						style="color: #fff">Mobile No :</label>
					<div class="col-xs-3">
						<input type="text" class="form-control" name="mobileNo" id="mobileNo"
							style="background: transparent; color: #fff;"
							placeholder="Mobile No"> <font color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font>
					</div>
				</div>
						<div class="form-group">
					<label for="inputemail" class="control-label col-xs-offset-3 col-xs-2"
						style="color: #fff">Login ID :</label>
					<div class="col-xs-3">
						<input type="email" class="form-control" name="email" id="email"
							style="background: transparent; color: #fff;"
							placeholder="Login ID"> <font color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font>
					</div>
				</div>
					<div class="form-group">
					<label for="inputpassword" class="control-label col-xs-offset-3 col-xs-2"
						style="color: #fff">Password :</label>
					<div class="col-xs-3">
						<input type="password" class="form-control" name="password" id="password"
							style="background: transparent; color: #fff;"
							placeholder="Password"> <font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font>
					</div>
				</div>
				<%
				}
				%>
				<div class="form-group">
					<div class="col-xs-offset-1 col-xs-10" style="margin-left: 160px">
						<input name="operation" value="Save" type="submit"
							style="background: transparent; color: #fff; width: 130px; height: 30px">
					<%
		
				if(session.getAttribute("session")!=null){
					%>
					<input name="operation" value="My Registered Books" type="submit"
							style="background: transparent; color: #fff; width: 180px; height: 30px">
											<%
				}
				%>
					</div>
				</div>

				
			</FORM>
			</center>
			</div>
			

</body>
</html>
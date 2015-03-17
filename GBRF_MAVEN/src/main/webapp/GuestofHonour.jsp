<%@page import="com.ncs.bean.GuestOfHonourBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.ncs.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Guest Of Honour</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

</head>
<body >
	<div class="container">
		<div class="row" style="background-image: url('img/card3.jpg');height: 1140px; width: 1140px" >
			<center>
				<h1 style="margin-top: 135px">Guest Of Honour</h1>
				<H2>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font> <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H2>
					<div class="table-responsive">
						<table class="table table-bordered" style="width: 47%">
							<tr >
								<th style="text-align: center;">#</th>
								<th style="text-align: center;">Name</th>
								<th style="text-align: center;">Date</th>
							</tr>
			</table>
						
					</div>
							
							<%
								int index = 0;
								List guestlist = (List) request.getAttribute("dtoList");
								Iterator it = guestlist.iterator();
								while (it.hasNext()) {
									index++;
									GuestOfHonourBean bean = (GuestOfHonourBean) it.next();
							%>
				<div class="row" >
						<marquee  style="height: 50px" direction="up" behavior=scroll scrollamount="2">
						<label for="input"
							class="control-label col-xs-offset-4  col-xs-1"
							style=";margin-left: 350px" ><%=index%></label>
								<label for="input"
							class="control-label  col-xs-2"
							><%=bean.getName()%></label>
								<label for="input"
							class="control-label  col-xs-3"
							style=";margin-left: 30px">	<%=bean.getDate()%></label>
							
								</marquee>
							
						</div>
							
							<%
								}
							%>

				
				<FORM ACTION="GuestOfHonourCtl" METHOD="post"
					class="form-horizontal">
				

					<div class="form-group" style="margin-top: 400px">
						<label for="inputName"
							class="control-label col-xs-offset-3 col-xs-2">Enter Name :</label>
						<div class=" col-xs-3">
							<input type="name" class="form-control" id="name" name="name"
								placeholder="Enter Name"> <font
								color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-offset-1 col-xs-10">
							<input name="operation" value="Save" type="submit">
						</div>
					</div>
				</form>
			</center>
		</div>
	</div>
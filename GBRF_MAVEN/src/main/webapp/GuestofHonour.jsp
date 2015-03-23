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
			<center>
		<div class="row" style="background-image: url('img/cardimg.jpg');height: 670px;width: 560px"  >
		
				<h1 style="margin-top: 117px">Guests Of Honour</h1>
				<H2>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font> <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H2>
					<div class="table-responsive">
						<table class="table table-bordered" style="width: 64%; margin-left: -6PX">
							<tr >
								<th style="text-align: center;width: 20px">ID</th>
								<th style="text-align: center;">Name</th>
								<th style="text-align: center;width: 160px">Date & Time</th>
							</tr>
			</table>
						
					</div>
							
							<marquee  style="height: 285px" direction="up" behavior=scroll scrollamount="3" >
							<%
							for(int i=0; i<=50;i++)
							{
								int index = 0;
								List guestlist = (List) request.getAttribute("dtoList");
								Iterator it = guestlist.iterator();
								while (it.hasNext()) {
									index++;
									GuestOfHonourBean bean = (GuestOfHonourBean) it.next();
							%>
				
						<div class="row" style="height: 30px" >
				<font size="1px">
						<label for="input"
							class="control-label col-xs-offset-1  col-xs-1"
							style=";margin-left: 100px" ><%=index%></label>
								<label for="input"
							class="control-label  col-xs-4"
							style="margin-left: -5px"><%=bean.getName()%></label>
								<label for="input"
							class="control-label  col-xs-3"
							style="margin-left: -30px">	<%=bean.getDate()%></label>
							</font>
								</div>
							
							
							
							
							<%
								}
							}
							%>
							
					
								</marquee>
				
		</div>
			</center>
			<br>
				<FORM ACTION="GuestOfHonourCtl" METHOD="post"
					class="form-horizontal">
				
                     <div class="row">
						<label for="inputName"
							class="control-label col-xs-offset-3 col-xs-2">Enter Name :</label>
						<div class=" col-xs-3">
							<input type="text" class="form-control" id="name" name="name"
								placeholder="Enter Name"> <font
								color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font>
						</div>
							<input name="operation" value="Save" type="submit" class="btn btn-success">
					</div>
						<br>
		
					<div class="form-group">

      <div class="col-xs-offset-5 col-xs-10" style="margin-left: 518px">
       <a class="btn btn-info"
        href="http://kissmatinternational.com/kip/?route=common/home" target="_blank">RELEASE AND BUY BOOK</a>
						</div>
					</div>
				</form>
	</div>
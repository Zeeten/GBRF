<%@page import="com.ncs.util.HTMLUtility"%>
<%@page import="com.ncs.bean.RegisterPrintedBookBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.ncs.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Read and Like</title>
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
<script language="javascript" type="text/javascript">
<!--

$(document).ready(function() {
	//When a user clicks a radio button that is within an element with a class of .matrix...
	$(".matrix :radio").mouseup( function(){
		//Remove other entries in the row. Row is defined as all other elements that have the same value as the current object.
		//Loop through all inputs with the same value as the current object
		$(".matrix input[value='"+$(this).val()+"']").each( function(){
			//For each of the matching objects, compare
			if($(this).attr("name") != $(this).val()){
				this.checked = false;
			}
		})
	});
});

// -->
</script>
</head>
<body background="img/bg/bgbooks.jpg">
	<div id="includedContent"></div>
	<div style="margin-top: 50px">

		<form name="matrixForm" ACTION="UsersLikesPartTwoCtl"  METHOD="post"  class="matrix" class="form-horizontal">
					<jsp:useBean id="bean" class="com.ncs.bean.LikesBean" scope="request"/>
			
       <%
			List likelist = (List) request.getAttribute("list");
		%>
			<div class="container">
<div class="row">
	<center>
		<H1 style="color: #fff">Read and Like-Part II</H1>
		<H2 style="color: #fff">Select best three chapters from 10 to 19 for part II Award</H2>

		<font color="red"><%=ServletUtility.getErrorMessage("like1", request)%></font>
		&emsp; <font color="red"><%=ServletUtility.getErrorMessage("like2", request)%></font>
		&emsp; <font color="red"><%=ServletUtility.getErrorMessage("like3", request)%></font>
				<div class="table-responsive">
				<div class="form-group">
					<label for="inputBook" class="control-label col-xs-offset-3 col-xs-2"
						style="color: #fff">Book</label>
					<div class="col-xs-3">
					<%=HTMLUtility.getList("bookNo", bean.getBookName(), likelist)%> 
						<font color="red"> <%=ServletUtility.getErrorMessage("bookNo", request)%></font>
					</div>
				</div>
							<br>
		<table class="table table-bordered" style=" width: 70%">
				<tr style="color: #fff;">
					<th >Chapter</th>
					<th >Like1</th>
					<th>Like2</th>
					<th >Like3</th>
				</tr>
				<tr style="color: #fff">
					<td>Chapter 1: A Skit with my Friend Jaspal Bhatti</td>
					<td align="center"><input type="radio"  name="like1"
						value="1" disabled></td>
					<td align="center"><input type="radio"  name="like2"
						value="1" disabled></td>
					<td align="center"><input type="radio"  name="like3"
						value="1" disabled></td>
				</tr>
				<tr style="color: #fff">
					<td>Chapter 2: FM2 factor and Defending the Youth</td>
					<td align="center"><input type="radio"  name="like1"
						value="2" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="2" disabled></td>
					<td align="center"><input type="radio"  name="like3"
						value="2" disabled></td>
				</tr>
				<tr style="color: #fff">
					<td>Chapter 3: The Indian Bucket System</td>
					<td align="center"><input type="radio" name="like1"
						value="3" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="3" disabled></td>
					<td align="center"><input type="radio" name="like3"
						value="3" disabled></td>
				</tr>
				<tr style="color: #fff">
					<td>Chapter 4: India is a Land of Many Verticals</td>
					<td align="center"><input type="radio" name="like1"
						value="4" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="4" disabled></td>
					<td align="center"><input type="radio" name="like3"
						value="4" disabled></td>
				</tr>
				<tr style="color: #fff">
					<td>Chapter 5: Creative Politics Creative India</td>
					<td align="center"><input type="radio" name="like1"
						value="5" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="5" disabled></td>
					<td align="center"><input type="radio" name="like3"
						value="5" disabled></td>
				</tr>
				<tr style="color: #fff">
					<td>Chapter 6: Social-Anti-Social Balance</td>
					<td align="center"><input type="radio" name="like1"
						value="6" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="6" disabled></td>
					<td align="center"><input type="radio" name="like3"
						value="6" disabled></td>
				</tr>
				<tr style="color: #fff">
					<td>Chapter 7: Virtue of Skill Development</td>
					<td align="center"><input type="radio" name="like1"
						value="7" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="7" disabled></td>
					<td align="center"><input type="radio" name="like3"
						value="7" disabled></td>
				</tr>
				<tr style="color: #fff">
					<td>Chapter 8: Vox Populi</td>
					<td align="center"><input type="radio" name="like1"
						value="8" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="8" disabled></td>
					<td align="center"><input type="radio" name="like3"
						value="8" disabled></td>
				</tr>
				<tr style="color: #fff">
					<td>Chapter 9: The 'KAUL Plan' for Raising 'CSDP & FO'</td>
					<td align="center"><input type="radio" name="like1"
						value="9" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="9" disabled></td>
					<td align="center"><input type="radio" name="like3"
						value="9" disabled></td>
				</tr >
					<tr style="color: #fff">
					<td>Chapter 10: Tech Blazon</td>
					<td align="center"><input type="radio" name="like1"
						value="10" ></td>
					<td align="center"><input type="radio" name="like2"
						value="10" ></td>
					<td align="center"><input type="radio" name="like3"
						value="10" ></td>
				</tr>
					<tr style="color: #fff">
					<td>Chapter 11: Technology Melancholia</td>
					<td align="center"><input type="radio" name="like1"
						value="11" ></td>
					<td align="center"><input type="radio" name="like2"
						value="11" ></td>
					<td align="center"><input type="radio" name="like3"
						value="11" ></td>
				</tr>
				<tr style="color: #fff">
					<td>Chapter 12: Indian Interface</td>
					<td align="center"><input type="radio" name="like1"
						value="12" ></td>
					<td align="center"><input type="radio" name="like2"
						value="12" ></td>
					<td align="center"><input type="radio" name="like3"
						value="12" ></td>
				</tr>
					<tr style="color: #fff">
					<td>Chapter 13: International Interface</td>
					<td align="center"><input type="radio" name="like1"
						value="13" ></td>
					<td align="center"><input type="radio" name="like2"
						value="13" ></td>
					<td align="center"><input type="radio" name="like3"
						value="13" ></td>
				</tr>
					<tr style="color: #fff">
					<td>Chapter 14: Welfare Security</td>
					<td align="center"><input type="radio" name="like1"
						value="14" ></td>
					<td align="center"><input type="radio" name="like2"
						value="14" ></td>
					<td align="center"><input type="radio" name="like3"
						value="14" ></td>
				</tr>
					<tr style="color: #fff">
					<td>Chapter 15: Creative Youth Creating Wealth</td>
					<td align="center"><input type="radio" name="like1"
						value="15" ></td>
					<td align="center"><input type="radio" name="like2"
						value="15" ></td>
					<td align="center"><input type="radio" name="like3"
						value="15" ></td>
				</tr>
					<tr style="color: #fff">
					<td>Chapter 16: International of Indian English</td>
					<td align="center"><input type="radio" name="like1"
						value="16" ></td>
					<td align="center"><input type="radio" name="like2"
						value="16" ></td>
					<td align="center"><input type="radio" name="like3"
						value="16" ></td>
				</tr>
					<tr style="color: #fff">
					<td>Chapter 17: 20 October,2031</td>
					<td align="center"><input type="radio" name="like1"
						value="17" ></td>
					<td align="center"><input type="radio" name="like2"
						value="17" ></td>
					<td align="center"><input type="radio" name="like3"
						value="17" ></td>
				</tr>
				<tr style="color: #fff">
					<td>Chapter 18: Vox Dei & the Maha Yagya</td>
					<td align="center"><input type="radio" name="like1"
						value="18" ></td>
					<td align="center"><input type="radio" name="like2"
						value="18" ></td>
					<td align="center"><input type="radio" name="like3"
						value="18" ></td>
				</tr>
				<tr style="color: #fff">
					<td>Chapter 19: Skilling & Marketing Gandhi Indian!</td>
					<td align="center"><input type="radio" name="like1"
						value="19" ></td>
					<td align="center"><input type="radio" name="like2"
						value="19" ></td>
					<td align="center"><input type="radio" name="like3"
						value="19" ></td>
				</tr>
			</table>
		
				</div>
				<div class="form-group">
					<div class="col-xs-offset-1 col-xs-10">
						<input name="operation" value="Save" type="submit"
							style="background: transparent; color: #fff; width: 130px; height: 30px">
					</div>
				</div>
	</div>
		</div>
		</form>
		</div>

</html>
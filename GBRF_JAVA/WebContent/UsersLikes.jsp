<%@page import="com.ncs.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Like Page for Creative Book</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<style type="text/css">
body {
	color: white;
}

td {
	font-size: 15px;
}

h1 {
	font-size: 35px;
}

H2 {
	font-size: 20px;
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
<body background="img/bg/bg.jpg">

		<form name="matrixForm" ACTION="UsersLikesCtl"  METHOD="post"  class="matrix">
			<div class="container">
<div class="row">
	<center>
		<H1>Read and WIN-Part I</H1>
		<H2>Select best three chapters from 1 to 9 for part I Award</H2>
		<font color="red"><%=ServletUtility.getErrorMessage("like1", request)%></font>
		&emsp; <font color="red"><%=ServletUtility.getErrorMessage("like2", request)%></font>
		&emsp; <font color="red"><%=ServletUtility.getErrorMessage("like3", request)%></font>

				<div class="table-responsive">
		<table class="table table-bordered">
				<tr>
					<th bgcolor="silver">Chapter</th>
					<th bgcolor="silver">Like1</th>
					<th bgcolor="silver">Like2</th>
					<th bgcolor="silver">Like3</th>
				</tr>
				<tr>
					<td>Chapter 1: A Skit with my Friend Jaspal Bhatti</td>
					<td align="center"><input type="radio" name="like1"
						value="1"></td>
					<td align="center"><input type="radio" name="like2"
						value="1"></td>
					<td align="center"><input type="radio" name="like3"
						value="1"></td>
				</tr>
				<tr>
					<td>Chapter 2: FM2 factor and Defending the Youth</td>
					<td align="center"><input type="radio" name="like1"
						value="2"></td>
					<td align="center"><input type="radio" name="like2"
						value="2"></td>
					<td align="center"><input type="radio" name="like3"
						value="2"></td>
				</tr>
				<tr>
					<td>Chapter 3: The Indian Bucket System</td>
					<td align="center"><input type="radio" name="like1"
						value="3"></td>
					<td align="center"><input type="radio" name="like2"
						value="3"></td>
					<td align="center"><input type="radio" name="like3"
						value="3"></td>
				</tr>
				<tr>
					<td>Chapter 4: India is a Land of Many Verticals</td>
					<td align="center"><input type="radio" name="like1"
						value="4"></td>
					<td align="center"><input type="radio" name="like2"
						value="4"></td>
					<td align="center"><input type="radio" name="like3"
						value="4"></td>
				</tr>
				<tr>
					<td>Chapter 5: Creative Politics Creative India</td>
					<td align="center"><input type="radio" name="like1"
						value="5"></td>
					<td align="center"><input type="radio" name="like2"
						value="5"></td>
					<td align="center"><input type="radio" name="like3"
						value="5"></td>
				</tr>
				<tr>
					<td>Chapter 6: Social-Anti-Social Balance</td>
					<td align="center"><input type="radio" name="like1"
						value="6"></td>
					<td align="center"><input type="radio" name="like2"
						value="6"></td>
					<td align="center"><input type="radio" name="like3"
						value="6"></td>
				</tr>
				<tr>
					<td>Chapter 7: Virtue of Skill Development</td>
					<td align="center"><input type="radio" name="like1"
						value="7"></td>
					<td align="center"><input type="radio" name="like2"
						value="7"></td>
					<td align="center"><input type="radio" name="like3"
						value="7"></td>
				</tr>
				<tr>
					<td>Chapter 8: Vox Populi</td>
					<td align="center"><input type="radio" name="like1"
						value="8"></td>
					<td align="center"><input type="radio" name="like2"
						value="8"></td>
					<td align="center"><input type="radio" name="like3"
						value="8"></td>
				</tr>
				<tr>
					<td>Chapter 9: The 'KAUL Plan' for Raising 'CSDP & FO'</td>
					<td align="center"><input type="radio" name="like1"
						value="9"></td>
					<td align="center"><input type="radio" name="like2"
						value="9"></td>
					<td align="center"><input type="radio" name="like3"
						value="9"></td>
				</tr>
					<tr>
					<td>Chapter 10: Tech Blazon</td>
					<td align="center"><input type="radio" name="like1"
						value="10" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="10" disabled></td>
					<td align="center"><input type="radio" name="like3"
						value="10" disabled></td>
				</tr>
					<tr>
					<td>Chapter 11: Technology Melancholia</td>
					<td align="center"><input type="radio" name="like1"
						value="11" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="11" disabled></td>
					<td align="center"><input type="radio" name="like3"
						value="11" disabled></td>
				</tr>
				<tr>
					<td>Chapter 12: Indian Interface</td>
					<td align="center"><input type="radio" name="like1"
						value="12" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="12" disabled></td>
					<td align="center"><input type="radio" name="like3"
						value="12" disabled></td>
				</tr>
					<tr>
					<td>Chapter 13: International Interface</td>
					<td align="center"><input type="radio" name="like1"
						value="13" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="13" disabled></td>
					<td align="center"><input type="radio" name="like3"
						value="13" disabled></td>
				</tr>
					<tr>
					<td>Chapter 14: Welfare Security</td>
					<td align="center"><input type="radio" name="like1"
						value="14" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="14" disabled></td>
					<td align="center"><input type="radio" name="like3"
						value="14" disabled></td>
				</tr>
					<tr>
					<td>Chapter 15: Creative Youth Creating Wealth</td>
					<td align="center"><input type="radio" name="like1"
						value="15" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="15" disabled></td>
					<td align="center"><input type="radio" name="like3"
						value="15" disabled></td>
				</tr>
					<tr>
					<td>Chapter 16: International of Indian English</td>
					<td align="center"><input type="radio" name="like1"
						value="16" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="16" disabled></td>
					<td align="center"><input type="radio" name="like3"
						value="16" disabled></td>
				</tr>
					<tr>
					<td>Chapter 17: 20 October,2031</td>
					<td align="center"><input type="radio" name="like1"
						value="17" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="17" disabled></td>
					<td align="center"><input type="radio" name="like3"
						value="17" disabled></td>
				</tr>
				<tr>
					<td>Chapter 18: Vox Dei & the Maha Yagya</td>
					<td align="center"><input type="radio" name="like1"
						value="18" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="18" disabled></td>
					<td align="center"><input type="radio" name="like3"
						value="18" disabled></td>
				</tr>
				<tr>
					<td>Chapter 19: Skilling & Marketing Gandhi Indian!</td>
					<td align="center"><input type="radio" name="like1"
						value="19" disabled></td>
					<td align="center"><input type="radio" name="like2"
						value="19" disabled></td>
					<td align="center"><input type="radio" name="like3"
						value="19" disabled></td>
				</tr>
			</table>
		
				</div>
					<font color="black"><INPUT TYPE="submit" VALUE="Save" name="operation"></font>&nbsp;&nbsp;<a
				href="UsersLikesListCtl">List</a>
	</center>
		</div>
		</div>
		</form>

</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Like Page for Creative Book</title>
</head>
<body>
	<center>
		<H1>Read and WIN</H1>
		<H1></H1>
		<FORM ACTION="LikesCtl" METHOD="post">

			<table width="600" align="center" border="1">
				<tr>
					<th bgcolor="silver">Chapter</th>
					<th bgcolor="silver">Like1</th>
					<th bgcolor="silver">Like2</th>
					<th bgcolor="silver">Like3</th>
				</tr>
				<tr>
					<td>Chapter 1: A Skit with my Friend Jaspal Bhatti</td>
					<td><input type="radio" name="like1" value="Chapter 1"></td>
					<td><input type="radio" name="like2" value="Chapter 1"></td>
					<td><input type="radio" name="like3" value="Chapter 1"></td>
				</tr>
				<tr>
					<td>Chapter 2: FM2 factor and Defending the Youth</td>
					<td><input type="radio" name="like1" value="Chapter 2"></td>
					<td><input type="radio" name="like2" value="Chapter 2"></td>
					<td><input type="radio" name="like3" value="Chapter 2"></td>
				</tr>
				<tr>
					<td>Chapter 3: The Indian Bucket System</td>
					<td><input type="radio" name="like1" value="Chapter 3"></td>
					<td><input type="radio" name="like2" value="Chapter 3"></td>
					<td><input type="radio" name="like3" value="Chapter 3"></td>
				</tr>
				<tr>
					<td>Chapter 4: India is a Land of Many Verticals</td>
					<td><input type="radio" name="like1" value="Chapter 4"></td>
					<td><input type="radio" name="like2" value="Chapter 4"></td>
					<td><input type="radio" name="like3" value="Chapter 4"></td>
				</tr>
				<tr>
					<td>Chapter 5: Creative Politics Creative India</td>
					<td><input type="radio" name="like1" value="Chapter 5"></td>
					<td><input type="radio" name="like2" value="Chapter 5"></td>
					<td><input type="radio" name="like3" value="Chapter 5"></td>
				</tr>
				<tr>
					<td>Chapter 6: Social-Anti-Social Balance</td>
					<td><input type="radio" name="like1" value="Chapter 6"></td>
					<td><input type="radio" name="like2" value="Chapter 6"></td>
					<td><input type="radio" name="like3" value="Chapter 6"></td>
				</tr>
				<tr>
					<td>Chapter 7: Virtue of Skill Development</td>
					<td><input type="radio" name="like1" value="Chapter 7"></td>
					<td><input type="radio" name="like2" value="Chapter 7"></td>
					<td><input type="radio" name="like3" value="Chapter 7"></td>
				</tr>
				<tr>
					<td>Chapter 8: Vox Populi</td>
					<td><input type="radio" name="like1" value="Chapter 8"></td>
					<td><input type="radio" name="like2" value="Chapter 8"></td>
					<td><input type="radio" name="like3" value="Chapter 8"></td>
				</tr>
				<tr>
					<td>Chapter 9: The 'KAUL Plan' for Raising 'CSDP & FO'</td>
					<td><input type="radio" name="like1" value="Chapter 9"></td>
					<td><input type="radio" name="like2" value="Chapter 9"></td>
					<td><input type="radio" name="like3" value="Chapter 9"></td>
				</tr>
				<tr>
					<td align="center" colspan="4"><INPUT TYPE="submit"
						VALUE="Save" name="operation">&nbsp;&nbsp;<a
						href="LikesListCtl">List</a></td>
				</tr>
			</table>
		</form>
	</center>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.Account"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create post</title>
</head>
<style>
	body{
		background-color:white;
	}
	#rcorners2 {
	    border-radius: 25px;
	    border: 2px solid #8AC007;
	    padding: 20px; 
	    width: 350px;
	    height: 450px; 
	 }
	textarea {
		width: 330px;
	}
</style>
<body>

<div id=rcorners2>
	<% if(session.getAttribute("account") == null) 
	{
		response.sendRedirect("https://securedev.mybluemix.net/index.jsp");
	}
	else
	{
		Account curr = (Account) session.getAttribute("account");
	%>
	
	<h2> <%= curr.getfName() %>, speak up!</h2>
	
	<a href="https://securedev.mybluemix.net/user">Home</a>
	<a href="https://securedev.mybluemix.net/globalPosts">View Forums</a>
	
	<% if(session.getAttribute("errors") != null) {%>
		<p id=error> <%= (String)session.getAttribute("errors") %> </p>
		<% session.setAttribute("errors", null); %>
	<% } %>
	
	<% if(session.getAttribute("success") != null) {%>
		<p id=success> <%= (String)session.getAttribute("success") %> </p>
		<% session.setAttribute("success", null); %>
	<% } %>
	
	<br><br><br><br>
	<form action="https://securedev.mybluemix.net/globalPosts" method="post" enctype="multipart/form-data">
		<table>
			<tr><td><textarea name="message" rows="10"></textarea></td></tr>
			<tr><td>Attach Photo: <input type="file" name="attachment"/></td></tr>
			<tr><td><input type="submit" value="Post!"/></td></tr>
		</table>
	</form>
	<p> Legend: <br>
		USE HTML/CSS TAGS FOR STYLE. <br>
		Item Link: @itemname@ <br>
	</p>
	
	
	<% } %>
</div>
</body>
</html>
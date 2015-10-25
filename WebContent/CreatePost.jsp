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
		background-color: rgb(149, 165, 166);
	}
</style>
<body>
	<% if(session.getAttribute("account") == null) 
	{
		response.sendRedirect("index.jsp");
	}
	else
	{
		Account curr = (Account) session.getAttribute("account");
	%>
	
	<h2> <%= curr.getUsername() %> Post </h2>
	
	<a href="HomePage.jsp">Home</a> <br/> <br>
	<a href="PostController">View Posts</a>
	
	<% if(session.getAttribute("errors") != null) {%>
		<p id=error> <%= (String)session.getAttribute("errors") %> </p>
		<% session.setAttribute("errors", null); %>
	<% } %>
	
	<% if(session.getAttribute("success") != null) {%>
		<p id=success> <%= (String)session.getAttribute("success") %> </p>
		<% session.setAttribute("success", null); %>
	<% } %>
	<p> Legend: <br>
		Bold: Enclose string in [b] and [/b] <br>
		Italic: Enclose string in [i] and [/i] <br>
	</p>
	<form action="PostController" method="post" enctype="multipart/form-data">
	
		<table>
			<tr><td>POST: <textarea name="message" rows="8"></textarea></td></tr>
			<tr><td>Attachment: <input type="file" name="attachment"/></td></tr>
			<tr><td><input type="submit" value="Post!"/></td></tr>
		</table>
	</form>
	
	<% } %>
</body>
</html>
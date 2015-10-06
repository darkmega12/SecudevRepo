<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.Account"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Backup</title>
</head>
<body>	
	<% if(session.getAttribute("account") == null) 
	{
		response.sendRedirect("index.jsp");
	}
	else
	{
		Account curr = (Account) session.getAttribute("account");
	%>
	
	<h2> <%= curr.getUsername() %> Back up </h2>
	
	<p>Back up current database by clicking on the button below.</p>
	
	<form action="ExportController" method="post">
		<input type="submit" value="backup now">
	</form>	
	
	<br>
	<p>Download CSV files <a href="">here</a>.</p>
	
	<% } %>
</body>
</html>
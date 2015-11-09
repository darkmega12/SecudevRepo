<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.Account"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="styles.css" media="screen" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Backup</title>
</head>
<body>	
	<% if(session.getAttribute("account") == null) 
	{
		response.sendRedirect("index.jsp");
	}
	else
	{
		Account curr = (Account) session.getAttribute("account");
		if(curr.isAdmin())
		{
	%>
	<div id=rcorners2>
		<h4> Welcome, <%= curr.getSalutation() %> <%= curr.getfName()%> <%= curr.getlName() %> </h4>
		<hr><br>
		<a href="HomePage.jsp">Home</a>
		<br><br><br>
			<form action="ExportController" method="post">
				<input type="submit" value="DATA BACK-UP">
			</form>	
			
			<br>
			<p>Download CSV files <a href="#">here</a>.</p>
			
			<% 
				}
				else
				{
					response.sendRedirect("HomePage.jsp");
				}
			} 
			%>
		<br><br><br><br><br><br><br><br><br>
		<form action = "LogoutController" method = "post">
		<input type = "submit" value = "Logout" />
		</form><br/>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Account"%>
<%@ page import="java.sql.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Management Console</title>

	<!-- Style -->
	<link rel="stylesheet" href="css/pages_style.css">
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
			<div id="rcorners2">
				<h4> Welcome, <%= curr.getSalutation() %>. <%= curr.getfName()%> <%= curr.getlName() %> </h4>
				
					<hr>
					<br>
					<br>
				
				<h2>Management Console</h2>	
				<p style="font-size:10px;">Directory: <a href="HomePage.jsp">[Home]</a></p>
				
					<br>
					<br>
				
				<table width="100%">
					<tr>
						<th>Username</th>
						<th>Firstname</th>
						<th>Lastname</th>
						<th>Total Amount</th>
						<th>Item</th>
						<th>Item Quantity</th>
						<th>Status (Paid, Not-Paid, Cancelled)</th>
					</tr>
	<%

			try {
				String url = "jdbc:mysql://localhost:3306/secudevs18";
				Class.forName("com.mysql.jdbc.Driver");
				Connection dbConnection = DriverManager.getConnection(url, "root", "p@ssword");

	            Statement statement = dbConnection.createStatement() ;
	            ResultSet resultset = statement.executeQuery("SELECT * FROM transactions") ; 
	%>
					<% while(resultset.next()){ %>
					<tr>
						<td><%= resultset.getString(3) %></td>
						<%
							try {
								String url1 = "jdbc:mysql://localhost:3306/secudevs18";
								Class.forName("com.mysql.jdbc.Driver");
								Connection dbConnection1 = DriverManager.getConnection(url, "root", "p@ssword");

					            Statement statement1 = dbConnection1.createStatement() ;
					            ResultSet resultset1 = statement1.executeQuery("SELECT * FROM accounts WHERE username = "+resultset.getString(3)+"") ; 

					            while(resultset1.next()){
						%>
						<td><%= resultset1.getString(3) %></td>
						<td><%= resultset1.getString(4) %></td> 
						<%
								}
							} catch(Exception e) {
								System.out.println(e);
							}
						%>
						<td><%= resultset.getString(4) %></td>
						<%
							try {
								String url2 = "jdbc:mysql://localhost:3306/secudevs18";
								Class.forName("com.mysql.jdbc.Driver");
								Connection dbConnection2 = DriverManager.getConnection(url, "root", "p@ssword");

					            Statement statement2 = dbConnection2.createStatement() ;
					            ResultSet resultset2 = statement2.executeQuery("SELECT * FROM item WHERE itemid = "+resultset.getString(5)+"") ; 

					            while(resultset2.next()){
						%>

						<td><%= resultset2.getString(2) %></td> 	
						<%
								}
							} catch(Exception e) {
								System.out.println(e);
							}
						%>
						<td><%= resultset.getString(6) %></td>
						<td><%= resultset.getString(7) %></td>
					</tr>
					<%}%>
				</table>
				</div>
	<%
			} catch(Exception e) {
				System.out.println(e);
			}

		} else {
			response.sendRedirect("HomePage.jsp");
		}
	}
	%>
</body>
</html>
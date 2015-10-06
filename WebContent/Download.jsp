<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.Account"%>
<%@ page import="java.sql.*" %>

<%// Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Download</title>
</head>
<body>
	<a href="Backup.jsp">Return to Backup</a>
	<% 
	if(session.getAttribute("account") == null) {
		response.sendRedirect("index.jsp");
	} else {
		Account curr = (Account) session.getAttribute("account");
		
		try {
			String url = "jdbc:mysql://localhost:3306/secudevs18";
			Class.forName("com.mysql.jdbc.Driver");
			Connection dbConnection = DriverManager.getConnection(url, "root", "p@ssword");

			Statement statement = dbConnection.createStatement() ;
			ResultSet resultset = statement.executeQuery("SELECT * FROM backup") ; 
	%>
		<form action="DownloadController" id="downloadfile" method="post">
            <select name="downloadfile" id="downloadfile">
            	<% while (resultset.next()) { %>
            	<option value="<%= resultset.getString(1) %>"><%= resultset.getString(3) %></option>
            	<% } %>
            </select>
            <% 
            	} catch(Exception e) {
					System.out.println(e);
			   	} 
			}
			%>
			<input type="submit" value="Download File">
		</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@ page import="java.sql.*" %>

<% Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Download</title>
</head>
<body>

		<% 
			try{
				String url = "jdbc:mysql://localhost:3306/secudevs18";
				Class.forName("com.mysql.jdbc.Driver");
				Connection dbConnection = DriverManager.getConnection(url, "root", "p@ssword");

	            Statement statement = dbConnection.createStatement() ;
	            ResultSet resultset = statement.executeQuery("SELECT * FROM backup") ; 
			
        %>
		
		<TABLE BORDER="1">
            <TR>
                <TH>ID</TH>
                <TH>DATE</TH>
                <TH>FILE</TH>
            </TR>
            <% while(resultset.next()){ %>
            <TR>
                <TD> <%= resultset.getString(1) %></td>
                <TD> <%= resultset.getString(2) %></TD>
                <TD> <%= resultset.getString(3) %></TD>
            </TR>
            <% }
            
				} catch(Exception e)
				{
					System.out.println(e);
				} 
				%>
        </TABLE>
</body>
</html>
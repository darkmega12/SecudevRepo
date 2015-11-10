<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.Account"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="styles.css" media="screen" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>
<style>
	body{
		background-color: white;
	}
</style>
<body>

<% 
	if(session.getAttribute("account") == null)
	{
		response.sendRedirect("index.jsp");
	}
	else
	{
		Account curr = (Account) session.getAttribute("account"); 
		session.setAttribute("isAdmin", curr.isAdmin());
	
%>

<div id=rcorners2>
	<h4> Welcome, <%= curr.getSalutation() %> <%= curr.getfName()%> <%= curr.getlName() %> </h4>
	<hr><br>
	<p> Username: <%= curr.getUsername() %></p>
	<p> Birthdate: <%
			java.text.DateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
			out.print(formatter.format(curr.getBirthday()));
			%></p]>
	<p> Gender: <%=curr.getGender() %></p>
	<p> Date Joined: <%= (curr.getDateJoined().getMonth()+1) +"/" +curr.getDateJoined().getDate() + "/" + 
		(curr.getDateJoined().getYear()+1900) %> </p>
	<p> About Me: <%= curr.getAboutme() %> </p>
	
	<a href='EditProfile.jsp'>Edit Profile </a><br/>
	<a href='CreatePost.jsp'>Post Message</a><br/></br>
	<a href='ItemController'>The Store</a><br>
	<a href='PostController'>View Forums</a><br>
	<% if(curr.isAdmin()) { %>
	<a href='Backup.jsp'>Create Backup</a></br>
	<% } %>
	<% if(curr.isAdmin()) 
		{
			session.setAttribute("isAdmin", true);
			out.println("<a class='btn btn-lg btn-primary ' href='Register.jsp' class='button'>Register</a>");
			out.println("a new user!");
		}
	  }
	%>
	</br></br>
	<form action = "LogoutController" method = "post">
	<input type = "submit" value = "Logout" />
	</form><br/>
</div>
</body>
</html>
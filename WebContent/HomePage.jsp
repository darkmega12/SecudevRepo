<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.Account"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<style>
	body{
		background-color: rgb(149, 165, 166);
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
<h2> Welcome, <%= curr.getSalutation() %> <%= curr.getfName()%> <%= curr.getlName() %> </h2><br/>
<form action = "LogoutController" method = "post">
<input type = "submit" value = "Logout" />
</form><br/>
<a href='EditProfile.jsp'>Edit Profile! </a><br/>
<a href='CreatePost.jsp'>Create Post!</a><br/>
<a href='ItemController'>The Store</a><br>
<a href='PostController'>View Posts</a><br>
<% if(curr.isAdmin()) { %>
<a href='Backup.jsp'>Create Backup</a>
<% } %>
<br>
<h4> Username: <%= curr.getUsername() %></h4><br/>
<h4> Birthdate: <%
		java.text.DateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
		out.print(formatter.format(curr.getBirthday()));
		%></h4><br/>
<h4> Gender: <%=curr.getGender() %></h4>
<h4> Date Joined: <%= curr.getDateJoined() %> </h4>
<h4> About Me: <%= curr.getAboutme() %> </h4><br/>




<% if(curr.isAdmin()) 
	{
		out.println("You're an admin!");
		session.setAttribute("isAdmin", true);
		out.println("<a class='btn btn-lg btn-primary ' href='Register.jsp' class='button'>Register</a>");
	}
  }
%>
</body>
</html>
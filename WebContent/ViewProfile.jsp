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
		Account viewaccount = (Account) session.getAttribute("account"); 
		session.setAttribute("isAdmin", viewaccount.isAdmin());
	
%>

	<div id="rcorners2">
	<h4> Welcome, <%= viewaccount.getSalutation() %> <%= viewaccount.getfName()%> <%= viewaccount.getlName() %> </h4>
	<hr><br>
	<p> Username: <%= viewaccount.getUsername() %></p>
	<p> Birthdate: <%
			java.text.DateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
			out.print(formatter.format(viewaccount.getBirthday()));
			%></p]>
	<p> Date Joined: <%= (viewaccount.getDateJoined().getMonth()+1) +"/" +viewaccount.getDateJoined().getDate() + "/" + 
		(viewaccount.getDateJoined().getYear()+1900) %> </p>
	<p> About Me: <%= viewaccount.getAboutme() %> </p>
	
	<h5><%= viewaccount.getTotCount() %></h5>
	<h5><%= viewaccount.getTotDonate() %></h5>
	<h5><%= viewaccount.getTotTrans() %></h5>
	<h5><%= viewaccount.getTotCollection() %></h5>
	
	<p> Gender: <%=viewaccount.getGender() %></p>

	
	<a href='EditProfile.jsp'>Edit Profile </a><br/>
	<a href='CreatePost.jsp'>Post Message</a><br/></br>
	<a href='ItemController'>The Store</a><br>
	<a href='PostController'>View Forums</a><br>
	<% if(viewaccount.isAdmin()) { %>
	<a href='Backup.jsp'>Create Backup</a></br>
	<% } %>
	<%
	  }
	%>
	</br></br>
	<form action = "LogoutController" method = "post">
	<input type = "submit" value = "Logout" />
	</form><br/>
</div>
</body>
</html>
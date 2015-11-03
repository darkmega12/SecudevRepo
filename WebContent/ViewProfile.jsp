<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.Account,model.Post,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% 
	if(session.getAttribute("account") == null && session.getAttribute("viewAccount") == null)
	{
		response.sendRedirect("index.jsp");
	}
	else
	{
		Account curr = (Account) session.getAttribute("account");
		Account viewAccount = (Account) session.getAttribute("viewAccount"); 
		session.setAttribute("isAdmin", curr.isAdmin());
%>
<h4> Username: <%= viewAccount.getUsername() %></h4>
<h5> <%= viewAccount.getTotCount() %></h5>
<h5> <%= viewAccount.getTotDonate() %></h5>
<h5> <%= viewAccount.getTotTrans() %></h5>
<h5> <%= viewAccount.getTotCollection() %></h5>
<h4> Birthdate: <%
		java.text.DateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
		out.print(formatter.format(viewAccount.getBirthday()));
		%></h4>
<h4> Gender: <%=viewAccount.getGender() %></h4>
<h4> Date Joined: <%= viewAccount.getDateJoined() %> </h4>
<h4> About Me: <%= viewAccount.getAboutme() %> </h4><br/>

<% if(curr.isAdmin()) 
	{
	
	}
  }
%>
</body>
</html>
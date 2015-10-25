<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.Account,model.Post,java.util.HashMap,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Post-its here!</title>
<style>
	.align{
		text-align: center;
	}
	.demo {
		border:1px solid #C0C0C0;
		border-collapse:collapse;
		padding:10px;
		width: 100%;
	}
	.demo th {
		border:1px solid #C0C0C0;
		padding:10px;
		background:#F0F0F0;
	}
	.demo td {
		border:1px solid #C0C0C0;
		padding:10px;
	}
	
	#content {
		float: left;
		width: 80%;
		
	}
	
	#footer {
		
		position: relative;
		float: left;
		width: 100%;
	}
	
	#page_footer {
		font-size: 18px;
	}
	
	.sub { 
     background: none;
     border: none;
     color: #0066ff;
     text-decoration: underline;
     cursor: pointer; 
	}
</style>
</head>
<body>

<% if(session.getAttribute("account") == null) 
{
	response.sendRedirect("index.jsp");
}
else
{
	Account curr = (Account) session.getAttribute("account");
	boolean isAdmin = curr.isAdmin();
%>

<h2>Global Posts</h2>
<h3><a href="HomePage.jsp">Home</a></h3>

	<div id="search_pane">
		<form action="SearchController" method="get">
			<div id="basic_search">
			Basic Search:
				<input name="search"/>
			</div>
			Advance Search:
			<div id="advance_search">
				<div id="search_adv1">
					<input name="search1" /> 
					<select name="searchtype1">
						<option value="username">name</option>
						<option value="message">post</option>
						<option value="before date">before date</option>
						<option value="after date">after date</option>
						<option value="during date">during date</option>
					</select>
					<select name="logicgate1">
						<option value="N/A">N/A</option>
						<option value="OR">OR</option>
						<option value="AND">AND</option>
					</select>
				</div>
				<div id="search_adv2">
					<input name="search2" /> 
					<select name="searchtype2">
						<option value="username">name</option>
						<option value="message">post</option>
						<option value="before date">before date</option>
						<option value="after date">after date</option>
						<option value="during date">during date</option>
					</select>
					<select name="logicgate2">
						<option value="N/A">N/A</option>
						<option value="OR">OR</option>
						<option value="AND">AND</option>
					</select>
				</div>
				<div id="search_adv3">
					<input name="search3" /> 
					<select name="searchtype3">
						<option value="username">name</option>
						<option value="message">post</option>
						<option value="before date">before date</option>
						<option value="after date">after date</option>
						<option value="during date">during date</option>
					</select>
				</div>
			</div>
			<div>
				Type of Search: Basic<input type="radio" name="searchType" value="basic" checked/>
				Advanced<input type="radio" name="searchType" value="advanced"/>
				<input type="submit" value="Search"/>
			</div>
		</form>
	</div>
	<br/>
<div id = "content">
	<table class="demo">
		<thead>
		<tr>
			<th>Account</th>
			<th>Post</th>
		</tr>
		</thead>
		<tbody>
		<% 
			@SuppressWarnings (value="unchecked")
			HashMap<String, Account> accounts = (HashMap<String, Account>)session.getAttribute("accounts");
			@SuppressWarnings (value="unchecked")
			ArrayList<Post> posts = (ArrayList<Post>)session.getAttribute("posts");
			Account tempAccount;
		%>
		<% for(int i = 0; i < posts.size(); i++){ %>
		<tr>
			<td>
				<div class="align">
				<% tempAccount = accounts.get(posts.get(i).getUsername()); %>
				<%= tempAccount.getfName() %> <br/>
				<form action="EditProfileController" method="get">
				<input type="submit" name="username" class="sub" value=<%= tempAccount.getUsername() %>> <br/>
				</form>
				<%= tempAccount.getDateJoined() %>
				</div>
			</td>
			<td> 
				<div class="align">
				<%  java.text.DateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
					out.print(formatter.format(posts.get(i).getDateCreated()));
				%> 
				<% if(posts.get(i).getUsername().equals(curr.getUsername()) || isAdmin) { %>
					<form clas="left" action = "EditPostController" method="post">
						<input type="hidden" name ="index" value = <%= i %>>
						<input type="hidden" name="post_id" value = <%= posts.get(i).getPostid()%>>
						<input type="hidden" name="username" value = <%= posts.get(i).getUsername()%>>
						<input type="submit" class="sub" value="edit"/>
					</form>
					<form class="right" action = "DeletePostController" method="post">
						<input type="hidden" name ="index" value = <%= i %>>
						<input type="hidden" name="post_id" value = <%= posts.get(i).getPostid()%>>
						<input type="hidden" name="username" value = <%= posts.get(i).getUsername()%>>
						<input type="submit" class="sub" value="delete">
					</form>
				<% } %>
				<br>
				<%= posts.get(i).getMessage() %> <br>
				<% out.print(formatter.format(posts.get(i).getDateModified())); %>
				</div>
			</td>
		</tr>
		<% } %>
		</tbody>
	</table>
</div>
<br><br>
<div id="footer">

<form action="PostController" method="get">
PAGE NUMBER: 
<% 	int total_pages = ((int)session.getAttribute("total")) / 10; 
	if((int)session.getAttribute("total") % 10 > 0)
		total_pages++;
	for(int i=1; i <= total_pages; i++) { %>
		<input type="submit" name="page_num" id="page_footer" class="sub" value = <%= i %>>
<% } %>
</form>
</div>
<% } %>
</body>
</html>
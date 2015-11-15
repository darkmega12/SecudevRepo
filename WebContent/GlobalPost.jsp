<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.Account,model.Post,java.util.HashMap,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="styles.css" media="screen" />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forums</title>
<style>
	body{	
	    margin-top: 0px;
	    margin-right: 0px;
	    margin-left: 0px;
	    margin-bottom: 0px;
		}
</style>
</head>
<body>
	<div id="nav">
			<h1>FORUMS</h1>
			<a href="HomePage.jsp">Home</a>
			<a href='PostController'>View Forums</a>
			<a href="ItemController">Store</a><br>
	</div>
	<% if(session.getAttribute("account") == null) 
	{
		response.sendRedirect("index.jsp");
	}
	else
	{
		Account curr = (Account) session.getAttribute("account");
		boolean isAdmin = curr.isAdmin();
	%>

	<div id="search_pane">
		<form action="SearchController" method="get">
			<div id="basic_search">
				<input type="text" placeholder="Search..." name="search"/>
			</div>
			
			<div id="advance_search">
				Advance Search:
					<div class="input_fields_wrap">
					    <button class="add_field_button">Add Criteria</button>
					    <div>
						    <input type="text" name="mytext[]">
						    <select name="searchtype1">
							    <option value="username">Username</option>
								<option value="message">Post</option>
								<option value="before date">Before Date</option>
								<option value="after date">After Date</option>
								<option value="during date">On Date</option>
						    </select>
					    	<select name="operator1">
							    <option value="N/A">N/A</option>
							    <option value="AND">AND</option>
								<option value="OR">OR</option>
						    </select>
					    </div>
					    
					</div>
			</div>
			<div class="search_preferences">
				<b>Date Format: YYYY-MM-DD</b><br>
				Type of Search: <input type="radio" name="searchType" value="basic" checked/> Basic
				<input type="radio" name="searchType" value="advanced"/> Advanced
				</br></br>
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
					<form action = "EditPostController" method="post">
						<input type="hidden" name="post_id" value = <%=posts.get(i).getPostid()%>>
						<input type="hidden" name="username" value = <%=posts.get(i).getUsername()%>>
						<input type="submit" class="sub" name="edit" value="edit"/>
						<input type="submit" class="sub" name="edit" value="delete">
					</form>
				<% } %>
				<br>
				<%= posts.get(i).getMessage() %> <br><br>
				
				<img src="/TestSecuProj/images?id=<%=posts.get(i).getPostid()%>"></img>
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
<% 	int total_pages = ((Integer)session.getAttribute("total")) / 10; 
	if((Integer)session.getAttribute("total") % 10 > 0)
		total_pages++;
	for(int i=1; i <= total_pages; i++) { %>
		<input type="submit" name="page_num" id="page_footer" class="sub" value = <%= i %>>
<% } %>
</form>
</div>
<% } %>	

<script>
$(document).ready(function() {
    var max_fields      = 20; //maximum input boxes allowed
    var wrapper         = $(".input_fields_wrap"); //Fields wrapper
    var add_button      = $(".add_field_button"); //Add button ID
    
    var x = 1; //initlal text box count
    $(add_button).click(function(e){ //on add input button click
        e.preventDefault();
        if(x < max_fields){ //max input box allowed
            x++; //text box increment
            $(wrapper).append('<div><input type="text" name="mytext[]"/><select name="searchtype1"><option value="username">Username</option><option value="message">Post</option><option value="before date">Before Date</option><option value="after date">After Date</option><option value="during date">On Date</option></select><select name="operator1"><option value="N/A">N/A</option><option value="AND">AND</option><option value="OR">OR</option></select><a href="#" class="remove_field">Remove</a></div>'); //add input box
        }
    });
    
    $(wrapper).on("click",".remove_field", function(e){ //user click on remove text
        e.preventDefault(); $(this).parent('div').remove(); x--;
    })
});
</script>
</body>
</html>
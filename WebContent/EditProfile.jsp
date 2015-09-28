<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.Account,java.util.Date,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Edit Profile</title>
	<!--Stylesheets-->
	<link rel="stylesheet" href="css/jquery-ui.css">
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/signin.css" rel="stylesheet">

	<!--Scripts-->
	<script src="js/jquery-2.1.4.min.js"></script>
	<script src="js/jquery-ui.js"></script>

	<% 
		if(session.getAttribute("account") == null)
		{
			response.sendRedirect("index.jsp");
		}
		else
		{
			Account curr = (Account) session.getAttribute("account");
	%>

	<script type="text/javascript">
		$(function() {
			$( "#birthdate" ).datepicker({minDate: new Date(1900,1-1,1), maxDate: '-18Y',
			  defaultDate: new Date(1970,1-1,1),
			  changeMonth: true,
			  changeYear: true,
			  yearRange: '-110:-18' });
			<% if(curr.getGender().equals("Male")){ %>
				$("#salutationF").hide();
			<% }else{ %>
				$("#salutationM").hide();
			<% } %>
			$('input:radio[name="sex"]').change(function(){
				if($(this).val() == "Male"){
					$("#salutationF").hide();
					$("#salutationM").show();
				} else{
					$("#salutationM").hide();
					$("#salutationF").show();

				}
			});	
		
		});
		function validateRegistration(){
			var regexName = /^[a-zA-Z\s]{1,50}$/; 
			var regexUsername = /^[a-zA-Z0-9_]{1,50}$/; 
			var isValid = true;
			$(".error").each(function(){
				$(this).removeClass("error");
			})
			
			if (!regexName.test($('#firstname').val()) || $("#firstname").val().trim().length == 0) {
				$('#firstname').addClass("error");
				isValid = false;
			}
			if (!regexName.test($('#lastname').val()) || $("#lastname").val().trim().length == 0) {
				$('#lastname').addClass("error");
				isValid = false;
			}
			if (!regexUsername.test($('#username').val()) || $("#username").val().trim().length == 0) {
				$('#username').addClass("error");
				isValid = false;
			}
			return isValid;
		}
	</script>
  </head>

  <body>
	<h2>  </h2> <br/>

	<div class="container">

      <form action= "EditProfileController" class="form-signin" onsubmit="return validateRegistration()" method="post">
        <h2 class="form-signin-heading">Edit <%= curr.getUsername() %>'s Profile</h2>
        
		<label for="firstname">First name</label>
        <input id="firstname" class="form-control" name ="firstname" autofocus maxlength="50" value = <%= curr.getfName() %>>
        <label for="lastname" >Last name</label>
        <input id="lastname" class="form-control" name="lastname" maxlength="50" value = <%= curr.getfName() %>>

		<label>Gender</label>
		<div class="form-group radio-group">
			<% if(curr.getGender().equals("Male")) 
			{
			%>
				<input type="radio" name="sex" value="Male" checked> Male 
				<input type="radio" name="sex" value="Female" > Female
			<% } else { %>
				<input type="radio" name="sex" value="Male" > Male
				<input type="radio" name="sex" value="Female" checked> Female
			<% } %>
		</div>
		<label class="control-label" for="salutation">Salutation</label>
		<select id="salutationM" class="form-control" name="salutationM">
			<option value ="Mr" <% if(curr.getSalutation().equals("Mr") && curr.getGender().equals("Male")){%> selected="selected" <% } %>>Mr</option>
			<option value ="Sir" <% if(curr.getSalutation().equals("Sir") && curr.getGender().equals("Male")){%> selected="selected" <% } %>>Sir</option>
			<option value ="Senior" <% if(curr.getSalutation().equals("Senior") && curr.getGender().equals("Male")){%> selected="selected" <% } %>>Senior</option>
			<option value ="Count" <% if(curr.getSalutation().equals("Senior") && curr.getGender().equals("Male")){%> selected="selected" <% } %>>Count</option>
		</select>
		
		<select id="salutationF" class="form-control" name="salutationF">
			<option value ="Miss" <% if(curr.getSalutation().equals("Miss") && curr.getGender().equals("Female")){%> selected="selected" <% } %>>Miss</option>
			<option value ="Ms" <% if(curr.getSalutation().equals("Ms") && curr.getGender().equals("Female")){%> selected="selected" <% } %>>Ms</option>
			<option value ="Mrs" <% if(curr.getSalutation().equals("Miss") && curr.getGender().equals("Female")){%> selected="selected" <% } %>>Mrs</option>
			<option value ="Madame" <% if(curr.getSalutation().equals("Madame") && curr.getGender().equals("Female")){%> selected="selected" <% } %>>Madame</option>
			<option value ="Majesty" <% if(curr.getSalutation().equals("Majesty") && curr.getGender().equals("Female")){%> selected="selected" <% } %>>Majesty</option>
			<option value ="Señora"<% if(curr.getSalutation().equals("Senora") && curr.getGender().equals("Female")){%> selected="selected" <% } %>>Señora</option>
		</select>
		
		<label for="birthdate">Birthdate</label>
        <input id="birthdate" class="form-control" name="birthdate"
        <% try { 
        	SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        	String formatDate = format.format(curr.getBirthday());
        %>
        	placeholder = <%= formatDate %> value = <%= formatDate %>
        <% }catch(Exception e){} %>
        >

		<label for="aboutme">About Me</label>
        <textarea id="aboutme" class="form-control" name="aboutme" rows=5><%= curr.getAboutme() %></textarea>
		
		
        <label for="password">Password</label>
        <input type="password" id="password" name="password" class="form-control" value = <%= curr.getPassword() %>>
        
        <% 
    	if(session.getAttribute("isAdmin") != null){
	    	boolean isAdmin = (boolean)session.getAttribute("isAdmin"); 
	
	    	
			if(isAdmin) 
			{
				out.println("<label class='control-label' for='AccessControl'>Access Control</label>");
				out.println("<select id='AccessControl' class='form-control' name='accesscontrollolplsdonthack'>"
						+"<option value ='Adminplsdonthack'>Admin</option>"
						+"<option value ='User'>User</option>"
					+"</select>");
			}	
		}
		%>
        
        <button class="btn btn-lg btn-primary btn-block" type="submit">Save</button>
        <a href="HomePage.jsp" class="btn btn-lg btn-primary btn-block" >Cancel</a>
      </form>

    </div>
	
	<% } %>
  </body>
</html>
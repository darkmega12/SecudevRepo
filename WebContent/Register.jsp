<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Welcome</title>
	<!--Stylesheets-->
	<link rel="stylesheet" href="css/jquery-ui.css">
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/signin.css" rel="stylesheet">

	<!--Scripts-->
	<script src="js/jquery-2.1.4.min.js"></script>
	<script src="js/jquery-ui.js"></script>

	<script type="text/javascript">
		$(function() {
			$( "#birthdate" ).datepicker({minDate: new Date(1900,1-1,1), maxDate: '-18Y',
			  defaultDate: new Date(1970,1-1,1),
			  changeMonth: true,
			  changeYear: true,
			  yearRange: '-110:-18' });
			$("#salutationF").hide();
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
	<style>
		#errors{
			font-size: 20px;
			color: red;
		}
	</style>
  </head>

  <body>
	<!--this is for sign in -->
<!--
    <div class="container">

      <form class="form-signin">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="inputUsername" class="sr-only">Username</label>
        <input id="inputUsername" class="form-control" placeholder="Username" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
    </div>
-->
	<div class="container">

      <form action= "RegisterController" class="form-signin" onsubmit="return validateRegistration()" method="post">
        <h2 class="form-signin-heading">Register</h2>
        	<%ArrayList<String> errors = (ArrayList<String>)session.getAttribute("registerErrors"); %>
			<% if(errors != null) { %>
				<% if(errors.size() > 0) { %>
					<p id="errors">
					<% for(String error: errors) { %>
						<%= error %> <br/>
					<% } %>
					</p>
				<% session.setAttribute("registerErrors", null); //so that error message only shows once %>
				<% } %>
			<% } %>
			
		<label for="firstname">First name</label>
        <input id="firstname" class="form-control" name ="firstname" autofocus maxlength="50">
        <label for="lastname" >Last name</label>
        <input id="lastname" class="form-control" name="lastname" maxlength="50">

		<label>Gender</label>
		<div class="form-group radio-group">
			<input type="radio" name="sex" value="Male" checked> Male 
			<input type="radio" name="sex" value="Female"> Female 
		</div>
		<label class="control-label" for="salutation">Salutation</label>
		<select id="salutationM" class="form-control" name="salutationM">
			<option value ="Mr">Mr</option>
			<option value ="Sir">Sir</option>
			<option value ="Senior">Senior</option>
			<option value ="Count">Count</option>
		</select>
		
		<select id="salutationF" class="form-control" name="salutationF">
			<option value ="Miss">Miss</option>
			<option value ="Ms">Ms</option>
			<option value ="Mrs">Mrs</option>
			<option value ="Madame">Madame</option>
			<option value ="Majesty">Majesty</option>
			<option value ="Senora">Senora</option>
		</select>
		
		<label for="birthdate">Birthdate</label>
        <input id="birthdate" class="form-control" name="birthdate" placeholder ="MM/DD/YYYY">
		<label for="username">Username</label>
        <input id="username" class="form-control" name = "username" maxlength="50">
		
		<label for="aboutme">About Me</label>
        <textarea id="aboutme" class="form-control" name="aboutme" rows=5 ></textarea>
		
		
        <label for="password">Password</label>
        <input type="password" id="password" name="password" class="form-control" >
        
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
        
        <button class="btn btn-lg btn-primary btn-block" type="submit">Register</button>
      </form>

    </div>
	
  </body>
</html>
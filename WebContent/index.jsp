<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
</head>
<body>
<% 
	if(session.getAttribute("one")==null)
	{
		session.setAttribute("one", "one");
		response.sendRedirect("https://securedev.mybluemix.net");
	}
%>
<% if(session.getAttribute("account") != null)
	{
 		response.sendRedirect("https://securedev.mybluemix.net/user");
	}
%>
    <div class="container">

      <form class="form-signin" action="https://securedev.mybluemix.net/LoginController" method="post">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="inputUsername" class="sr-only">Username</label>
        <input id="inputUsername" class="form-control" placeholder="Username" name = "username" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Password" name = "password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
      <form class="form-signin" action="RegisterController" method="get">
        <h2 class="form-signin-heading">New User?</h2>
        <button type="submit" class="btn btn-lg btn-primary btn-block" type="submit">Register</button>
      </form>
      <b>
    </div>
</body>
</html>
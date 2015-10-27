<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.Account"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>The Store</title>
</head>
<style>
	body{
	margin: 0px; padding: 0px;
	}
	#nav{
		background-color: green; width:auto; height:100px;
	}
	#logo-img h1{
		display: inline-block;
		width:400px;
		float:right;
		font-size: 50px;
		text-shadow: 5px 2px orange;
	}
	#left-nav{
		float:left;
		width: 300px;
		height:600px;
		border-right: 1px solid gray;
	}
	#left-nav a{
		margin-left:30px;
	}
	#left-nav .paypal{
		margin: 20px;
	}
	select{
		width: 130px;
		float:right;
		margin-right:10px;
		margin-top:5px;
	}
	.btn{
		width:250px;
		height:30px;
		margin-left:20px;
		font-size: 15px;
		border-radius: 5px;
		color: #FFFFFF;
	    background-color: #8AC007;
	    box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
	}
	.btn:hover , .btn:active{
		background-color: #ffffff;
	    color: #8AC007;
	    box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19);
	}
	
	.container{
		width:1000px;
		height:600px;
		margin-left: 320px;
	}
	
	.shop{
		width:750px;
		height:600px;
		margin-left:130px;
		margin-right:100px;
		box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19);
	}
	.item img{
		width:300px;
		height:300px;
		display:inline;
		float:right;
	}
	.item h2, .item h1{
	margin-top:30px;
		clear:none; width: 400px;display:inline;float:left;margin-left:40px;
	}
	.item p{
		clear:none; width:400px;display:inline;float:left; margin-left:40px; margin-top:30px;
	}

</style>

<body>
<div id="nav">
	<div style="padding-top:70px; margin-left:30px;">
		<a href="HomePage.jsp">Home</a>
		<a href='PostController'>View Forums</a><br>
	</div>
<% if(session.getAttribute("account") == null) 
	{
		response.sendRedirect("index.jsp");
	}
	else
	{
		Account curr = (Account) session.getAttribute("account");
	%>
	<%}%>
	
	<div id="logo-img"><img src ="http://drupalstyle.com/sites/default/files/images/shopping_bag.png"> 
		<h1>THE STORE</h1>
	</div>
	<div id="left-nav">
		<div class="paypal">
			<!--get from controller (values) then pass to data-amount is selected-->
			<select> 
				<option value="5USD">$5</option>
				<option value="10USD">$10</option>
				<option value="20USD">$20</option>
			</select>
			<script async="async" src="https://www.paypalobjects.com/js/external/paypal-button.min.js?merchant=lokalista.ph@gmail.com" 
			    data-button="donate" 
			    data-name="Donation" 
			    data-quantity="1000000000000" 
			    data-amount="10" 
			    data-currency="USD" 
			    data-env="sandbox"
			></script>
		</div>
		<button class="btn">Cart (Temporary Checkout)</button>
	</div>
	
	<div class = "container">
		<div class="shop">
			<div class="item">
				<h2>De La Salle University Jacket</h2>
				<img src=http://universidad.com.ph/wp-content/themes/shopperpress/thumbs/lasalle_solar.png>
				<p>Another clothing to wear proud and loud during the <br> upcoming 76th Animo games!</p>
				<h2>PHP 1355</h2>
				<button class="btn">Add to Cart</button>
			</div>
		</div>
	</div>
</div>


</body>
</html>
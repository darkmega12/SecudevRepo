<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.Account,model.Item,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="styles.css" media="screen" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>The Store</title>
</head>
<style>
		body{	
	    margin-top: 0px;
	    margin-right: 0px;
	    margin-left: 0px;
	    margin-bottom: 0px;
		}
</style>

<body>
	<div id="nav">
			<h1>THE STORE</h1>
			<a href="HomePage.jsp">Home</a>
			<a href='PostController'>View Forums</a>
			<a href="Store.jsp">Store</a><br>
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
	
		<div id="left-nav">
			<div class="paypal">
				<!--get from controller (values) then pass to data-amount is selected-->
				<select> 
					<option value="5USD">$5</option>
					<option value="10USD">$10</option>
					<option value="20USD">$20</option>
				</select>	
				<form action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post" target="_top">
					<input type="hidden" name="cmd" value="_s-xclick">
					<input type="hidden" name="hosted_button_id" value="E74NGM4GNUTQY">
					<input type="image" src="https://www.sandbox.paypal.com/en_US/i/btn/btn_donate_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
					<img alt="" border="0" src="https://www.sandbox.paypal.com/en_US/i/scr/pixel.gif" width="1" height="1">
				</form>		
			</div>
			
			<div class="paypal">
	<!-- 		<form target="paypal" action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post" > -->
	<!-- 			<input type="hidden" name="cmd" value="_s-xclick"> -->
	<!-- 			<input type="hidden" name="encrypted" value="-----BEGIN PKCS7-----MIIHHwYJKoZIhvcNAQcEoIIHEDCCBwwCAQExggE6MIIBNgIBADCBnjCBmDELMAkGA1UEBhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWExETAPBgNVBAcTCFNhbiBKb3NlMRUwEwYDVQQKEwxQYXlQYWwsIEluYy4xFjAUBgNVBAsUDXNhbmRib3hfY2VydHMxFDASBgNVBAMUC3NhbmRib3hfYXBpMRwwGgYJKoZIhvcNAQkBFg1yZUBwYXlwYWwuY29tAgEAMA0GCSqGSIb3DQEBAQUABIGAWN1SH7kV9pXIDECBqMVtuHFy+pEyoKtvpS16i42C07oBMGXaoBtrJ07aMG7W3GUj0fdGNgOUAQLq7EUkrEcJOhtaK087oiqj+y2yjpjmmaJQsssotpV2+ypcVbUD8N8niaU9tkDEY34GvzT9WSo68wA+aXlamVQfzbTkX5yl0X0xCzAJBgUrDgMCGgUAMGsGCSqGSIb3DQEHATAUBggqhkiG9w0DBwQIiJK5Bv0KYJKASM/RyLvsAD0SHdBdpuRhzqw6RMjQe7+bAtSev0gprNLvJl8GH22NtsAvxq0yM1lBjEHIKsUzQBUbJM8x3wOvvvX3jf7XaobRWKCCA6UwggOhMIIDCqADAgECAgEAMA0GCSqGSIb3DQEBBQUAMIGYMQswCQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTERMA8GA1UEBxMIU2FuIEpvc2UxFTATBgNVBAoTDFBheVBhbCwgSW5jLjEWMBQGA1UECxQNc2FuZGJveF9jZXJ0czEUMBIGA1UEAxQLc2FuZGJveF9hcGkxHDAaBgkqhkiG9w0BCQEWDXJlQHBheXBhbC5jb20wHhcNMDQwNDE5MDcwMjU0WhcNMzUwNDE5MDcwMjU0WjCBmDELMAkGA1UEBhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWExETAPBgNVBAcTCFNhbiBKb3NlMRUwEwYDVQQKEwxQYXlQYWwsIEluYy4xFjAUBgNVBAsUDXNhbmRib3hfY2VydHMxFDASBgNVBAMUC3NhbmRib3hfYXBpMRwwGgYJKoZIhvcNAQkBFg1yZUBwYXlwYWwuY29tMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3luO//Q3So3dOIEv7X4v8SOk7WN6o9okLV8OL5wLq3q1NtDnk53imhPzGNLM0flLjyId1mHQLsSp8TUw8JzZygmoJKkOrGY6s771BeyMdYCfHqxvp+gcemw+btaBDJSYOw3BNZPc4ZHf3wRGYHPNygvmjB/fMFKlE/Q2VNaic8wIDAQABo4H4MIH1MB0GA1UdDgQWBBSDLiLZqyqILWunkyzzUPHyd9Wp0jCBxQYDVR0jBIG9MIG6gBSDLiLZqyqILWunkyzzUPHyd9Wp0qGBnqSBmzCBmDELMAkGA1UEBhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWExETAPBgNVBAcTCFNhbiBKb3NlMRUwEwYDVQQKEwxQYXlQYWwsIEluYy4xFjAUBgNVBAsUDXNhbmRib3hfY2VydHMxFDASBgNVBAMUC3NhbmRib3hfYXBpMRwwGgYJKoZIhvcNAQkBFg1yZUBwYXlwYWwuY29tggEAMAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQEFBQADgYEAVzbzwNgZf4Zfb5Y/93B1fB+Jx/6uUb7RX0YE8llgpklDTr1b9lGRS5YVD46l3bKE+md4Z7ObDdpTbbYIat0qE6sElFFymg7cWMceZdaSqBtCoNZ0btL7+XyfVB8M+n6OlQs6tycYRRjjUiaNklPKVslDVvk8EGMaI/Q+krjxx0UxggGkMIIBoAIBATCBnjCBmDELMAkGA1UEBhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWExETAPBgNVBAcTCFNhbiBKb3NlMRUwEwYDVQQKEwxQYXlQYWwsIEluYy4xFjAUBgNVBAsUDXNhbmRib3hfY2VydHMxFDASBgNVBAMUC3NhbmRib3hfYXBpMRwwGgYJKoZIhvcNAQkBFg1yZUBwYXlwYWwuY29tAgEAMAkGBSsOAwIaBQCgXTAYBgkqhkiG9w0BCQMxCwYJKoZIhvcNAQcBMBwGCSqGSIb3DQEJBTEPFw0xNTEwMjgwMDMzMDFaMCMGCSqGSIb3DQEJBDEWBBTxGBHc672SE/W5P8IdjG9qtOOG/DANBgkqhkiG9w0BAQEFAASBgHxaDqf6Qesm6nscCdmdTEjp8cUbXHyzExWus86V0P7wDOByUq/6I2oNEr6Fk0TItk5qaW/NkWkFwnkSbGPIyhlG2T1Hf+LB5/XIbP6ioROBRz/acVm/OmBLkTEJEQCD35/fvfwIZ0ih6ebJoHTJTQtUZH+6zq8+mlbnn/yolyJ6-----END PKCS7----- -->
	<!-- 			"> -->
	<!-- 			<input type="image" src="https://www.sandbox.paypal.com/en_US/i/btn/btn_viewcart_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!"> -->
	<!-- 			<img alt="" border="0" src="https://www.sandbox.paypal.com/en_US/i/scr/pixel.gif" width="1" height="1"> -->
	<!-- 		</form> -->
			</div>
			<% if(curr.isAdmin()) { %>
			<button class="btn" onclick='overlay()'>Add Item</button>
			
			<button class="btn">View Cart</button>
		</div>
		<div id="overlay">
		     <div class="modal">
		          <h3>Add Item</h3>
		          <form action="ItemController" method="post" enctype="multipart/form-data">
				          <label>Item Name: </label><input type="text" name="itemname" placeholder = "Item Name"><br><br>
				          <label>Item Price: </label><input type="text" name="itemprice" placeholder = "Item Price"><br><br>
				          <label>Item Description: </label> <br><textarea rows="1" cols="1" name="itemdesc"></textarea><br><br>
				           Photo:<input type="file" name="attachment"><br><br>
				           <input type="submit" name="itemsel" value="Add">&nbsp&nbsp
		           </form><br><button onclick="overlay()">Close</button>
		     </div>
		</div>
			<% } %>			
		<div class = "container">
			<div class="shop">
			<%
			ArrayList<Item> it = (ArrayList<Item>)session.getAttribute("items");
			  
			for(int i = 0; i < it.size(); i++) 
			{
			%>
				<div class="item">
					<h2 class="item_name"><%=it.get(i).getName() %></h2>
					<img class="item_img" src="/TestSecuProj/images2?id=<%= it.get(i).getId()%>">
					<p class="description"><%= it.get(i).getDescription() %></p>
					<h2 class="item_price">PHP <%= it.get(i).getPrice() %></h2>
							<div style="padding-left:20px;">
	 					<form target="paypal" action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post"> 
	 						<input type="hidden" name="cmd" value="_s-xclick"> 
	 						<input type="hidden" name="hosted_button_id" value="ZC36GNY68ZCHE"> 
	 						<input type="image" src="https://www.sandbox.paypal.com/en_US/i/btn/btn_cart_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
	 						<img alt="" border="0" src="https://www.sandbox.paypal.com/en_US/i/scr/pixel.gif" width="1" height="1"> 
	 					</form> 
	
				           <%if(isAdmin) {%>....Admin Only:<form action="ItemController" method="post"><input type="hidden" name="itemid" value="n" /> <input type="submit" name="itemsel" value="Edit"/><input type="submit" name="itemsel" value="Remove"/></form>
				           <%}%>
			
						<button class="btn">ADD TO CART</button>
					</div>
				</div>
<%}} %>
			</div>
		</div>

	
	<script>
	function overlay() {
		el = document.getElementById("overlay");
		el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
	}
	</script>
</body>
</html>
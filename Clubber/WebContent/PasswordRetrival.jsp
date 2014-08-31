<%@page import="Utlis.Constants"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% String statusMessage = (String)request.getAttribute(Constants.STATUS_MESSAGE); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Password Retrival</title>
	<link href="CSS/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">	
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	<script src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>	
</head>
<body dir="rtl">
	<div class="password-retrival-area">
		<form class="password-retrival-form" id="passwordRetrival" name="passwordRetrival" method="post" action="PasswordRetrival">
			<label id="emailLabel">דוא"ל</label>
			<input type="text" name="email" id="email" required>
  			<br>
  			<button type="submit" >שלח</button>
		</form>
	</div>
	<div class="message-area">
		<label id="statusMessageLabel">
			<% if(statusMessage != null){ %>
				<%=statusMessage %>
			<%} %>		
		</label>
	</div>
	<script>
	
	// Set default messages  
	jQuery.extend(jQuery.validator.messages, {
	    required: "שדה חובה",
	    email: 'כתובת דוא"ל אינה חוקית',
	});	
	
	// validate fields
	$( "#passwordRetrival" ).validate({
		  rules: {
		    email: {
		      	email: true
		    }
		 }
		});		
	
	</script>		    				
</body>
</html>
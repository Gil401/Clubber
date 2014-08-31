<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="CSS/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	<title>Change Password</title>
</head>
<body dir="rtl">
	<div class="change-password-area">
	<form class="change-password-form" id="changePassword" name="changePassword" method="post" action="changePassword">
		
		<label id="passwordLabel">סיסמה ישנה</label>
		<input type="password" name="oldPassword" id="oldPassword" required>
		<br>
		
		<label id="passwordLabel">סיסמה</label>
		<input type="password" name="password" id="password" required>
 		<br>
		
		<label id="verifyPasswordLabel">אימות סיסמה</label>
		<input type="password" name="verifyPassword" id="verifyPassword" required>
		<br>
		
		<button type="submit" >שנה</button>
		
		<label id="messageText"></label>
	</form>
	</div>
	<script>
	// Set error message  
	
	jQuery.extend(jQuery.validator.messages, {
	    required: "שדה חובה",
	    equalTo: "סיסמאות אינן תואמות",
	});
	

	$( "#changePassword" ).validate({
		  rules: {
			verifyPassword: {
	      		equalTo: "#password"
		  	}
		 }
		});		

	</script>
</body>
</html>
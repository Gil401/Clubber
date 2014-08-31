<%@page import="ClubberLogic.UserType"%>
<%@page import="Utlis.Constants"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% String userTypeStr = (String)request.getAttribute(Constants.WHO_AM_I); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="CSS/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="CSS/Main.css" rel="stylesheet" type="text/css">	
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<title>Welcome Page</title>
</head>
<body>
 <div class= 'site-header'>
    <div class= 'upper-header'>
    	<div class='logo'></div>
    	<div class='advertisements'></div>
    </div>
    <div class='nav-bar'> פרופיל | המכרזים שלי | התרעות </div>
    <br>
    </div>
    <div class='main-container'>  
    
	This is Welcome Page
	<div class="profile">
		<a href="ClientProfile.jsp">עבור לפרופיל</a>
	</div> 
	</div>
</body>
</html>
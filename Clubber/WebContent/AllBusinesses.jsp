<%@page import="ClubberLogic.UserType"%>
<%@page import="Utlis.Constants"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>כל המקומות</title>
    <link href="CSS/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
</head>
<body dir="rtl">
	<div class="all-businesses">
	</div>
<script>
		
	function getAllBusinesses(){
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "DBDataAllBusinesses"},
	        success: function(businesses) {

				if (businesses.length > 0) {
					for (var i = 0; i < businesses.length; i++) {
						
						var business = '<div id=businesse'+businesses[i].m_Id +'class="businesse" style="background-color:lightblue;" onClick="goToBusinessProfile('+businesses[i].m_Id +');">' +
						'<div class = "businesse-id" style="visibility: hidden" >'+ businesses[i].m_Id + '</div>'+
						'<div class = "businesse-name">'+ businesses[i].m_Name + '</div>'+
						'<div class = "businesse-type">' + businesses[i].m_BusinessTypeId.Name + '</div>'+
						'<div class = "businesse-range-ages">' + businesses[i].m_AreaId.Name	+ '</div>'+
						'<div class = "businesse-description">'	+ businesses[i].m_CityId.Name + '</div>'+
						'<div class = "businesse-photo">' + businesses[i].m_Photo + '</div>'+
						'</div>';
						
						$('.all-businesses').append($(business));
									
					}
					
				}
				else {
					$(businesses).append('<h1>אין מקומות בילוי זמינים</h1>');
				}	        	
	        	
	        },
	        error: function(data){
	            	console.log("error");}
	 	});
	}
	
	$(function onLoad(){
		getAllBusinesses();
	});
	
	function goToBusinessProfile(businessId)
	{
		sessionStorage.setItem("businessId", businessId);
		 window.location.href = "BusinessProfile.jsp";
		
	}
	
	
</script>
</body>
</html>
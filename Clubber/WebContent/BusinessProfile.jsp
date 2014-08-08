<%@page import="Utlis.SessionUtils"%>
<%@page import="Utlis.Constants"%>
<%@page import="ClubberLogic.PR"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% String messageText = (String)request.getAttribute(Constants.MESSAGE_TEXT); %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>פרופיל מקום</title>
	    <link href="CSS/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
	    <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
		<script src="//code.jquery.com/jquery-1.10.2.js"></script>
		<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
		<script src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>	
	</head>
<body dir="rtl">
	<div class="business-details">
		<form class="business-details-form" id="businessDetails" name="businessDetails" method="post" action="UpdateBusinessDetails">
		  	
		  	<input type="text" name="id" id="id"  hidden>
		  	
		  	<label id="businessNameLabel">שם</label>
		  	<input type="text" name="name" id="name" required disabled>
		  	<br>
		  	
		  	<label id="businessNameLabel">סוג עסק</label>
		  	<input type="text" name="BusinessTypeId" id="BusinessTypeId" hidden>
		  	<input type="text" name="BusinessTypeName" id="BusinessTypeName" required disabled>
		  	<br>
		  	
		  	<label id="areaLabel">איזור</label>
		  	<input type="text" name="areaId" id="areaId" hidden />			
		  	<select name="areaName" id="areaName" required disabled></select>
		  	<br>

		  	<label id="cityLabel">עיר</label>
		  	<input type="text" name="cityId" id="cityId" hidden/>
		  	<select name="cityName" id="cityName" required disabled></select>			
		  	<br>

		  	<label id="streetLabel">רחוב</label>
		  	<input type="text" name="streetId" id="streetId" required disabled hidden>
		  	<input type="text" name="streetName" id="streetName" required disabled>			
		  	<br>

		  	<label id="streetLabel">מס' בית</label>
		  	<input type="text" name="homeNumber" id="homeNumber" required disabled>			
		  	<br>
		  	
		  	<label id="phoneNumberLabel">טלפון</label>
		  	<input type="text" name="phoneNumber" id="phoneNumber" required disabled>			
		  	<br>
			
			<label id="descriptionLabel">תיאור</label>
			<input name="description" id="description" disabled>
  			<br>
			
			<div class="business-photo">
			
			</div>
			<br>
			  			  			
  			<button id="editBusinessDel" type="button" >ערוך</button>
  			<button id="updateBusinessDel" type="submit" disabled>שמור</button>
		</form>		
		<br>
		<label id="messageText">
			<% if(messageText != null){ %>
				<%=messageText %>
			<%} %>		
		</label>
	</div>
	
	<div class="business-lines">
	</div>	
		
	<script>
	
		// Set error messages  
		jQuery.extend(jQuery.validator.messages, {
		    required: "שדה חובה",	    
		});
		
		$("#editBusinessDel").click(function() {
			$('#name').attr("disabled", false);
			$('#BusinessTypeName').attr("disabled", false);
			$('#areaName').attr("disabled", false);
			$('#cityName').attr("disabled", false);
			$('#streetName').attr("disabled", false);		
			$('#homeNumber').attr("disabled", false);
			$('#phoneNumber').attr("disabled", false);
			$('#description').attr("disabled", false);
			$("#updateBusinessDel").attr("disabled",false);
		});
		
		function getBusinessData(id){
		    $.ajax({
		        url: "GetDBData",
		        type: "post",
		        dataType: 'json',
		        data:{RequestType: "DBDataGetBusinessData", businessId: id},
		        success: function(data) {
		        	
		        	$("#id").val(data.m_Id);
		        	$("#name").val(data.m_Name);
		        	$("#BusinessTypeName").val(data.m_BusinessTypeId.Name);

		        	var areaVal = '<option>' + data.m_AreaId.Name +'</option>';
		        	$("#areaName").append(areaVal);
		        	$("areaId").val(data.m_AreaId.id);

		        	var cityVal = '<option>' + data.m_CityId.Name +'</option>';
		        	$("#cityName").append(cityVal);		   
		        	$("cityId").val(data.m_CityId.id);
		        	
		        	var areaId = data.m_AreaId.id;
					getAllCitiesByArea(areaId);
		        	
		        	$("#streetName").val(data.m_StreetId.Name);
		        	$("#streetId").val(data.m_StreetId.id);
		        	$("#homeNumber").val(data.m_HouseNumber);
		        	$("#phoneNumber").val(data.m_PhoneNumber);
		        	$("#description").val(data.m_Description);
		        	
		        	showAllBusinessLines(data.m_Lines);	 
		        	uploadAreasFromDB();
		        },
		        error: function(data){
		            	console.log("error");}
		    });
		}
		
		function showAllBusinessLines(businessLines){

			var lines = $('.business-lines');

			if (businessLines.length > 0) {
				for (var i = 0; i < businessLines.length; i++) {
					lines.append('<div class="line">');
					lines.append('<div class = "line-date">'
								+ dateConvertor(businessLines[i].startDate)
								+ '</div></br>');
					lines.append('<div class = "line-name">'
							+ businessLines[i].m_LineName + '</div>');
					lines.append('<div class = "line-pic">' + ' Pic ' + '</div>');
					lines.append('<div class = "line-range-ages">' + businessLines[i].minAge	+ '</div>');
					lines.append('<div class = "line-description">'	+ businessLines[i].description + '</div>');
					lines.append('</div>');	
				}
			}
			else {
				lines.append('<h1>אין אירועים זמינים</h1>');
			}
			
		}
		
		function dateConvertor(i_Date) {
			var hebrewDaysNames = new Array("ראשון", "שני", "שלישי", "רביעי", "חמישי",
					"שישי", "שבת");
			var date = new Date(i_Date), day = date.getDay(), fullYear = date.getFullYear(), month = date.getMonth(), dayInMonth = date
					.getDate();

			// המערכים שמאפשרים את המרת השמות לשמות עבריים
			
			var hebrewMonthsNames = new Array("דצמבר", "נובמבר", "אוקטובר", "ספטמבר",
					"אוגוסט", "יולי", "יוני", "מאי", "אפריל", "מרץ", "פברואר", "ינואר");

			// מדפיס את היום
			var getDay = " יום " + hebrewDaysNames[day];

			// מדפיס את החודש
			var getMonth = hebrewMonthsNames[(11 - month)];

			// מדפיס את היום בחודש
			$("#dayInMonth").text(dayInMonth);

			// מדפיס את השנה המלאה
			$("#fullYear").text(fullYear);

			return (getDay + " " + dayInMonth + " " + getMonth + " " + fullYear);
		}

		
		function uploadAreasFromDB(){
		    $.ajax({
		        url: "GetDBData",
		        type: "post",
		        dataType: 'json',
		        data:{RequestType: "DBDataGetBusinessAreasData"},
		        success: function(areasList) {
		        	var areas = $("#areaName");
		        	
		        	//delete former data 
		        	areas.html("");
		        	
					for (var i = 0; i < areasList.length; i++) {
						areas.append('<option id=' + areasList[i].id +'>' + areasList[i].Name + '</option>');
					}
		        },
		        error: function(data){
		            	console.log("error");}
		    });
			
		}
		
		$('#areaName').change(function() {

		    var id = $(this).find(':selected')[0].id;
		    getAllCitiesByArea(id);

		});
		
		function getAllCitiesByArea(id){
		    $.ajax({
		        url: "GetDBData",
		        type: "post",
		        dataType: 'json',
		        data:{RequestType: "DBDataGetBusinessCitiesData", areaId: id},
		        success: function(citiesList) {
		        	
		        	var cities = $("#cityName");
		        	
		        	//delete former data 
		        	cities.html("");
		        	
					for (var i = 0; i < citiesList.length; i++) {
						cities.append('<option>' + citiesList[i].Name + '</option>');
					}

		        },
		        error: function(data){
		            	console.log("error");}
		    });
			
			
		}
		
		$(function onLoad(){
			getBusinessData(sessionStorage.getItem("businessId"));
		});
		
	</script>			
</body>
</html>

<%@page import="Utlis.SessionUtils"%>
<%@page import="Utlis.Constants"%>
<%@page import="ClubberLogic.UserType"%> 
<%@page import="ClubberLogic.PR"%>
<%@  include file="patternTemplates/header.tpl" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<% String messageText = (String)request.getAttribute(Constants.MESSAGE_TEXT); %>
    
    

<br/>
<div id="latest-events">
<div class="container">
 <div class="event-inner">
 <div class="row">                       
                    <div id="latest-event-content" class="col-sm-7 col-md-8">
    
    
	<div class="business-details bg">
		<form class="business-details-form" id="businessDetails" name="businessDetails" method="post" action="UpdateBusinessDetails" enctype="multipart/form-data">
		  	
		  	<input type="text" name="id" id="id"  hidden/>
		  	
			<input type="file" name="pic"id="pic" style="max-width:30%; max-height:30%; float: left;">
		  	
		  	<label id="businessNameLabel">שם</label>
		  	<input type="text" name="name" id="name" required disabled/>
		  	<br>
		  	
		  	<label id="businessNameLabel">סוג עסק</label>
		  	<input type="text" name="BusinessTypeId" id="BusinessTypeId" hidden/>
		  	<select type="text" name="BusinessTypeName" id="BusinessTypeName" required disabled></select>
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
		  	<input type="text" name="streetId" id="streetId" hidden/>
		  	<input type="text" name="streetName" id="streetName" required disabled/>			
		  	<br>

		  	<label id="streetLabel">מס' בית</label>
		  	<input type="text" name="homeNumber" id="homeNumber" required disabled/>			
		  	<br>
		  	
		  	<label id="phoneNumberLabel">טלפון</label>
		  	<input type="text" name="phoneNumber" id="phoneNumber" required disabled/>			
		  	<br>
			
			<label id="descriptionLabel">תיאור</label>
			<input name="description" id="description" disabled/>
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
	</div>	</div></div></div></div></div>
		<%@  include file="patternTemplates/footer.tpl" %>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/jquery.prettyPhoto.js"></script>
<script type="text/javascript" src="js/jquery.fitvids.js"></script>
<script type="text/javascript" src="js/soundcloud.player.api.js"></script>
<script type="text/javascript" src="js/sc-player.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/coundown-timer.js"></script>
<script type="text/javascript" src="js/jquery.backstretch.min.js"></script>
<script type="text/javascript" src="js/switcher.js"></script>
<script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<script src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
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
			$('#pictureLabel').hide();
			$('#pic_container').append('<br><br><br><input type="file" name="pic" id="pic" style="float:right;padding:0; margin:0;max-width:30%; max-height:30%;">');
			
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
		        	
		        	var typeVal = '<option>' + data.m_BusinessTypeId.Name +'</option>';
		        	$("#BusinessTypeName").append(typeVal);
		        	$("#BusinessTypeId").val(data.m_BusinessTypeId.id);

		        	var areaVal = '<option>' + data.m_AreaId.Name +'</option>';
		        	$("#areaName").append(areaVal);
		        	$("#areaId").val(data.m_AreaId.id);

		        	var cityVal = '<option>' + data.m_CityId.Name +'</option>';
		        	$("#cityName").append(cityVal);		   
		        	$("#cityId").val(data.m_CityId.id);

		        	$("#streetName").val(data.m_StreetId.Name);
		        	$("#streetId").val(data.m_StreetId.id);
		        	$("#homeNumber").val(data.m_HouseNumber);
		        	$("#phoneNumber").val(data.m_PhoneNumber);
		        	$("#description").val(data.m_Description);
		        	if (!data.imageUrl) {
		        		$("#pic")[0].style.display = "none";
		        	}
		        	else {
		        		$('#pic').replaceWith('<img src="'+data.imageUrl+'" id="pic" style="max-width:100px; max-height:100px; float:right;margin-left:30px">');
		        	}
		        	
		        	var areaId = data.m_AreaId.id;
		        	getAllCitiesByArea(areaId);

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
		
		function uploadBusinessesTypeFromDB(){
		    $.ajax({
		        url: "GetDBData",
		        type: "post",
		        dataType: 'json',
		        data:{RequestType: "DBDataGetBusinessesTypeData"},
		        success: function(typesList) {
		        	var types = $("#BusinessTypeName");
		        	
		        	//delete former data 
		        	types.html("");
		        	
					for (var i = 0; i < typesList.length; i++) {
						types.append('<option id=' + typesList[i].id +'>' + typesList[i].Name + '</option>');
					}
		        },
		        error: function(data){
		            	console.log("error");}
		    });
			
		}
		
		$('#areaName').change(function() {

			var id = $(this).find('option:selected').attr('id');
			$("#areaId").val(id);
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
						cities.append('<option id='+ citiesList[i].id +'>' + citiesList[i].Name + '</option>');
					}

		        },
		        error: function(data){
		            	console.log("error");}
		    });
		}

		$("#cityName").change(function(){
			
			var id = $(this).find('option:selected').attr('id');
			$("#cityId").val(id);
		});
		
		$("#BusinessTypeName").change(function(){
			
			var id = $(this).find('option:selected').attr('id');
			$("#BusinessTypeId").val(id);
		});		
		
		$(function onLoad(){
			getBusinessData(sessionStorage.getItem("businessId"));
		});
		
	</script>			
</body>
</html>

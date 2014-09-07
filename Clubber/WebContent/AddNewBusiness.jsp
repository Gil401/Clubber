<%@  include file="patternTemplates/header.tpl" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="Utlis.Constants"%>
<% String messageText = (String)request.getAttribute(Constants.MESSAGE_TEXT); %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
		<title>הוספת מקום חדש</title>
	</head>
	<body>
    <div id="latest-events">
        <div class="container">
            <div class="event-inner">                
                <div class="row">                       
                    <div id="latest-event-content" class="col-sm-7 col-md-8"> 
                        <div class="bg">
                            <h2 class="heading">הוספת מקום חדש</h2 >
					        <div class="add-new-business-area">
					  			<form class="add-new-business-form" id="addNewBusiness" name="addNewBusiness" method="post" action="AddNewBusiness" enctype="multipart/form-data">
						  			<div class="message-area">
										<label id=subscribeFailed>
										<% if(messageText != null){ %>
											<%=messageText %>
										<%} %>
										</label>   			
					  				</div>
								  	<br>
								  	
								  	<input type="file" name="pic"id="pic">
								  	<br>
								  	
								  	<div class="lable" id="businessNameLable">שם המקום</div>
								  	<input type="text" name="name" name="name" required>
									<br>
																  	
								  	<div class="lable" id="businessAreaLabel">איזור</div>
		  							<input type="text" name="areaId" id="areaId" hidden />								  	
								  	<select name="areaName" id="areaName" required></select>
								  	<br>
								  	
								  	<div class="lable" id="businessCityLabel">ישוב</div>
		  							<input type="text" name="cityId" id="cityId" hidden/>								  	
								  	<select name="cityName" id="cityName" required></select>
								  	<br>
	
								  	<div class="lable" id="businessStreetLabel">רחוב</div>								  	
								  	<input type="text" name="streetName" id="streetName" required>
								  	<br>
	
								  	<div class="lable" id="businessStructureNumberLabel">מספר בית</div>
								  	<input type="text" name="homeNumber" id="homeNumber">
						
								  	<br>
	
								  	<div class="lable" id="businessPhoneLabel">טלפון</div>
								  	<input type="text" name="phoneNumber" id="phoneNumber" required>
								  	<br>
								  	
								  	<div class="lable" id="businessTypeLabel">סוג המקום</div>
		  							<input type="text" name="BusinessTypeId" id="BusinessTypeId" hidden/>
		  							<select type="text" name="BusinessTypeName" id="BusinessTypeName"></select>
								  	<br>
								  	
								  	<div class="lable" id="businessDescriptionLabel">תיאור</div>
								  	<input type="text" name="description" id="description">
								  	<br>
	
									<div class="business-photo">
										<!--  need to add button to load picture type jpg and then save it to DB -->									
									</div>
								  	<br>
						  			
						  			<button type="submit" >שלח</button>
						  			<input type=reset value="נקה"><br>
								</form>
					    	</div>
					    </div>	                      
                    </div> 
                </div>                          
            </div>
        </div>
    </div>

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
		    number: "שדה זה מכיל רק מספרים",
		});
    
	$( "#addNewBusiness" ).validate({
		  rules: {
			  businessStructureNumber: {
		      	number: true
		    },
	
		    businessPhone: {
	      		number: true
		  	},
		 }
		});		
	
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
				
				$("#areaId").val($("#areaName").find('option:first').attr('id'));
				
			    var id = $("#areaName").find(':first')[0].id;
			    getAllCitiesByArea(id);

				
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
				
				$("#BusinessTypeId").val($("#BusinessTypeName").find('option:first').attr('id'));
				
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
				
				$("#cityId").val($("#cityName").find('option:first').attr('id'));

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
		uploadAreasFromDB();
		uploadBusinessesTypeFromDB();
	});

	
    </script>                                	
	</body>
</html>
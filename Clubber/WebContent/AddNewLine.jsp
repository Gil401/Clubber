<%@  include file="patternTemplates/header.tpl" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="Utlis.Constants"%>
<% String messageText = (String)request.getAttribute(Constants.MESSAGE_TEXT); %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
		<title>הוספת ליין חדש</title>
	</head>
	<body>
    <div id="latest-events">
        <div class="container">
            <div class="event-inner">                
                <div class="row">                       
                    <div id="latest-event-content" class="col-sm-7 col-md-8"> 
                        <div class="bg">
                            <h2 class="heading">הוספת ליין חדש</h2 >
					        <div class="add-new-business-area">
					  			<form class="add-new-line-form" id="addNewLine" name="addNewLine" method="post" action="AddNewLine" enctype="multipart/form-data">
						  			<div class="message-area">
										<label id=subscribeFailed>
										<% if(messageText != null){ %>
											<%=messageText %>
										<%} %>
										</label>   			
					  				</div>
					  				
	  								<input type="file" name="pic" id="pic" >
	  								<br>
					  				
								  	<div class="lable" id="lineNameLable">שם הליין</div>
								  	<input type="text" name="name" name="name" required>
									<br>

								  	<div class="lable" id="linebusinessNameLable">מקום הליין</div>
								  	<input type="text" name="businessId" id="businessId" hidden />
								  	<select type="text" name="businessName" id="businessName" required></select>
									<br>
																  	
								  	<div class="lable" id="lineMusicStyleLabel">סגנון מוזיקה
							         	<div id="musicStyleEdt" class="music-style-area combobox-auction-input">
							        	</div>
								 	</div>
								  	<br>
								  	
									<select type="text" name="dayInWeek" id="dayInWeek" required>
										<option value='1'>א</option>
										<option value='2'>ב</option>
										<option value='3'>ג</option>
										<option value='4'>ד</option>
										<option value='5'>ה</option>
										<option value='6'>ו</option>
										<option value='7'>ש</option>
									</select>
									<br>
	
								  	<div class="lable" id="lineStartDateLabel" required>תאריך פתיחה של הליין</div>								  	
								  	<input type="text" name="startDate" id="startDate" required>
								  	<br>
	
								  	<div class="lable" id="lineEndDateLabel">תאריך סגירה של הליין</div>
								  	<input type="text" name="endDate" id="endDate" disabled>
								  	<br>
	
								  	<div class="lable" id="minAgeLabel">גיל מינימלי</div>
								  	<input type="text" name="minAge" id="minAge" required>
								  	<br>
								  									  	
								  	<div class="lable" id="lineDescriptionLabel">תיאור</div>
								  	<textarea rows="5" cols="20" type="text" name="description" id="description"></textarea>
								  	<br>
	
								  	<div class="lable" id="lineEtranceFeeLabel">כניסה בתשלום</div>
								  	<input type="text" name="etranceFee" id="etranceFee">
								  	<br>
	
								  	<div class="lable" id="lineDJLabel">דיג'יי</div>
								  	<input type="text" name="DJ" id="DJ">
								  	<br>
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
    <script src="js/datepicker-he.js"></script>
    
    <script>
    // Set error messages  
    jQuery.extend(jQuery.validator.messages, {
		    required: "שדה חובה",	    
		});
    	
	// date picker
	$(function() {
		var date = new Date();
		$('#startDate').datepicker({
			dateFormat: "dd/mm/yy",
			minDate: date,
			changeMonth: true,
	      	changeYear: true,
	      	showOn: "button",
	        buttonImage: "images/calendar.gif",
	        buttonImageOnly: true});
	});

	$("#startDate").change(function(){
		var startDate = $("#startDate").val();
		
		$('#endDate').attr("disabled", false);
		
		$('#endDate').datepicker({
			dateFormat: "dd/mm/yy",
			minDate: startDate,
			changeMonth: true,
	      	changeYear: true,
	      	showOn: "button",
	        buttonImage: "images/calendar.gif",
	        buttonImageOnly: true});		
	});
		
	
	function uploadMusicStyleData(){
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "DBDataGetMusicStyleData"},
	        success: function(musicStyleList) {
	        	var musicStyleDiv = $(".music-style-area");
	        	
	        	//delete former data 
	        	musicStyleDiv.html("");

	        	musicStyleDiv.append($('<label><input type="checkbox" id="musicStyle" onClick="toggle(this)" checked>הכל </label>'));
	        	
	        	for(var i=0; i < musicStyleList.length; i++){
	        		
	        		var element = '<label><input type="checkbox" name="musicStyle" checked>' +musicStyleList[i].Name+ '</label>' ;
	        		musicStyleDiv.append($(element));
	        	}	        	
	        },
	        error: function(data){
	            	console.log("error");}
	    });
		
	}

	function uploadAllBusinessData(){
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "GetDBData-BusinessLst"},
	        success: function(businessList) {
	        	var businesses = $("#businessName");
	        	
	        	//delete former data 
	        	businesses.html("");
	        	
				for (var i = 0; i < businessList.length; i++) {
					businesses.append('<option id=' + businessList[i].id +'>' + businessList[i].Name + '</option>');
				}        	
				
				$("#businessId").val($("#businessName").find('option:first').attr('id'));
	        },
	        error: function(data){
	            	console.log("error");}
	    });
		
	}
	
	$("#businessName").change(function() {
		$("#businessId").val($("#businessName").find('option:selected').attr('id'));
	});
	
	$(function onLoad(){
		uploadMusicStyleData();
		uploadAllBusinessData();
	});

	
	

	
    </script>                                	
	</body>
</html>
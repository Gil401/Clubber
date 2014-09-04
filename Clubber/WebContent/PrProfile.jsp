<%@page import="Utlis.SessionUtils"%>
<%@page import="Utlis.Constants"%>
<%@page import="ClubberLogic.PR"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@  include file="patternTemplates/header.tpl"%>
<% String messageText = (String)request.getAttribute(Constants.MESSAGE_TEXT); %>
  
<style>
input{
	margin: 10px;
}
		div.star {
		   width:26px; 
		   height:30px; 
		   background-image:url('images/star.png');
		   background-repeat:no-repeat; 
		   float:right;
		}

		div.yellow-Star {
		   width:26px;
		   height:30px;
		   background-image:url('images/yellowStar.jpg');
		   background-repeat:no-repeat;
		   float:right;
		}
	
</style>

<div id="latest-events">
	<div class="container">
		<div class="event-inner">
			<div class="row">
				<div id="latest-event-content" class="col-sm-7 col-md-8">
<div class="bg">

<h2>פרטי משתמש</h2>
	<div class="user-details">
			<form class="user-details-form" id="userDetails"
				name="userDetails" method="post" action="UpdateUserDetails" enctype="multipart/form-data">

				<input type="file" name="pic"id="pic" style="max-width:30%; max-height:30%; float: left;">
				<div id="pic_container"></div>
				<div style="float: left; margin-top: 100px;">
				
				<button class='btn' type="button" style="width: 160px;" onclick="window.location ='AddNewLine.jsp'">הוסף ליין חדש </button><br ><br>
				<button class='btn' type="button" style="width: 160px;" onclick="window.location ='AddNewBusiness.jsp'">הוסף עסק חדש</button><br><br>
				<button class='btn' type="button" style="width: 160px;" onclick="window.location ='AddNewLine.jsp'">חפש לקוחות</button><br><br>

				</div>
	<br /><br /><br />
			<br>
		  	
		  	<label id="firstnameLabel">שם פרטי</label>
		  	<input type="text" name="firstName" id="firstName" required disabled>
		  	<br>
		  	
		  	<label id="lastnameLabel">שם משפחה</label>
		  	<input type="text" name="lastName" id="lastName" required disabled>			
		  	<br>
		  	
		  	<label id="genderLabel">מין</label>
		  	<div style="width: 69%; float: left">
		  	<input type="radio" name="gender" value="Male" checked disabled >
			זכר
			<input type="radio" name="gender" value="Female" disabled>
			נקבה
			
			</div>
			<br>
			<label id="birthdateLabel">תאריך לידה</label>
			<input type = "datetime" name="birthdate" id="birthdate" size="17" required disabled>
			<br>
			
			<label id="phonenumberLabel">טלפון</label>
			<input type="text" name="phoneNumber" id="phoneNumber" required disabled>
			<br>
			
			<label id="passwordLabel">סיסמה</label>
			<input type="password" name="Password" id="password" required disabled>
  			<br>

			<label id="verifyPasswordLabel">אימות סיסמה</label>
			<input type="password" name="verifyPassword" id="verifyPassword" required disabled>
  			<br>
  			<br ><BR><BR><BR><button id="editUserDel" type="button" >ערוך</button>
  			<button id="updateUserDel" type="submit" disabled>שמור</button>
  			  <div class="user-rating">
				<br /><br /><br />
						
  			
  			<br /><br />	
  			
		<label id="generalLabel">כללי</label>
		<br>
		<div class="generalStars">
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
		</div>
		<br><br>
		<label id="availabilityLabel">זמינות</label>
		<br>
		<div class="availabilityStars">
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
		</div>
		<br><br>
		<label id="reliabilityLabel">נאמנות</label>
		<br>
		<div class="reliabilityStars">
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
		</div>
		<br><br>
		<label id="treatsLabel">פינוקים</label>
		<br>
		<div class="treatsStars">
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
		</div>	
	</div>	
		</form>		
		<br>
		<label id="messageText">
			<% if(messageText != null){ %>
				<%=messageText %>
			<%} %>		
		</label>
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
	
		// date picker
		$(function() {
			var date = new Date();
			var currentYear = date.getFullYear();
			$('#birthdate').datepicker({
				dateFormat: "dd/mm/yy",
				yearRange: "-120:+0",
				minDate: new Date(currentYear - 120, 1, 1),
				maxDate: date,
				changeMonth: true,
		      	changeYear: true,
		      	showOn: "button",
		      	disabled: true,
		        buttonImage: "images/calendar.gif",
		        buttonImageOnly: true
			});	      	
		});

		// Set error messages  
		jQuery.extend(jQuery.validator.messages, {
		    required: "שדה חובה",
		    equalTo: "סיסמאות אינן תואמות"	    
		});
		

		$( "#userDetails" ).validate({
			  rules: {
				verifyPassword: {
		      		equalTo: "#password"
			  	}
			 }
		});		
		
		$("#editUserDel").click(function() {
			$('#firstName').attr("disabled", false);
			$('#lastName').attr("disabled", false);
			$('input[name="gender"]').attr("disabled", false);
			$('#birthdate').datepicker("enable");
			$('#phoneNumber').attr("disabled", false);
			$('#password').attr("disabled", false);		
			$('#verifyPassword').attr("disabled", false);
			$("#updateUserDel").attr("disabled",false);
			$("#editUserDel").attr("disabled",true);
			$('#pictureLabel').hide();
			$('#pic_container').append('<br><br><br><input type="file" name="pic" id="pic" style="float:right;padding:0; margin:0;max-width:30%; max-height:30%;">');
		});
		
		function getUserProfile(){
		    $.ajax({
		        url: "GetDBData",
		        type: "post",
		        dataType: 'json',
		        data:{RequestType: "DBDataUserProfile"},
		        success: function(data) {
		        	$("#firstName").val(data.firstName);
		        	$("#lastName").val(data.lastName);
		        	$('input[name="gender"][value="'+data.gender+'"]').prop("checked", true);

					var date = new Date(data.birthDate);
					var month = date.getMonth() + 1;
					$("#birthdate").val(date.getDate() + "/" +month + "/" + date.getFullYear());

		        	$("#phoneNumber").val(data.phoneNumber);
		        	$("#password").val(data.password);
		        	$('#pic').replaceWith('<img src="'+data.imageUrl+'" id="pic" style="max-width:100px; max-height:100px; float:right">');
					$("#editUserDel").attr("disabled",false);
		        },
		        error: function(data){
		            	console.log("error");}
		    });
		}
		
		function getPrProfileReview(){
		    $.ajax({
		        url: "GetDBData",
		        type: "post",
		        dataType: 'json',
		        data:{RequestType: "DBDataPrProfileReview"},
		        success: function(data) {
					
		        	var i;
		        	if(data != null){
			        	for(i=0; i < data.availability; i++){
			        		var star = $(".availabilityStars").children()[i];
			        		$(star).removeClass("star");
			        		$(star).addClass("yellow-Star");			        		
			        	}
			        	
			        	for(i=0; i < data.realiability; i++){
			        		var star = $(".reliabilityStars").children()[i];
			        		$(star).removeClass("star");
			        		$(star).addClass("yellow-Star");			        		
			        	}
			        	
			        	for(i=0; i < data.treats; i++){
			        		var star = $(".treatsStars").children()[i];
			        		$(star).removeClass("star");
			        		$(star).addClass("yellow-Star");			        		
			        	}
			        
			        	for(i=0; i < data.general; i++){
			        		var star = $(".generalStars").children()[i];
			        		$(star).removeClass("star");
			        		$(star).addClass("yellow-Star");
			        	}
		        	}
		        	
		        },
		        error: function(data){
		            	console.log("error");}
		    });
		}
		
		$(function(){
			getUserProfile();
			getPrProfileReview();
		});
		
	</script>			
</body>
</html>
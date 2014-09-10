<%@page import="Utlis.SessionUtils"%>
<%@page import="Utlis.Constants"%>
<%@page import="ClubberLogic.PR"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@  include file="patternTemplates/header.tpl"%>

<%
	String messageText = (String) request.getAttribute(Constants.MESSAGE_TEXT);
%>

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
		label {
		width: 95px;
		}
	
</style>

<div id="latest-events">
	<div class="container">
		<div class="event-inner">
			<div class="row">
				<div id="latest-event-content" class="col-sm-7 col-md-8">
				<div class="bg">

					<h2 id="clientTitle">פרטי משתמש</h2><br>
					<div class="user-details">
						<form class="user-details-form" id="userDetails" name="userDetails" method="post" action="UpdateUserDetails" enctype="multipart/form-data">
							<div class="pic-area">
								<input type="file" name="pic" id="pic" style="max-width:30%; max-height:30%;">
							</div>
							<div id="viewMode">
							  	<br/><br/><br/><br/>
							  	<label id="viewEmailLabel">כתובת מייל</label>
							  	<label id="viewEmail"></label>			
							  	<br>

							  	<label id="viewgenderLabel">מין</label>
							  	<label id="viewgender"></label>
							  	
								<br>
								<label id="viewbirthdateLabel">תאריך לידה</label>
								<label id="viewbirthdate"></label>
							<br>
							
								<label id="viewphonenumberLabel">טלפון</label>
								<label id="viewphonenumber"></label>
								<br><br>
					  			<button id="editUserDel" type="button" >ערוך</button>
					  			<br><br>
								<label id="messageText">
									<% if(messageText != null){ %>
										<%=messageText %>
									<%} %>		
								</label>
				  			</div>

							<br>
							<div id="editMode" style='display:none'>
								<br><br><br><br>
							<label id="firstnameLabel">שם פרטי</label> 
							  	<input type="text" name="firstName" id="firstName" required>
							<br>

							<label id="lastnameLabel">שם משפחה</label> 
							  	<input type="text" name="lastName" id="lastName" required>			
							<br>

							<label id="genderLabel">מין</label> 
							  	<input type="radio" name="gender" value="Male" checked>
								זכר
								<input type="radio" name="gender" value="Female">
								נקבה
							<br> 
							
							<label id="birthdateLabel">תאריך לידה</label>
								<input type = "datetime" name="birthdate" id="birthdate" size="17" required readonly>
	  						<br>
														
							<label id="phonenumberLabel">טלפון</label> 
								<input type="text" name="phoneNumber" id="phoneNumber" required>
							<br>
							
							<label id="passwordLabel">סיסמה</label> 
								<input type="password" name="Password" id="password" required>
							<br>

							<label id="verifyPasswordLabel">אימות סיסמה</label> 
								<input type="password" name="verifyPassword" id="verifyPassword" required>
							<br>
					  			<button id="updateUserDel" type="submit">שמור</button>
  							</div>							
						</form> 
					</div>
					<div class="user-rating">
						<label id="generalLabel">כללי</label> <br>
						<div class="generalStars">
							<div class="star"></div>
							<div class="star"></div>
							<div class="star"></div>
							<div class="star"></div>
							<div class="star"></div>
						</div>
						<br>
						<br> <label id="punctualityLabel">דיקנות</label> <br>
						<div class="punctualityStars">
							<div class="star"></div>
							<div class="star"></div>
							<div class="star"></div>
							<div class="star"></div>
							<div class="star"></div>
						</div>
						<br>
						<br> <label id="reliabilityLabel">אמינות</label><br>
						<div class="reliabilityStars">
							<div class="star"></div>
							<div class="star"></div>
							<div class="star"></div>
							<div class="star"></div>
							<div class="star"></div>
						</div>
						<br>
					</div><br><br>
					<div class="recomendedLines">
					</div>
				</div></div>
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
	        buttonImageOnly: true});	      	
	});

	// Set error messages  
	$.extend(jQuery.validator.messages, {
		required : "שדה חובה",
		equalTo : "סיסמאות אינן תואמות"
	});

	$("#userDetails").validate({
		rules : {
			verifyPassword : {
				equalTo : "#password"
			}
		}
	});

	$("#editUserDel").click(function() {
		$('#clientTitle').html("פרטי המשתמש");
		$("#viewMode").hide();
		$("#editMode").show();
		$('.pic-area').append('<input type="file" name="pic" id="pic" style="float:right;padding:0; margin:0;max-width:30%; max-height:30%;">');
		$(".user-rating").hide();
	});
	
	function getUserProfile() {
		$.ajax({
			url : "GetDBData",
			type : "post",
			dataType : 'json',
			data : {
				RequestType : "DBDataUserProfile"
			},
			success : function(data) {
				if (data == "UserNotLoggedOn") {
					window.location.href = "Login.jsp";
				}
				console.log(data);
				loadDataToView(data);
				$("#firstName").val(data.firstName);
				$("#lastName").val(data.lastName);
				$('input[name="gender"][value="' + data.gender + '"]').prop("checked", true);
				
				var date = new Date(data.birthDate);
				var month = date.getMonth() + 1;
				$("#birthdate").val(date.getDate() + "/" +month + "/" + date.getFullYear());
				
				$("#phoneNumber").val(data.phoneNumber);
				$("#password").val(data.password);

				if (!data.imageUrl) {
	        		$("#pic")[0].style.display = "none";
	        	}
	        	else {
	        		$('#pic').replaceWith('<img src="'+data.imageUrl+'" id="pic" style="max-width:100px; max-height:100px; float:right;margin-left:30px">');
	        	}
				
				if (data.isUserEditable) {
				$("#editUserDel").attr("disabled",false);
	        	}
	        	else {
	        		$("#editUserDel")[0].style.display = "none";
	        		$("#updateUserDel")[0].style.display = "none";
	        	}
			},
			error : function(data) {
				console.log("error");
			}
		});
	}

	function loadDataToView(data) {
		$('#clientTitle').html(data.firstName + " " + data.lastName);
    	$("#viewEmail").html(data.email);
    	        	
    	if (data.gender == "Female") {
    		$("#viewgender").html("נקבה");        		
    	}
    	else {
    		$("#viewgender").html("זכר");
    	}
    	
		var date = new Date(data.birthDate);
		var month = date.getMonth() + 1;
		$("#viewbirthdate").html(date.getDate() + "/" +month + "/" + date.getFullYear());
		$('#viewphonenumber').html(data.phoneNumber);
	}
	
	function getClientProfileReview() {
		$.ajax({
			url : "GetDBData",
			type : "post",
			dataType : 'json',
			data : {
				RequestType : "DBDataClientProfileReview"
			},
			success : function(data) {

				var i;
				if (data != null) {
					for (i = 0; i < data.punctuality; i++) {
						var star = $(".punctualityStars").children()[i];
						$(star).removeClass("star");
						$(star).addClass("yellow-Star");
					}

					for (i = 0; i < data.realiability; i++) {
						var star = $(".reliabilityStars").children()[i];
						$(star).removeClass("star");
						$(star).addClass("yellow-Star");
					}

					for (i = 0; i < data.general; i++) {
						var star = $(".generalStars").children()[i];
						$(star).removeClass("star");
						$(star).addClass("yellow-Star");
					}
				}

			},
			error : function(data) {
				console.log("error");
			}
		});
	}

	function getRecomendedUserLines() {
		$.ajax({
			url : "GetDBData",
			type : "post",
			dataType : 'json',
			data : {
				RequestType : "DBDataRecomendedLines"
			},
			success : function(data) {
				console.log(data);
				$.each(data, function(index, val) {
					
					$(".recomendedLines").append($('<p>' + val.line.m_LineName + ' - ' + val.business.m_Name + ' </p>'
							+'<p> גיל מינימלי: ' + val.line.minAge + '</p>'
							+'<p>' + val.business.m_StreetId.Name + ' ' +val.business.m_HouseNumber + ', ' + val.business.m_CityId.Name + '</p>'
							+'<p>' + val.line.description +'</p>'));
				});
			},
			error : function(data) {
				console.log("error");
			}
		});
	}

	$(function() {
		getUserProfile();
		getClientProfileReview();
		getRecomendedUserLines();
		
		//fancy box
		
	});
</script>

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

							<label id="firstnameLabel">תמונה</label> 
							<input type="file" name="pic" id="pic" disabled > 
							<br>
							
							<label id="firstnameLabel">שם פרטי</label> 
							<input type="text" name="firstName" id="firstName" required disabled > 
							<br>

							<label id="lastnameLabel">שם משפחה</label> 
							<input type="text" name="lastName" id="lastName" required disabled> 
							<br>

							<label id="genderLabel">מין</label> 
							<input type="radio" name="gender" value="Male" checked disabled> זכר 
							<input type="radio" name="gender" value="Female" disabled> נקבה
							<br> 
							
							<label id="birthdateLabel">תאריך לידה</label> 
							<input name="birthdate" id="birthdate" class="datepicker" disabled> 
							<br><br> 
							
							<label id="phonenumberLabel">טלפון</label> 
							<input type="text" name="phoneNumber" id="phoneNumber" required disabled > 
							<br>

							<label id="emailLabel">דוא"ל</label> 
							<input type="text" name="email" id="email" required disabled > 
							<br> 
							
							<label id="passwordLabel">סיסמה</label> 
							<input type="password" name="Password" id="password" required disabled> 
							<br>

							<label id="verifyPasswordLabel">אימות סיסמה</label> 
							<input type="password" name="verifyPassword" id="verifyPassword" required disabled> 
							<br>

							<button id="editUserDel" type="button">ערוך</button>
							<button id="updateUserDel" type="submit"  disabled >שמור</button>
						</form>
						<br> 
						
						<label id="messageText"> <%
					 	if (messageText != null) {
					 	%>
							<%=messageText%> 
						<%
					 	}
					 	%>
						</label>
					</div>
					<div class="recomendedLines">
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
						<br> <label id="reliabilityLabel">אמינות</label> description<br>
						<div class="reliabilityStars">
							<div class="star"></div>
							<div class="star"></div>
							<div class="star"></div>
							<div class="star"></div>
							<div class="star"></div>
						</div>
						<br>
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

	function formattedDate(date) {
	    var d = new Date(date || Date.now()),
	        month = '' + (d.getMonth() + 1),
	        day = '' + d.getDate(),
	        year = d.getFullYear();
	
	    if (month.length < 2) month = '0' + month;
	    if (day.length < 2) day = '0' + day;
	
	    return [day, month, year].join('/');
	}

	// Set error messages  
	$.extend(jQuery.validator.messages, {
		required : "שדה חובה",
		email : 'כתובת דוא"ל אינה חוקית',
		equalTo : "סיסמאות אינן תואמות",
		date : "תאריך לידה אינו חוקי"
	});

	$("#userDetails").validate({
		rules : {
			email : {
				email : true
			},

			verifyPassword : {
				equalTo : "#password"
			},

			birthdate : {
				date : true
			}
		}
	});

	$("#editUserDel").click(function() {
		
		$('input').attr('disabled', false);
		
		$('#firstName').attr("disabled", false);
		$('#lastName').attr("disabled", false);
		$('input[name="gender"]').attr("disabled", false);
		$('#birthdate').attr("disabled", false);
		$('#phoneNumber').attr("disabled", false);
		$('#email').attr("disabled", false);
		$('#password').attr("disabled", false);
		$('#verifyPassword').attr("disabled", false);
		$("#updateUserDel").attr("disabled", false);
		$('#pic').replaceWith('<input type="file" name="pic" id="pic">');
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
				console.log(data);
				$("#firstName").val(data.firstName);
				$("#lastName").val(data.lastName);
				$('input[name="gender"][value="' + data.gender + '"]').prop("checked", true);
				$("#birthdate").val(formattedDate(data.birthDate));
				$("#phoneNumber").val(data.phoneNumber);
				$("#email").val(data.email);
				$("#password").val(data.password);
				$('#pic').replaceWith('<img src="'+data.imageUrl+'" id="pic">');
			},
			error : function(data) {
				console.log("error");
			}
		});
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
	});
</script>


</body>
</html>
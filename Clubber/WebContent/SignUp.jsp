<%@page import="Utlis.Constants"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<% String messageText = (String)request.getAttribute(Constants.MESSAGE_TEXT); %>
<%@  include file="patternTemplates/header.tpl" %>
    <div id="latest-events">
        <div class="container">
            <div class="event-inner">                
                <div class="row">                       
                    <div id="latest-event-content" class="col-sm-7 col-md-8"> 
                        <div class="bg">
                            <h2 class="heading"> הרשמה <strong>לאתר</strong> </h2 >       
                                 <div class="signup-area">
  			<form class="create-account-form" id="createAccount" name="createAccount" method="post" action="SignUp">
	  			<div class="message-area">
					<label id=loginFailed>
					<% if(messageText != null){ %>
						<%=messageText %>
					<%} %>
					</label>   			
  				</div>
			  	<br>
			  	<div class="lable" id="whoAmILabel">מי אני?</div>
			  	<input type="radio" name="whoAmI" value="PR" checked>
				יחצן
				<input type="radio" name="whoAmI" value="Client">
				Clubber
				<br><br>
			  	
			  	<div class="lable" id="firstnameLabel">שם פרטי</div>
			  	<input type="text" name="firstName" id="firstName" required>
			  	<br>
			  	
			  	<div class="lable" id="lastnameLabel">שם משפחה</div>
			  	<input type="text" name="lastName" id="lastName" required>			
			  	<br>
			  	<br>
			  	<div class="lable" id="genderLabel">מין</div>
			  	<input type="radio" name="gender" value="Male" checked>
				זכר
				<input type="radio" name="gender" value="Female">
				נקבה
				<br><br>
				
				<div class="lable" id="birthdateLabel">תאריך לידה</div>
				<input type = "datetime" name="birthdate" id="birthdate" required readonly>
	  			<br>
	  							
				<div class="lable" id="phonenumberLabel">טלפון</div>
				<input type="text" name="phoneNumber" id="phoneNumber" required>
				<br>

				
				<div class="lable" id="emailLabel">דוא"ל</div>
				<input type="text" name="email" size="40" id="email" required>
	  			<br>

<!-- 				<div class="lable" id="passwordLabel">סיסמה</div> -->
<!-- 				<input type="password" name="password" id="password"> -->
<!-- 	  			<br> -->
				 
<!-- 				<div class="lable" id="verifyPasswordLabel">אימות סיסמה</div> -->
<!-- 				<input type="password" name="verifyPassword" id="verifyPassword" required> -->
<!-- 	  			<br><br> -->
	  			
	  			<div class="captcha-area">
			       <%
			         ReCaptcha c = ReCaptchaFactory.newReCaptcha("YOUR_PUBLIC_KEY", "YOUR_PRIVATE_KEY", false);
			         out.print(c.createRecaptchaHtml(null, null));
			       %>

	  			</div>
	  			
	  			<button type="submit" >הירשם</button>
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
	
		// date picker
		$(function() {
			var date = new Date();
			var currentYear = date.getFullYear();
			$('#birthdate').datepicker({
				yearRange: "-120:+0",
				minDate: new Date(currentYear - 120, 1, 1),
				maxDate: date,
				changeMonth: true,
		      	changeYear: true,
		      	showOn: "button",
		        buttonImage: "images/calendar.gif",
		        buttonImageOnly: true});	      	
		});
	
		
		// Set error messages  
		jQuery.extend(jQuery.validator.messages, {
		    required: "שדה חובה",
		    email: 'כתובת דוא"ל אינה חוקית',
		    equalTo: "סיסמאות אינן תואמות",
		    date: "תאריך לידה אינו חוקי"
		    
		});
		

		$( "#createAccount" ).validate({
			  rules: {
			    email: {
			      	email: true
			    },
		
			    
				verifyPassword: {
		      		equalTo: "#password"
			  	},
			    
			    birthdate: {
			    	date: true
			    }
			 }
			});		

		</script>			
    
</body>
</html>
<%@page import="Utlis.SessionUtils"%>
<%@page import="Utlis.Constants"%>
<%@page import="ClubberLogic.PR"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@  include file="patternTemplates/header.tpl"%>
<% String statusMessage = (String)request.getAttribute(Constants.STATUS_MESSAGE); %>

<div id="latest-events">
	<div class="container">
		<div class="event-inner">
			<div class="row">
				<div id="latest-event-content" class="col-sm-7 col-md-8">
				<div class="bg">

					<h2 id="clientTitle">פרטי משתמש</h2>

	<div class="password-retrival-area">
		<form class="password-retrival-form" id="passwordRetrival" name="passwordRetrival" method="post" action="PasswordRetrival">
			<label id="emailLabel">דוא"ל</label>
			<input type="text" name="email" id="email" required>
  			<br><br>
  			<button type="submit" >שלח</button>
		</form>
	</div>
	<div class="message-area">
		<label id="statusMessageLabel">
			<% if(statusMessage != null){ %>
				<%=statusMessage %>
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

// Set default messages  
jQuery.extend(jQuery.validator.messages, {
    required: "שדה חובה",
    email: 'כתובת דוא"ל אינה חוקית',
});	

// validate fields
$( "#passwordRetrival" ).validate({
	  rules: {
	    email: {
	      	email: true
	    }
	 }
	});		

</script>		    				

<%@ page contentType="text/html; charset=UTF-8" %>
<%@  include file="patternTemplates/header.tpl" %>

        
    
    
    <div class= 'site-header'>
    <div class= 'upper-header'>
    	<div class='logo'></div>
    	<div class='advertisements'></div>
    </div>
    <br>
    </div>
    <div class="container">
            <div class="event-inner">                
                <div class="row">   
    <div class='main-container bg '>  
       	<div class="offer-item-reviewed-container">
       		<div class='offer-item-reviewed-title'>
       			
       		</div>
       		<div class= "offer-item-reviewed-content">
       			<div title="לחץ כאן כדי לראות את פרטי הליין" class="offer-reviewed-item-line-image-container" onclick="alert('')" style="float:right">
       				<img src="/Clubber/images/line_Img.png" class="offer-item-line-image">
       			</div>
       			<div class="offer-reviewed-item-description" style="float:right">
		       		<div id="offer-description">
						<label class="offer-title-label">תיאור</label>
					</div>
					<div id="offered-line">
						<label class="offer-title-label">ליין מוצע</label>
					</div>
					<div id="offer-expiration-date">
						<label class="offer-title-label">ת.תפוגת ההצעה</label>
					</div>
					<div id="max-arrival-hour">
						<label class="offer-title-label">שעת הגעה מקסימלית</label>
					</div>
					<div id="offer-status">
						<label class="offer-title-label">סטטוס ההצעה</label>
					</div>
					<div id="offer-treats">
						<label class="offer-title-label"> סל פינוקים</label>
					</div>
				</div>
				<input class='btn-primary' type="button" id="accept-offer-button" name="accept-offer-button"  value="קבל הצעה זו" onClick= "approveBtnClicked()" style="float: left; margin-top: 300px;"/>
			</div>
		</div>
       <div class='all-messages-container'>
	       	<div class="old-messages"></div>
	       	<div class="new-message"></div> 
	       	 <input type="text" id='outgoing-message-text' class='outgoing-message outgoing-message-empty' onblur='setNewMessageTextOnFocusOut();' onfocus='setNewMessageTextOnFocusIn();' name="new-message-input" value="השב...">        
	    </div>
	    <div class='recomended-lines-container'></div>        
    </div></div></div></div>
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
<script src="Script/OfferReview.js"  type="text/javascript" charset="windows-1255" ></script>
    </body>
</html>

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
       	<div class='offer-item-reviewed-title'>
       	</div>
       	<br>
       	<br>
       	<div class="offer-item-reviewed-container">
       		<div class= "offer-item-reviewed-content">
       			<div title="לחץ כאן כדי לראות את פרטי הליין" class="offer-reviewed-item-line-image-container" onclick="alert('')" style="float:right">
       				<img src="/Clubber/images/line_Img.png" class="offer-item-line-image">
       			</div>
       			<div id="ViewOfferData" class="offer-reviewed-item-description">
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
				<div id='EditOfferData' class='offer-reviewed-item-description' style='display:none'>
					<label id="lineDescriptionLabel">תיאור</label> 
					<textarea rows="5" cols="20" type="text" name="description" id="description" required ></textarea>
					<br>
					
					<label id="linebusinessNameLable">ליין מוצע</label> 
					<input type="text" name="lineId" id="lineId" required hidden /> 
					<select name="lineName" id="lineName" required></select>
					<br>

					<label id="offerEndDateLabel">ת. תפוגת ההצעה</label> 
					<input type="text" id='endDate' name="endDate" readonly="true" required placeholder="תאריך האירוע"> 
					<br>
					
					<label id="offerMaxArrivalTime">שעת הגעה מקסימלית</label> 
					<input type="time" name="maxArrivalTime" id="maxArrivalTime" required >
					<br>
					
					<label id="offerStatusLabel">סטטוס ההצעה</label> 
					<label id="offerStatus"></label>
					<br>
					
					<div style="height:120px"> 
			         	<label id="treatsLabel">סל פינוקים</label>
					        <div class='new-auction-field-input combobox-auction-input' id= "treats" >
							</div> 
					</div>
					<input class='btn-primary' type="button" id="update-offer-button" onClick="saveBtnClicked()" name="update-offer-button" value="שמור"/>
				</div>
				<div class="auction-item-description">
		       		<div id="auction-event-date">
						<label class="offer-title-label">תאריך האירוע</label>
					</div>
					<div id="auction-event-type">
						<label class="offer-title-label">סוג האירוע</label>
					</div>
					<div id="auction-music-style">
						<label class="offer-title-label">סגנון המוזיקה</label>
					</div>
					<div id="auction-area">
						<label class="offer-title-label">אזור</label>
					</div>
					<div id="auction-guests-quantiny">
						<label class="offer-title-label">כמות האורחים</label>
					</div>
					<div id="auction-exceptions">
						<label class="offer-title-label">תיאור חריגים</label>
					</div>
					<div id="auction-business-type">
						<label class="offer-title-label">סוג בית עסק</label>
					</div>
					<div id="auction-certain-business">
						<label class="offer-title-label">מקום בילוי מסויים</label>
					</div>
					<div id="auction-sitts-type">
						<label class="offer-title-label">סוג מקומות ישיבה</label>
					</div>
					<div id="auction-description">
						<label class="offer-title-label">תיאור כללי</label>
					</div>
				</div>
				<input class='btn-primary' type="button" id="accept-offer-button" name="accept-offer-button"  value="קבל הצעה זו" onClick= "approveBtnClicked()"/>
				<input class='btn-primary' type="button" id="edit-offer-button" name="edit-offer-button"  value="ערוך"/>
			</div>
		</div>
       <div class='all-messages-container'>
	       	<div class="old-messages"></div>
	       	<div class="new-message"></div> 
	       	 <textarea id='outgoing-message-text' class='outgoing-message outgoing-message-empty' onblur='setNewMessageTextOnFocusOut();' onfocus='setNewMessageTextOnFocusIn();' name="new-message-input">השב...</textarea>        
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

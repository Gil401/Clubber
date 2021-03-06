<%@ page contentType="text/html; charset=UTF-8" %>
<%@  include file="patternTemplates/header.tpl" %>

<style>
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
		div.user-rating{
			float:right;
			display:block;
		}
	
</style>      
    
    
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
	       	<br>
	       	<br>
					<div class="reviewed-auction">
			       		<div id="auction-event-date">
							<label class="title-label">תאריך האירוע</label>
						</div>
						<div id="auction-event-type">
							<label class="title-label">סוג האירוע</label>
						</div>
						<div id="auction-music-style">
							<label class="title-label">סגנון המוזיקה</label>
						</div>
						<div id="auction-area">
							<label class="title-label">אזור</label>
						</div>
						<div id="auction-guests-quantiny">
							<label class="title-label">כמות האורחים</label>
						</div>
						<div id="auction-exceptions">
							<label class="title-label">תיאור חריגים</label>
						</div>
						<div id="auction-business-type">
							<label class="title-label">סוג בית עסק</label>
						</div>
						<div id="auction-certain-business">
							<label class="title-label">מקום בילוי מסויים</label>
						</div>
						<div id="auction-sitts-type">
							<label class="title-label">סוג מקומות ישיבה</label>
						</div>
						<div id="auction-description">
							<label class="title-label">תיאור כללי</label>
						</div>
						<div class='make-offer' id="make-offer-button" onClick= "makeOfferClicked()">תן הצעה</div>
					</div>
					
			</div>		
		</div>
       
	    <div class='recomended-lines-container'></div>        
    </div></div>
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
	<script src="Script/AuctionReview.js"  type="text/javascript" charset="windows-1255" ></script>
    <script src="Script/Utils.js"  type="text/javascript" charset="utf-8" ></script>
    </body>
</html>

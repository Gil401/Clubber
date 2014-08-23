<%@ page contentType="text/html; charset=UTF-8" %>
<%@  include file="patternTemplates/header.tpl" %>


<div id="latest-events">
        <div class="container">
            <div class="event-inner">                
                <div class="row">                       
                    <div id="latest-event-content" class="col-sm-7 col-md-8"> 
                        <div class="bg">
                           <div class='main-container'>  
                           <h2><B>תן הצעה!</B></h2>
    		<form id="new_offer_form">     
	        
	        <div  class="large-text-label page-description">בחלק זה אתה מוזמן לתת הצעה לדורש </div>  
	         <br /><br />
	         
	         סוף זמן לאישור הצעה: <input type="time" name="time" />
	         <div id="event-date-error" class="error-label-hidden">יש למלא שעת סגירה</div>
	         
	         <br/><br/>
	         
	         
	         <div class = "new-auction-field" class='new-auction-field' style="height:120px"> 
	         	<label class='new-auction-field-title'> ליינים רלוונטיים</label>
	        	<div class='new-auction-field-input combobox-auction-input' id= "Lines_to_offer" >
	        	</div>
			</div>
			
			<div class = "new-auction-field" class='new-auction-field' style="height:120px"> 
	         	<label class='new-auction-field-title'> פינוקים</label>
	        	<div class='new-auction-field-input combobox-auction-input' id= "treats_to_offer" >
	        	</div>
			</div>  
			
	         <br/>
	         	<div class = "lable" class='new-auction-field'> 
	         <label class='new-auction-field-title'> מושב מעשנים: </label>
	         <select class='new-auction-field-input' id= "is-flexible-date" name="is-flexible-date">
	         <option value="false">לא</option>
	         <option value="true">כן</option>
	         </select>  </div>
	         
	         <div class='new-auction-field'>
	         <label class='new-auction-field-title'> אשר כמות אורחים</label>
	          <input type="text" class='new-auction-field-input' id="guests-quantity" name="guests-quantity" >
	          <div id="guests-quantity-error" class="error-label-hidden">הזן כמות האורחים</div>
	         </div>
	         
	         <br /><br />
	         
	         <div class='new-auction-field' >
	         <label class='new-auction-field-title'> תיאור כללי *</label>
	         <textarea class='new-auction-field-input' id="general-description" name="general-description"  rows="4" cols="50" style= "width:500px;resize: none;"> </textarea>
	         <div id="general-description-error" class="error-label-hidden">הזן תיאור כללי על האירוע</div><br />
	         <br/> <br />
	          
	         </div>
	        
	        <br>
	        <div><input class='btn-primary' type="button" id="add-new-auction-button" name="add-new-auction-button"  value="הצע" onClick= "newAuctionClicked()"/></div>
       </form>
    </div>
                        </div>                                       
                    </div> 
                </div>                          
            </div>
        </div>
    </div><%@  include file="patternTemplates/footer.tpl" %>
 
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
    <script src="js/datepicker-he.js"></script>
    <script type="text/javascript" src="Script/NewOffer.js"></script>    
    
</body>
</html>
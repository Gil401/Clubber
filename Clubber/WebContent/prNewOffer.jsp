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
	         
	         <label class="new-offer-field-title">תוקף ההצעה</label>
	         <input type="text" id="closing-date" name="closing-date" readonly="true" required/>
	         <div id="event-date-error" class="error-label-hidden">יש למלא את התאריך בו ההצעה פגה</div>
	         <br/><br/>
	         
	         <label class="new-offer-field-title">שעת הגעה</label>
	         <input id ="max-arrival-time" type="time" name="max-arrival-time" />
	         <div id="event-time-error" class="error-label-hidden">יש למלא שעת הגעה</div>
	         <br/><br/>
	         
	         <div class = "new-offer-field" class='new-offer-field'> 
	         	<label class='new-offer-field-title'> ליינים רלוונטיים</label>
	        	<select name="Lines_to_offer" id="Lines_to_offer" required></select>
	        	</div>
			</div>
			
			<div class = "new-offer-field" class='new-offer-field'> 
	         	<label class='new-offer-field-title'> פינוקים</label>
	        	<div class='new-offer-field-input combobox-offer-input' id= "treats_to_offer" >
	        	</div>
			</div>  
	         <br/>
	         <div class='new-offer-field' >
	         <label class='new-offer-field-title'>* תיאור כללי</label>
	         <label class='new-offer-field-title' style="font-size:small;width:100%">כאן זה המקום לתאר את מקום הבילוי, את האווירה, סוגי מקומות הישיבה הקיימים והחוויה הכוללת שמוצעת לחוגג.</label>
	         <textarea class='new-offer-field-input' id="general-description" name="general-description"  rows="4" cols="50" style= "width:500px;resize: none;"> </textarea>
	         <div id="general-description-error" class="error-label-hidden">הזן תיאור כללי על האירוע</div><br />
	         <br/> <br />
	          
	         </div>
	        
	        <br>
	        <div><input class='btn-primary' type="button" id="add-new-offer-button" name="add-new-offer-button" value="הצע" onClick= "newOfferClicked()"/></div>
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
    <script src="Script/Utils.js"  type="text/javascript" charset="utf-8" ></script>
    <script type="text/javascript" src="Script/NewOffer.js"></script>    
    
</body>
</html>
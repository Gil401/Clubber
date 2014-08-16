<%@ page contentType="text/html; charset=UTF-8" %>
<%@  include file="patternTemplates/header.tpl" %>



<div id="latest-events">
        <div class="container">
            <div class="event-inner">                
                <div class="row">                       
                    <div id="latest-event-content" class="col-sm-7 col-md-8"> 
                        <div class="bg">
                           <div class='main-container'>  
                           <h2>הוספת מכרז  <B> חדש</B></h2>
    		<form id="new_auction_form"  >     
	        <div  class="large-text-label page-description">צוות קלאבר מזמין אותך להזין את פרטי האירוע שלך, ולקבל הצעות אטרקטיביות מהמקומות הכי חמים! </div>  
	         <br /><br />
	         <input type="text" class='datepicker' name="datepicker" placeholder="תאריך האירוע"><br/><br/>
	         <div class='new-auction-field'>
	         <label class='new-auction-field-title'>סוג האירוע</label>  
	         <select class='new-auction-field-input' id="event-type" name="event-type">
	         <option>פרטי</option>
	         <option value="other">אחר</option>
	         </select> &nbsp;&nbsp;&nbsp;<input type="text" id="other" style="display: none;" >  </div>  
	         
	         
	         <div class = "lable" class='new-auction-field' style="height:90px"> 
	         <label class='new-auction-field-title'> סגנון מוזיקה </label>
	        	<div class = "lable" class='new-auction-field-input' id= "musicStyle" >
	   			 <input type="checkbox" value="2">  ראפ
	   			 <input type="checkbox">  טכנו
	   			 <input type="checkbox">  רוסי כבד
	   			 <input type="checkbox">  רוסי מיליטנטי
	   			 
	   			 </div>
	   			 
			</div> 
			
			<div class='new-auction-field'> 
	         <label class='new-auction-field-title'> אזור </label>
	         <select class='new-auction-field-input' id= 'area' name= area>
	         </select>  </div>    
			    
	         <br/>
	         <div class = "lable" class='new-auction-field'> 
	         <label class='new-auction-field-title'> האם התאריך גמיש? </label>
	         <select class='new-auction-field-input' id= "is-flexible-date" name="is-flexible-date">
	         <option value="false">לא</option>
	         <option value="true">כן</option>
	         </select>  </div>
	         
	         <div class='new-auction-field'>
	         <label class='new-auction-field-title'> כמות אורחים *</label>
	        
	         <div class="note-label">ניתן לרשום כמות משוערת (לדוג': 10-12)</div>
	          <input type="text" class='new-auction-field-input' id="guests-quantity" name="guests-quantity" >
	         </div>
	         
	         <br /><br />
	         
	         <div class='new-auction-field' style= "height:105px">
	         <label class='new-auction-field-title'> תיאור חריגים</label>
	         <textarea class='new-auction-field-input' id="exceptions-description" name="exceptions-description" style= " resize: none;"> </textarea>
	         <br /><br />
	         <div class="note-label"><input type="checkbox"> אורחים בגילאים נמוכים במיוחד</div> 
	         </div>
	         <br>
	         
	         <div class='new-auction-field'>
	         <label class='new-auction-field-title'>גיל מינימלי * </label>
	         <input type="number" id="min-age" name="min-age"  min=0 class='new-auction-field-input'>
	         </div>
	         
	         <div class='new-auction-field' style="height:90px"> 
	         <label class='new-auction-field-title'> סוג בית עסק </label>
	        	 <select></select>
	        	 <div class='new-auction-field-input' id= "business-type"></div>
	        </div>
	         
	         <div class='new-auction-field'> 
	         <label class='new-auction-field-title'> מקום בילוי מסוים </label>
	         <select class='new-auction-field-input' id= 'certain-business' name= 'certain-business'>
	         </select>  </div>
	         
	         <div class='new-auction-field'> 
	         <label class='new-auction-field-title'> מעשנים </label>
	         <select class='new-auction-field-input' id= 'smoking' name= 'smoking'>
	         <option value="false">לא</option>
	         <option value="true">כן</option>
	         </select>  </div>
	         
	         <div class='new-auction-field' style="height:90px"> 
	         	<label class='new-auction-field-title'> סוג מקומות ישיבה </label>
	         	<select></select>
	         	<div class='new-auction-field-input' id= 'sitts-type'></div>
	         </div>
	         
	         <div class='new-auction-field' >
	         <label class='new-auction-field-title'> תיאור כללי *</label>
	         <div id="general-description-error" class="error-label-hidden">הזן תיאור כללי על האירוע</div><br />
	         <textarea class='new-auction-field-input' id="general-description" name="general-description"  rows="4" cols="50" style= "width:500px;resize: none;"> </textarea>
	         <br/> <br />
	          
	         </div>
	        
	        <br>
	        <div><input class='btn-primary' type="button" id="add-new-auction-button" name="add-new-auction-button"  value="הוסף" onClick= "newAuctionClicked()"/></div>
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
    <script type="text/javascript">
    $('#event-type').on("change", function(){
    	console.log($(this).val());
    	if($(this).val() == 'other')
    		$('#other').show();
    	else 
    		$('#other').hide();
     });
     </script>
    
</body>
</html>

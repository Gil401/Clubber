<%@ page contentType="text/html; charset=UTF-8" %>
<%@  include file="patternTemplates/header.tpl" %>

    <div class= 'site-header'>
    <div class= 'upper-header'>
    	<div class='advertisements'></div>
    </div>
    </div>
    <div id="latest-events">
        <div class="container">
            <div class="event-inner">                
                <div class="row">                       
                    <div id="latest-event-content" class="col-sm-7 col-md-8"> 
                       
    <div class='main-container'>  
    <div class= "page-description bg">
    	
    	<div style="width: 100%">
    		<div style="width: 50%; float:right">
    		<div style="width: 30%;float:right;margin:10px;">בחר ליין</div>
    		<select style="margin:10px;" id="myLines"><option></option></select><BR>
    		
    		<div style="width: 30%;float:right;margin:10px">בחר סטטוס</div>
    		<select style="margin:10px" id="status"><option value="0">הכל</option><option value="1">ממתין</option><option value="2">אושר</option><option value="2">סירוב</option></select>
    		</div>
    		
    		<div style="width: 50%; float: left"><input type="submit" value="חיפוש" onclick="loadOffers ($('#lineId').val(), $('.datepicker').val(), $('#status').val())"></div>
    	</div>
    	
    </div>
           
    </div>
    
    
    </div></div></div></div></div>
    
    
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
    <script type="text/javascript" src="Script/myOffers.js"></script>
    
    
    <script src="js/datepicker-he.js"></script>
  	<script>
	$(function() {

		$('#date').datepicker({
			dateFormat: "dd/mm/yy",
	      	showOn: "button",
	        buttonImage: "images/calendar.gif",
	        buttonImageOnly: true
		});	      	
	});

  	</script>
	
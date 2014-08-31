<%@  include file="patternTemplates/header.tpl" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
		<title>הוספת יחצן לליין שלי</title>
	</head>
	<body>
    <div id="latest-events">
        <div class="container">
            <div class="event-inner">                
                <div class="row">                       
                    <div id="latest-event-content" class="col-sm-7 col-md-8"> 
                        <div class="bg">
                            <h2 class="heading">בקשות הצטרפות לליינים שלך</h2 >
	
	                     	<div class="join-line-request-area">
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
	
	function convert12to24(timeStr)
	{
	    var meridian = timeStr.substr(timeStr.length-2).toLowerCase();;
	    var hours =  timeStr.substr(0, timeStr.indexOf(':'));
	    var minutes = timeStr.substring(timeStr.indexOf(':')+1, timeStr.indexOf(' '));
	    if (meridian=='pm')
	    {
	        if (hours!=12)
	        {
	            hours=hours*1+12;
	        }
	        else
	        {
	            hours = (minutes!='00') ? '0' : '24' ;
	        }
	    }

	    return hours+':'+minutes;
	}
	
	function convertTimeStampFormat(timestamp)
	{
		var d = new Date(timestamp || Date.now()),
	    year = d.getFullYear();
		var startIndex= timestamp.indexOf(year)+5;
		var time= timestamp.substr(startIndex);
		
		return (formattedDate(timestamp) + ' '+ convert12to24(time));
	}

	
	function showAllJoinLineRequest(){
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "DBDataGetJoinRequestData"},
	        success: function(joinRequestsList) {

	    		var joinRequests = $('.join-line-request-area');

	    		if (joinRequestsList.length > 0) {
	    			for (var i = 0; i < joinRequestsList.length; i++) {
	    				
	    				var message = '<div class="join-line-request">'
	    							  +'בקשת הצטרפות מיום '+ convertTimeStampFormat(joinRequestsList[i].createdOn)	    									 
	    							  +'<br>שלום, <br> '
	    							  + joinRequestsList[i].fromUserId +' מעוניין להצטרף לליין '+ joinRequestsList[i].lineId
	    							  +'<br><br><div><input type="button" onclick="sendEmail(true);" value="אשר"><input type="button" onclick="sendEmail(false); value="דחה"></div>'
	    							  +'</div>';
	    				
	    				joinRequests.append(message);
	    			}
	    		}
	    		else {
	    			joinRequests.append('<h1>אין בקשות הצטרפות</h1>');
	    		}
	    		
	        },
	        error: function(data){
	            	console.log("error");}
	    });		
	}
	
	function sendEmail(isJoin)
	{
		if(isJoin == true){
			
			JoinPRtoLine();
			
			
		}
		else if(isJoin == False){
			
		}
			
	}
	
	function JoinPRtoLine(){
		
	}
	
	$(function onLoad(){
		showAllJoinLineRequest();
	});
	
	</script>                            	
	</body>
</html>
var currAucId;
var currOffId;

var refreshRate = 1000; //miliseconds

function setNewMessageTextOnFocusOut()
{
	elementId= 'outgoing-message-text'; 
    
    if ($('#outgoing-message-text')[0].value == "")
    {
    	document.getElementById(elementId).setAttribute("class","outgoing-message outgoing-message-empty");
        document.getElementById(elementId).setAttribute("value","���...");
    }  
}

function setNewMessageTextOnFocusIn()
{
elementId= 'outgoing-message-text'; 
    
    if ($('#outgoing-message-text')[0].value == "���...")
    {
    	document.getElementById(elementId).setAttribute("class","outgoing-message outgoing-message-value");
        document.getElementById(elementId).setAttribute("value","");
    } 
}

function convert12to24(timeStr)
	{
	    var meridian = timeStr.substr(timeStr.length-2).toLowerCase();;
	    var hours =  timeStr.substr(0, timeStr.indexOf(':'));
	    var minutes = timeStr.substring(timeStr.indexOf(':')+1, timeStr.lastIndexOf(':'));
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

function formattedDate(date) {
    var d = new Date(date || Date.now()),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [day, month, year].join('/');
}


function convertTimeStampFormat(timestamp)
{
	var d = new Date(timestamp || Date.now()),
    year = d.getFullYear();
	var startIndex= timestamp.indexOf(year)+5;
	var time= timestamp.substr(startIndex);
	
	return (formattedDate(timestamp) + ' '+ convert12to24(time));
}


function approveBtnClicked()
{
	ajaxApproveCurrentOffer();
}

function loadOfferFromDB(data)
{
		console.log("adding current offer");
		var description;
			
		if (data.description== null)
		{
			description="";
		}
		else
		{
			description= data.description;
		}
		$(".offer-item-reviewed-title").append("<div class= 'offer-item-right-title' >"+data.prId.Name+"</div> <div class='offer-item-left-title' >" +formattedDate(data.submitDate) +"</div>");
		$("#offer-description").append("<label class='offer-value-label'>"+description+"</label>");
		$("#offered-line").append("<label class='offer-value-label'>"+data.lineId.Name+"</label>");/*should be a link to line*/
		$("#offer-expiration-date").append("<label class='offer-value-label'>"+formattedDate(data.expirationDate)+"</label>");
		$("#max-arrival-hour").append("<label class='offer-value-label'>"+convert12to24(data.maxArrivalHour)+"</label>");
		$("#offer-status").append("<label class='offer-value-label'>"+data.offerStatusId.Name+"</label>");
		
		for (var item in data.offerTreats) 
		{
			$("#offer-treats").append("<div class='offer-treat-div'><img src='/Clubber/images/Check_Image.png' class='offer-item-treat-image'><label class='offer-multi-value-label'>"+data.offerTreats[item].Name+"</label></div><br>");
		}
		
	}
	
	function loadAuctionFromDB(data){
		console.log("adding current auction");
		
		$("#auction-event-date").append("<label class='offer-value-label'>"+formattedDate(data.eventDate)+"</label>");
		$("#auction-event-type").append("<label class='offer-value-label'>"+data.eventType.Name+"</label>");
		$("#auction-area").append("<label class='offer-value-label'>"+data.area.Name+"</label>");
		$("#auction-guests-quantiny").append("<label class='offer-value-label'>"+data.guestesQuantiny+"</label>");
		$("#auction-exceptions").append("<label class='offer-value-label'>"+data.exceptionsDescription+"</label>");
		$("#auction-certain-business").append("<label class='offer-value-label'>"+(data.certainBusiness ? data.certainBusiness.Name : "")+"</label>");
		
		if (data.musicStyle.length > 0) {
			var musicStyles = ""; 
			for (var item in data.musicStyle) 
			{
				musicStyles += data.musicStyle[item].Name + " ,";
			}
			
			if (musicStyles.length > 1) {
				musicStyles = musicStyles.substring(0,musicStyles.length - 2);
			}
			
			$("#auction-music-style").append("<label class='offer-value-label'>"+musicStyles+"</label>");
		}
		
		if (data.businessType.length > 0) {
			var businessesType = ""; 
			for (var item in data.businessType) 
			{
				businessesType += data.businessType[item].Name + " ,";
			}
			
			if (businessesType.length > 0) {
				businessesType = businessesType.substring(0,businessesType.length - 2);
			}
			
			$("#auction-business-type").append("<label class='offer-value-label'>"+businessesType+"</label>");
		}
		
		if (data.sittsType.length > 0) {
			var sittsType = ""; 
			for (var item in data.sittsType) 
			{
				sittsType += data.sittsType[item].Name + " ,";
			}
			
			if (sittsType.length > 0) {
				sittsType = sittsType.substring(0,sittsType.length - 2);
			}
			
			$("#auction-sitts-type").append("<label class='offer-value-label'>"+sittsType+"</label>");
		}
		
		$("#auction-description").append("<label class='offer-value-label'>"+data.description+"</label>");
		
		
	}
	function ajaxOfferFormDBData() {
		var now= new Date();
		
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "GetDBData-OfferReview"},
	        success: function(data) {
	            if (data != null) {
	            	//if offer expired or already accepted or auction is closed -> remove accept btn:
	            	if (data.expirationDate< now || data.offerStatusId.id == 1 )
            		{
	            		$("#accept-offer-button").remove();
            		}
	            	
	            	console.log("GetDBData-OfferReview");  
		            currAucId= data.auctionId;
		            currOffId=data.id;
		            loadOfferFromDB(data);
		            
		            ajaxAuctionFromDBData();
	            }},
	        error: function(data){
	            	console.log("error- offer review");}	        
	    });
	}
	 
	function ajaxAuctionFromDBData() {
		$.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "GetDBData-AuctionReview", currAuctionID : currAucId},
	        success: function(data) {
	            if (data != null) {
	            	console.log("GetDBData-AuctionReview");
		            loadAuctionFromDB(data);
		            
		            setInterval(ajaxMessagesFormDBData, refreshRate);
	            }},
	        error: function(data){
	            	console.log("error- auction review");}	        
	    });
	}

	function ajaxApproveCurrentOffer() {
	    $.ajax({
	        url: "AcceptOffer",
	        type: "post",
	        dataType: 'json',
	        data:{AccptedOfferId:currOffId},
	        success: function(data) {
	        	console.log("offer accepted");  
	        },
	        error: function(data){
	            console.log("error- getting offer details");}	        
	    });
	}
	
	
	function ajaxSendMessage(){
		var description= $('#outgoing-message-text')[0].value;
		 $.ajax({
		        url: "GetDBData",
		        type: "post",
		        dataType: 'json',
		        data: {RequestType: "GetDBData-AddMessage",OutGoingMessageDescription:description},
		        success: function(data){
		        	console.log("message creation succedded");
		        	$('#outgoing-message-text')[0].value="";
		        },
		        error: function(data){
	            	console.log("error- adding message");}
		        });
	}
	
	function loadMessagesFromDB(data)
	{
		 $(".old-messages").empty(); 
		for (var i = 0; i < data.length; i++) {
			$(".old-messages").append("<hr style='width:50%' align='right'><div class='incoming-message'> <label>"+data[i].description+"</label></br> <label class='message-date'>"+convertTimeStampFormat(data[i].createdOn)+"</label></div>");
		}
	}
	
	function ajaxMessagesFormDBData() {
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "GetDBData-Messages"},
	        success: function(data) {
	            if (data != null) {
	                console.log("GetDBData-Messages");  
	                loadMessagesFromDB(data);
	            }},
	        error: function(data){
	            	console.log("error- Messages");}
	            
	        
	    });
	}

	$(function() {
		ajaxOfferFormDBData();
		$('#outgoing-message-text').keypress( function( e ) {
			  if( e.keyCode == 13 ) { ajaxSendMessage(); }
			} );
	});
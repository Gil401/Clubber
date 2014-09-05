var currAucId;
var currOffId;
var msgIntervalId;

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

function initiateFormBtns(){
	$("#edit-offer-button").click(function() {
		
		$('#EditOfferData').show();
		$('#ViewOfferData').hide();
		$('#accept-offer-button').hide();
		$('#edit-offer-button').hide();
		ajaxOfferFormDBData(loadDataToInputs);	
	});
	
	$("#update-offer-button").click(function() {
		$('#EditOfferData').hide();
		$('#ViewOfferData').show();
		$('#accept-offer-button').show();
		$('#edit-offer-button').show();
	});
}

function approveBtnClicked()
{
	ajaxApproveCurrentOffer();
}

function saveBtnClicked()
{
	ajaxOfferDtailesUpdate();
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

		var submitDate = new Date(data.submitDate);
		var expirationDate = new Date(data.expirationDate);
		var submitMonth = submitDate.getMonth() + 1;
		var expirationMonth = expirationDate.getMonth() + 1;
		
		$(".offer-item-reviewed-title").html("");
		$(".offer-item-reviewed-title").append("<div class= 'offer-item-right-title' onClick='moveToPrProfile("+data.prId.id+");' >"+data.prId.Name+"</div> <div class='offer-item-left-title' >" +submitDate.getDate() +"/"+ submitMonth +"/" + submitDate.getFullYear() +"</div>");
		
		$("#offer-description").html($("#offer-description").find(".offer-title-label"));
		$("#offer-description").append("<label class='offer-value-label'>"+description+"</label>");
		
		$("#offered-line").html($("#offered-line").find(".offer-title-label"));
		$("#offered-line").append("<label class='offer-value-label'>"+data.lineId.Name+"</label>");/*should be a link to line*/
		
		$("#offer-expiration-date").html($("#offer-expiration-date").find(".offer-title-label"));
		$("#offer-expiration-date").append("<label class='offer-value-label'>"+ expirationDate.getDate() +"/" + expirationMonth +"/"+ expirationDate.getFullYear() +"</label>");
		
		$("#max-arrival-hour").html($("#max-arrival-hour").find(".offer-title-label"));
		$("#max-arrival-hour").append("<label class='offer-value-label'>"+convertLongToTimeString(data.maxArrivalHourAsLong)+"</label>");
		
		$("#offer-status").html($("#offer-status").find(".offer-title-label"));
		$("#offer-status").append("<label class='offer-value-label'>"+data.offerStatusId.Name+"</label>");
		
		$("#offer-treats").html($("#offer-treats").find(".offer-title-label"));
		for (var item in data.offerTreats) 
		{
			$("#offer-treats").append("<div class='offer-treat-div'><img src='/Clubber/images/Check_Image.png' class='offer-item-treat-image'><label class='offer-multi-value-label'>"+data.offerTreats[item].Name+"</label></div><br>");
		}
	}

	function moveToPrProfile(prId){
		
		 $.ajax({
		        url: "PrProfile?",
		        type: "post",
		        dataType: 'json',
		        data:{RequestType: "visitPrProfile", PrId:"prId"},
		        success: successCallbck,
		        error: function(data){
		            	console.log("error- offer review");}	        
		    });
		window.location.href = 'PrProfile.jsp';
		
	}

	function loadAuctionFromDB(data){
		console.log("adding current auction");
		var eventDate = new Date(data.eventDate);
		var month = eventDate.getMonth() + 1;
		
		$("#auction-event-date").append("<label class='offer-value-label'>"+eventDate.getDate() +"/"+ month +"/"+eventDate.getFullYear()+"</label>");
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
	function ajaxOfferFormDBData(successCallbck) {	
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "GetDBData-OfferReview"},
	        success: successCallbck,
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
		            
		            msgIntervalId= setInterval(ajaxMessagesFormDBData, refreshRate);
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
	
	
	
	function loadTreatsAndLines(treatsChecked, selectedLine) {
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "GetDBData-NewOffer"},
	        success: function(data) {
	            if (data != null) {
	            	clearInterval(msgIntervalId);
	            	var treatsDiv = $("#treats");
	            	
	            	//delete former data 
	            	treatsDiv.html("");
	            	
	            	for(var i=0; i < data.treats.length; i++){
	            		
	            		 $('<input id="treat'+data.treats[i].id+'" type="checkbox" name="treats" value=' + data.treats[i].id+ '/>' +
	                     		'<label style="padding-right:10px; font-weight:normal;" for="'+data.treats[i].id+'">' + data.treats[i].Name + '</label></br>').appendTo($(treatsDiv)) ;
	            	}
	            	
	            	for (var item in treatsChecked) 
	    			{
	    				$('#treat'+ treatsChecked[item].id).attr("checked", true);
	    			}
	            	
            		var linesDiv = $("#lineName");
	            	
	            	//delete former data 
            		linesDiv.html("");
	            	
            		for(var i=0; i < data.lines.length; i++){
	            		
            			var element = '<option value="'+data.lines[i].id + '">' + data.lines[i].Name + '</option>';
	            		linesDiv.append($(element));
	            	}
            		
            		$("#lineName").val(selectedLine.id);
            		msgIntervalId= setInterval(ajaxMessagesFormDBData, refreshRate);
	            }},
	        error: function(data){
	            	console.log("error");}
	    });
	}
	
	$(function() {
		
		var successCallback = function(data) {
            if (data != null) {
        		var now= new Date();
        		
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
        }};
            
		ajaxOfferFormDBData(successCallback);
		$('#outgoing-message-text').keypress( function( e ) {
			  if( e.keyCode == 13 ) { ajaxSendMessage(); }
			} );
		initiateFormBtns();
	});
	
	function loadDataToInputs(data) {
		$("#description").val(data.description);
		$("#offerStatus").html(data.offerStatusId.Name);
		$("#maxArrivalTime").val(convertLongToTimeString(data.maxArrivalHourAsLong));
		$('#endDate').datepicker({});
		$('#endDate').datepicker("setDate", data.expirationDate);
		loadTreatsAndLines(data.offerTreats, data.lineId);
		
	}
	
	function replaceAll(find, replace, str) 
	{
		  return str.replace(new RegExp(find, 'g'), replace);
	}

	
	function ajaxOfferDtailesUpdate()
	{	
		var treats= $('#treats').find('input').serialize();
		var final= replaceAll("treats=","",treats);
		var final2= replaceAll("%2F","",final); 
		var offerId=currOffId;
		
		var description= $('#description')[0].value;
		var maxArrivalTime=  convertStringToLongTime($('#maxArrivalTime')[0].value);
		var endDate= $('#endDate')[0].value;
		var lineName=$("#lineName")[0].value;
		
		clearInterval(msgIntervalId);
		
	    $.ajax({
	        url: "UpdateOfferDetails",
	        type: "post",
	        dataType: 'json',
	        data: {Id:offerId, Treats: final2, LineName: lineName, 
	        	EndDate: endDate, Description: description, MaxArrivalTimeAsLong: maxArrivalTime},
	        success: function(data){
	        	console.log("offer update succedded");
	        	
	        	var successCallback = function(data) {
	                if (data != null) {	                	
	                	console.log("GetDBData-OfferReview");
	    	            loadOfferFromDB(data);
	            }};
	                
	    		ajaxOfferFormDBData(successCallback);
	    		
	    		msgIntervalId= setInterval(ajaxMessagesFormDBData, refreshRate);
	     },
	        error: function(data){
	            	console.log("error");}
	    });
	}

var currAucId;
var currOffId;
var msgIntervalId;
var currOffPrNAME;
var createdById;
var currLoggedUser;
var currPrId;
var currClientId;
var whoAmI;

var refreshRate = 2000; //miliseconds

function setNewMessageTextOnFocusOut()
{
	var element= $('#outgoing-message-text'); 
    
    if (element.val() == "")
    {
    	element.attr("class","outgoing-message outgoing-message-empty");
    	element.val("השב...");
    }  
}

function setNewMessageTextOnFocusIn()
{
	var element= $('#outgoing-message-text'); 
    
    if (element.val() == "השב...")
    {
    	element.attr("class","outgoing-message outgoing-message-value");
    	element.val("");
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
		
		currOffPrNAME= data.prId.Name;
		currPrId= data.prId.id;
		
		/*$(".offer-item-reviewed-title").html("");
		$(".offer-item-reviewed-title").append("<div class= 'offer-item-right-title' onClick='moveToPrProfile("+data.prId.id+");' >"+data.prId.Name+"</div> <div class='offer-item-left-title' >" +submitDate.getDate() +"/"+ submitMonth +"/" + submitDate.getFullYear() +"</div>");
		*/
		$("#offer-description").html($("#offer-description").find(".offer-title-label"));
		$("#offer-description").append("<label class='offer-value-label'>"+description+"</label>");
		
		$("#offered-line").html($("#offered-line").find(".offer-title-label"));
		$("#offered-line").append("<label class='offer-value-label' style='cursor: pointer; color:cornflowerblue' onClick='openDetails("+data.lineId.id+");'>"+data.lineId.Name+"</label>");/*should be a link to line*/
		
		$("#offered-line-business").html($("#offered-line-business").find(".offer-title-label"));
		$("#offered-line-business").append("<label class='offer-value-label'>"+data.lineBusinessId.Name+"</label>");
		
		$("#offer-expiration-date").html($("#offer-expiration-date").find(".offer-title-label"));
		$("#offer-expiration-date").append("<label class='offer-value-label'>"+ expirationDate.getDate() +"/" + expirationMonth +"/"+ expirationDate.getFullYear() +"</label>");
		
		$("#max-arrival-hour").html($("#max-arrival-hour").find(".offer-title-label"));
		$("#max-arrival-hour").append("<label class='offer-value-label'>"+convertLongToTimeString(data.maxArrivalHourAsLong)+"</label>");
		
		$("#offer-status").html($("#offer-status").find(".offer-title-label"));
		$("#offer-status").append("<label class='offer-value-label'>"+data.offerStatusId.Name+"</label>");
		
		$("#offer-treats").html($("#offer-treats").find(".offer-title-label"));
		for (var item in data.offerTreats) 
		{
			$("#offer-treats").append("<div class='offer-treat-div'><img src='/Clubber/images/Check_Image.png' class='offer-item-treat-image'><label class='offer-multi-value-label'>"+data.offerTreats[item].Name+"</label></div>");
		}
		if(whoAmI == "Client")
		{
			$("#edit-offer-button").hide();
		}
		else
		{
			$("#accept-offer-button").hide();
			$("#expose-user-details-button").hide();
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
		
		currClientId= data.createdBy.id
		createdById=data.createdBy.id;
		
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
		            ajaxGetUserDetails(createdById,data.userDetailsExposeInt);
		            msgIntervalId= setInterval(ajaxMessagesFormDBData, refreshRate);
	            }},
	        error: function(data){
	            	console.log("error- auction review");}	        
	    });
	}
	function exposedChanged(list) {
		$('#exposedDetails').val(list.value);
	}
	function ajaxApproveCurrentOffer() {
		jConfirm(' הינך מאשר את ההצעה הנ"ל, ובכך חושף את פרטי ההתקשרות שלך בפני היחצן <br> בחר את פרטי ההקשרות שברצונך לחשוף: <br><br>  <select name="userDetailsExpose" id="userDetailsExpose" required onchange="exposedChanged(this)"><option value="1">אימייל</option><option value="2">טלפון</option><option value="3">אימייל וטלפון</option></select>', 'קבלת הצעה ', 
				function(res) {
					if (res) {
						var userExposeCode = $('#exposedDetails').val();
						$('#exposedDetails').val("1");
						clearInterval(msgIntervalId);
						$.ajax({
						        url: "AuctionManagementActions",
						        type: "post",
						        dataType: 'json',
						        data:{RequestType: "AuctionManagementOfferAccepted", OfferId:currOffId, DisplayCode: userExposeCode, AcceptedAuctionID : currAucId },
						        success: function(data) {
						                console.log("Offer accepted"); 
						                location.reload();
						            },
						        error: function(data){
						            	console.log("error");} 
						    });
					}
			});
	}
	
	function ajaxExposeeCurrentUserDetails() {
		jConfirm(' בחר את פרטי ההקשרות שברצונך לחשוף: <br><br>  <select name="userDetailsExpose" id="userDetailsExpose" required onchange="exposedChanged(this)"><option value="1">אימייל</option><option value="2">טלפון</option><option value="3">אימייל וטלפון</option></select>', 'קבלת הצעה ', 
				function(res) {
					if (res) {
						var userExposeCode = $('#exposedDetails').val();
						$('#exposedDetails').val("1");
						clearInterval(msgIntervalId);
						$.ajax({
						        url: "AuctionManagementActions",
						        type: "post",
						        dataType: 'json',
						        data:{RequestType: "AuctionManagementExposeUserDetails", OfferId:currOffId, DisplayCode: userExposeCode, AcceptedAuctionID : currAucId },
						        success: function(data) {
						                console.log("Offer accepted"); 
						                location.reload();
						            },
						        error: function(data){
						            	console.log("error");} 
						    });
					}
			});
	}
	function addClientDetailsToScreen(detailsCode, userDetails)
	{
		if (detailsCode != DISPLAY_NONE)
		{
			$('#user-contact-details').append('<div class="user-contact-details"><label class="offer-title-label" style="float:right;">'+userDetails.firstName+' ' +userDetails.lastName+'<label></div>');
			
			if (detailsCode == DISPLAY_EMAIL)
			{
				$('#user-contact-details').append('<div id="auction-user-email"><a href="mailto:'+userDetails.email+'"><img  style="float:right; height:21px; width:21px; margin-left:10px;" src="/Clubber/images/EmailIcon.png" ></div>');
				$('#auction-user-email').append("<label class='offer-value-label'>"+userDetails.email+"</label></a>");
			}
			else if(detailsCode == DISPLAY_PHONE_NUMBER)
			{
				$('#user-contact-details').append('<div id="auction-user-phone"><img src="/Clubber/images/phone_icon.png" style="float:right; height:19px; width:19px; margin-left:10px;"></div>');
				$('#auction-user-phone').append("<label class='offer-value-label'>"+userDetails.phoneNumber+"</label>");
			}
			else if(detailsCode == DISPLAY_EMAIL_AND_PHONE)
			{
				$('#user-contact-details').append('<div id="auction-user-email"><a href="mailto:'+userDetails.email+'"><img  style="float:right; height:21px; width:21px; margin-left:10px;" src="/Clubber/images/EmailIcon.png" ></div>');
				$('#auction-user-email').append("<label class='offer-value-label'>"+userDetails.email+"</label></a>");
				
				$('#user-contact-details').append('<div id="auction-user-phone"><img src="/Clubber/images/phone_icon.png" style="height:19px; width:19px; margin-left:10px;"" ></div>');
				$('#auction-user-phone').append("<label class='offer-value-label'>"+userDetails.phoneNumber+"</label>");
			}
		}
		
		
	}
	
	function ajaxGetUserDetails(userId, displayCode)
	{
		$.ajax({
	        url: "SessionActions",
	        type: "post",
	        dataType: 'json',
	        data:{UserId: userId, RequestType: "SessionActions-SetUserInSession"},
	        success: function(data) {
	        	retriveUserData(displayCode,userId);
	        	
	        },
	        error: function(data){
	        	console.log("error");
	    	}
	    });
		
	}
	
		
	function retriveUserData(displayCode,userId)
	{
		$.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "DBDataUserDataRetrive", UserToDisplayId: userId},
	        success: function(data) {
	            if (data != null) { 
	            	addClientDetailsToScreen(displayCode, data);
	            }
            },
	        error: function(data){
	            	console.log("error- user details");}
	        
	    });
	}
	
	function ajaxSendMessage(){
		var description= $('#outgoing-message-text')[0].value;
		var fromID;
		var toID;
		
		if (whoAmI == "Client")
		{
			toID=currPrId;
			fromID=currClientId;
		}
		else
		{
			toID=currClientId;
			fromID=currPrId;
		}
		
		clearInterval(msgIntervalId);
		 $.ajax({
		        url: "GetDBData",
		        type: "post",
		        dataType: 'json',
		        data: {RequestType: "GetDBData-AddMessage",OutGoingMessageDescription:description,FromID:fromID, ToID: toID},
		        success: function(data){
		        	console.log("message creation succedded");
		        	$('#outgoing-message-text')[0].value="";
		        	msgIntervalId= setInterval(ajaxMessagesFormDBData, refreshRate);
		        },
		        error: function(data){
	            	console.log("error- adding message");}
		        });
	}
	
	function loadMessagesFromDB(data)
	{
		 $(".old-messages").empty(); 
		for (var i = 0; i < data.length; i++) {
			
			var classForMsg;
			if (currLoggedUser == data[i].fromUserId)
			{
				classForMsg="msgFrom";
			}
			else
			{
				classForMsg="msgTo";
			}
				
			$(".old-messages").append("<hr style='width:50%' align='right'><div class='incoming-message'> <label>"+data[i].description+"</label></br> <label class='message-from'>"+data[i].fromUserId.Name+'     ' +convertTimeStampFormat(data[i].createdOn)+'    ' +"</label></div>");
		}
	}
	
	function ajaxMessagesFormDBData() {
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "GetDBData-Messages", PrId:currPrId},
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
		getCurrentUserType();
		getCurrentLoggedOnUser();
		var successCallback = function(data) {
            if (data != null) {
        		var now= new Date();
        		
            	//if offer expired or already accepted or auction is closed -> remove accept btn:
            	if (/*data.expirationDate< now ||*/ data.offerStatusId.id != PENDING_OFFER_STATUS_ID )
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

	function getCurrentLoggedOnUser() {
		$.ajax({
	        url: "SessionActions",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "SessionActions-GetLoggedOnUserId"},
	        success: function(data) {
	            console.log("SessionActions-GetLoggedOnUserId"); 
	            currLoggedUser=data;
	        },
	        error: function(data){
	        	console.log("error");
	    	}
	    });
	}
	
	function getCurrentUserType() {
		$.ajax({
	        url: "SessionActions",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "SessionActions-GetWhoAmI"},
	        success: function(data) {
	            console.log("SessionActions-GetWhoAmI"); 
	            whoAmI=data;
	        },
	        error: function(data){
	        	console.log("error");
	    	}
	    });
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

function loadOffersFromDB(data, areaName)
	{
		var description;
		console.log("adding offers to "+ areaName);
		$(areaName).html("");
		for (var item in data) {
			if (data[item].description== null)
			{
				description="";
			}
			else
			{
				description= data[item].description;
			}
			
			var date = new Date(data[item].submitDate);
			var month = date.getMonth() + 1;
			var line_img= "/Clubber/images/Line_Default.jpg";
			
			if ((data[item].linePhotoURL != "" ) && (data[item].linePhotoURL != null ))
			{
				line_img=data[item].linePhotoURL;
			}
			
			$('<div id=' +data[item].id+' class="offer-item-container bg"><div  class="offer-item-line-image-container" id="'+data[item].lineId.id+'" onclick="openDetails('+data[item].lineId.id+');"><img src="'+line_img+'" class="offer-item-line-image" style="float: right; max-width: 100px; max-height:100px; margin-left:25px;margin-top: 8px;cursor: pointer;"></div>'
					+ ' <div class="offer-item-title"> <div class= "offer-item-right-title" style="float: right;cursor: pointer;text-decoration: underline;color:cornflowerblue" onclick="loadPrProfile(' + data[item].prId.id+ ')" >'+data[item].prId.Name+" "+' </div> <div class="offer-item-left-title" style="float: left">' + date.getDate() +"/" + month +"/" + date.getFullYear() +'</div> </div>'
					+ '</br><div class="offer-item-content" >'
					+ '<div class="offer-item-description">'+description+'</div>'
					+'<br/><div cless="0ffer-item-status">סטטוס: '+data[item].offerStatusId.Name+'</div>'
					+'</div><br /><br /><div style="float: left; "><button id= "NotRelevantOffer'+data[item].id+'" style="background-color: red; border-radius: 20px; font-size:13px;" onClick="notRelevantOffer('+ data[item].id +');">סמן כלא רלוונטי</button><button onclick="offerClicked(' + data[item].id + ')" style="border-radius: 20px; font-size:13px;">פרטים נוספים</button></div>'
					+'</div>').appendTo($(areaName)) ; 
			
			if (data[item].offerStatusId.id ==  NOT_RELEVANT_OFFER_STATUS_ID)
			{
				 $('#NotRelevantOffer'+data[item].id).hide();
			}
		}
	}
	
	function loadPrProfile(prId) {
		$.ajax({
	        url: "SessionActions",
	        type: "post",
	        dataType: 'json',
	        data:{UserId: prId, RequestType: "SessionActions-SetUserInSession"},
	        success: function(data) {
	            console.log("SessionActions-SetUserInSession");  
	            window.location.href = "PrProfile.jsp";
	        },
	        error: function(data){
	        	console.log("error");
	    	}
	    });
	}

	function notRelevantOffer(offerId)
	{
		jConfirm('האם הינך בטוח שההצעה לא רלוונטית?', 'שינוי סטטוס', 
				function(res) {
					if (res) {
						 $.ajax({
						        url: "AuctionManagementActions",
						        type: "post",
						        dataType: 'json',
						        data:{RequestType: "AuctionManagementOfferNotRelevant", OfferId:offerId},
						        success: function(data) {
						                console.log("Offer not relevant"); 
						                ajaxOffersFormDBData();
						            },
						        error: function(data){
						            	console.log("error");} 
						    });
					}
			});
	}
	
	function notRelevantAuction(auctionId)
	{
		jConfirm('האם הינך בטוח שהאירוע לא רלוונטי?', 'שינוי סטטוס', 
				function(res) {
					if (res) {
						$.ajax({
					        url: "AuctionManagementActions",
					        type: "post",
					        dataType: 'json',
					        data:{RequestType: "AuctionManagementAuctionNotRelevant", AuctionId:auctionId},
					        success: function(data) {
					                console.log("Aution not relevant");  
					                ajaxOffersFormDBData();
					                $('#NotRelevantAuction').hide();
					                $('#ActivateAuction').show();
					                
					            },
					        error: function(data){
					            	console.log("error");}
					            
					        
					    });
					}
			});
	}
	
	function activateAuction(auctionId)
	{
		jConfirm('האם הינך בטוח שברצונך לפתוח מחדש את האירוע?', 'שינוי סטטוס', 
				function(res) {
					if (res) {
						$.ajax({
					        url: "AuctionManagementActions",
					        type: "post",
					        dataType: 'json',
					        data:{RequestType: "AuctionManagementActivateAuction", AuctionId:auctionId},
					        success: function(data) {
					                console.log("Auction activated");  
					                ajaxOffersFormDBData();
					                $('#NotRelevantAuction').show();
					                $('#ActivateAuction').hide();          
					            },
					        error: function(data){
					            	console.log("error");}
					            
					        
					    });
					}
			});
	} 
	
	function loadAuctionFromDB(data, areaName)
	{
		var description;
		console.log("adding current auction to "+ areaName);
		$(areaName).html("");
		if (data.description== null)
		{
			description="";
		}
		else
		{
			description= data.description;
		}

		var exceptions = 'אין';
		
		if (data.exceptionsDescription) {
			exceptions = data.exceptionsDescription;
		}
		
		var date = new Date(data.eventDate);
		var month = date.getMonth() + 1;
		
		var musicStyles = "לא נבחר";
		if (data.musicStyle.length > 0) {
			musicStyles = "";
			for (var item in data.musicStyle) 
			{
				musicStyles += data.musicStyle[item].Name + " ,";
			}
			
			if (musicStyles.length > 1) {
				musicStyles = musicStyles.substring(0,musicStyles.length - 2);
			}
		}

		$(' <div id=' +data.id+' class="my-auction-container" style="padding-top: 0; border-radius: 25px; background: #CC3333; padding:10px; margin-top: 10px;"> <div class="my-auction-title title-container">'+data.eventType.Name+ ' ב'+ data.area.Name +' <div style="float: left; margin-left: 15px; font-size:20px;"> '+date.getDate() +"/" + month +"/" + date.getFullYear() +' </div></div>'
				+ '<div class="my-auction-description auction-description-field-container">'+description+'</div><br/>'
				+ '<div class="my-auction-description">'+'כמות מוזמנים: '+data.guestesQuantiny+'</div>'
				+ '<div class="my-auction-description">'+'סגנון מוזיקה: ' +musicStyles+'</div>'
				+ '<div class="my-auction-description">'+' תיאור מוזמנים חריגים: ' +exceptions+'</div>'
				+ '<br/><dic class="my-auction-description" style="font-weight:bold;" >'+'סטטוס:'+data.auctionStatus.Name
				+'<div style="float: left; "><button id= "NotRelevantAuction" style="background-color: gray; border-radius: 20px; font-size:13px;" onClick="notRelevantAuction('+ data.id +');">סמן כלא רלוונטי</button><button id="ActivateAuction" style="background-color: gray; border-radius: 20px; font-size:13px;" onClick="activateAuction('+ data.id +');">הפוך לפעיל</button></div>'+
				+'</div></div>').appendTo($(areaName)) ;
	}

	
	function offerClicked(offerID)
	{
		  $.ajax({
		        url: "AuctionOfferItemClicked",
		        type: "post",
		        dataType: 'json',
		        data:{ClickedItemType: "OfferItemClicked", ItemID:offerID},
		        success: function(data) {
		        	 console.log("redirect to offer review page");
		             window.location.href = 'OfferReview.jsp';
		            },
		        error: function(data){
		            	console.log("error");}
		            
		        
		    });
	}
	
	
	
	function ajaxOffersFormDBData() {
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "GetDBData-AuctionManagement"},
	        success: function(data) {
	            if (data != null) {
	                console.log("GetDBData-AuctionManagement");  
	                loadOffersFromDB(data.offers, '.all-Offers-container' );
	                loadAuctionFromDB(data.currentAuciton, '.current-auction-container' );
	                if (data.currentAuciton.auctionStatus.id == NOT_RELEVANT_AUCTION_STATUS_ID || data.currentAuciton.auctionStatus.id == INACTIVE_AUCTION_STATUS_ID){
	                	 $('#NotRelevantAuction').hide();
			             $('#ActivateAuction').show();
	                }
	                else
                	{
	                	 $('#NotRelevantAuction').show();
			             $('#ActivateAuction').hide();
                	}
	                
	            }},
	        error: function(data){
	            	console.log("error");}
	            
	        
	    });
	}

	$(function() {
		ajaxOffersFormDBData();
	});
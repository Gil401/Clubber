function formattedDate(date) {
    var d = new Date(date || Date.now()),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [day, month, year].join('/');
}	

function loadOffersFromDB(data, areaName)
	{
		var description;
		console.log("adding offers to "+ areaName);
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
			$('<div id=' +data[item].id+' class="offer-item-container bg"><div  class="offer-item-line-image-container" id="'+data[item].lineId.id+'" onclick="alert(' + data[item].lineId.id + ')"><img src="/Clubber/images/line_Img.png" class="offer-item-line-image" style="float: right; max-width: 100px; max-height:100px;"></div>'
					+ ' <div class="offer-item-title"> <div class= "offer-item-right-title" style="float: right" >'+data[item].prId.Name+" "+' </div> <div class="offer-item-left-title" style="float: left">' + date.getDate() +"/" + month +"/" + date.getFullYear() +'</div> </div>'
					+ '</br><div class="offer-item-content" >'
					+ '<div class="offer-item-description">'+description+'</div>'
					+'<br/><div cless="0ffer-item-status">'+data[item].offerStatusId.Name+'</div>'
					+'</div><br /><br /><div style="float: left; "><button style="background-color: red; border-radius: 20px; font-size:13px;" onClick="notRelevantOffer('+ data[item].id +');">סמן כלא רלוונטי</button><button onclick="offerClicked(' + data[item].id + ')" style="border-radius: 20px; font-size:13px;">פרטים נוספים</button></div>'
					+'</div>').appendTo($(areaName)) ; 
		}
	}
	
	function notRelevantOffer(offerId)
	{
		 $.ajax({
		        url: "AuctionManagementActions",
		        type: "post",
		        dataType: 'json',
		        data:{RequestType: "AuctionManagementOfferNotRelevant", OfferId:offerId},
		        success: function(data) {
		                console.log("Offer not relevant");  
		            },
		        error: function(data){
		            	console.log("error");}
		            
		        
		    });
	}
	
	function notRelevantAuction(auctionId)
	{
		 $.ajax({
		        url: "AuctionManagementActions",
		        type: "post",
		        dataType: 'json',
		        data:{RequestType: "AuctionManagementAuctionNotRelevant", AuctionId:auctionId},
		        success: function(data) {
		                console.log("Aution not relevant");  
		            },
		        error: function(data){
		            	console.log("error");}
		            
		        
		    });
	}

	
	function loadAuctionFromDB(data, areaName)
	{
		var description;
		console.log("adding current auction to "+ areaName);
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

		$(' <div id=' +data.id+' class="my-auction-container" style="padding-top: 0; cursor: hand; cursor: pointer;border-radius: 25px; background: #6D1F10; padding:10px; margin-top: 10px;"> <div class="my-auction-title">'+data.eventType.Name+ ' ב'+ data.area.Name +' <div style="float: left; margin-left: 15px;"> '+date.getDate() +"/" + month +"/" + date.getFullYear() +' </div></div>'
				+ '<div class="my-auction-description">'+description+'</div>'
				+ '<div class="my-auction-description">'+'כמות מוזמנים: '+data.guestesQuantiny+'</div>'
				+ '<div class="my-auction-description">'+'סגנון מוזיקה: ' +musicStyles+'</div>'
				+ '<div class="my-auction-description">'+'חריגים: ' +exceptions+'</div>'
				+'<br /><br /><div style="float: left; "><button style="background-color: gray; border-radius: 20px; font-size:13px;" onClick="notRelevantAuction('+ data.id +');">סמן כלא רלוונטי</button></div>'+
				+'</div>').appendTo($(areaName)) ;
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
	            }},
	        error: function(data){
	            	console.log("error");}
	            
	        
	    });
	}

	$(function() {
		ajaxOffersFormDBData();
	});
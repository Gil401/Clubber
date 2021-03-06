var currAucId;

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
	 
	function ajaxAuctionFromDBData() {
		$.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "GetDBData-AuctionReview"},
	        success: function(data) {
	        	if (data == "UserNotLoggedOn") {
					window.location.href = "Login.jsp";
				}
	            if (data != null) {
	            	console.log("GetDBData-AuctionReview");
		            loadAuctionFromDB(data);
		            currAucId= data.id;
	            }},
	        error: function(data){
	            	console.log("error- auction review");}	        
	    });
	}
	
	function makeOfferClicked()
	{
		$.ajax({
	        url: "AuctionOfferItemClicked",
	        type: "post",
	        dataType: 'json',
	        data:{ClickedItemType: "AuctionItemClicked", ItemID:currAucId},
	        success: function(data) {
	        	 console.log("redirect to new offer page");
	             window.location.href = 'prNewOffer.jsp';
	            },
	        error: function(data){
	            	console.log("error");}
	    });
	}
	
	$(function() {
        ajaxAuctionFromDBData();
	});

	

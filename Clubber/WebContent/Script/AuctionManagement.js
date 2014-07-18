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
			$(' <div id=' +data[item].id+' class="offer-item-container"> <div class="offer-item-title"> <div class= "offer-item-right-title" >'+data[item].prId.Name+'</div> <div class="offer-item-left-title" >' +formattedDate(data[item].submitDate) +'</div> </div>'
					+ '<div class="offer-item-content" >'
					+ '<div title="��� ��� ��� ����� �� ���� �����" class="offer-item-line-image-container" id="'+data[item].lineId.id+'" onclick="alert(' + data[item].lineId.id + ')"><img src="/Clubber/images/line_Img.png" class="offer-item-line-image"></div>'
					+ '<div title="��� ��� ��� ����� �� ���� �����" class="offer-item-description" onclick="offerClicked(' + data[item].id + ')">'+description+'</div>'
					+'</div>'
					+'</div>').appendTo($(areaName)) ; 
		}
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
		$(' <div id=' +data.id+' class="my-auction-container" onclick="alert(' + data.id + ')" style="height:150px"> <div class="my-auction-title">'+data.eventType.Name+ ' - '+formattedDate(data.eventDate) +'</div>'
				+ '<div class="my-auction-description" title="��� ��� ��� ����� �� ���� �����">'+description+'</div>'
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
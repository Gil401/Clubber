var currAucId;

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
	        data:{RequestType: "GetDBData-AuctionReview", currAuctionID : 26},
	        success: function(data) {
	            if (data != null) {
	            	console.log("GetDBData-AuctionReview");
		            loadAuctionFromDB(data);
		            currAucId= data.auctionId;
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
	        data:{ClickedItemType: "AuctionItemClicked", ItemID:29},
	        success: function(data) {
	        	 console.log("redirect to new offer page");
	             window.location.href = 'NewOffer.jsp';
	            },
	        error: function(data){
	            	console.log("error");}
	    });
	}
	
	function getClientProfileReview() {
		$.ajax({
			url : "GetDBData",
			type : "post",
			dataType : 'json',
			data : {
				RequestType : "DBDataClientProfileReview"
			},
			success : function(data) {

				var i;
				if (data != null) {
					for (i = 0; i < data.punctuality; i++) {
						var star = $(".punctualityStars").children()[i];
						$(star).removeClass("star");
						$(star).addClass("yellow-Star");
					}

					for (i = 0; i < data.realiability; i++) {
						var star = $(".reliabilityStars").children()[i];
						$(star).removeClass("star");
						$(star).addClass("yellow-Star");
					}

					for (i = 0; i < data.general; i++) {
						var star = $(".generalStars").children()[i];
						$(star).removeClass("star");
						$(star).addClass("yellow-Star");
					}
				}

			},
			error : function(data) {
				console.log("error");
			}
		});
	}
	
	$(function() {
        ajaxAuctionFromDBData();
        getClientProfileReview();
	});

	

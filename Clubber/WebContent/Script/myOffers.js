var userID = $("#session_user_id").val();

$(document).ready(function() 
{
	loadDataIntoInputs(userID);
	
	getAuctionsAndOffers(userID, 0, 0);
	
	$('#offerStatus').on('change', function() {
		getAuctionsAndOffers(userID, $('#myLines').val(), $(this).val());})
		
	$('#myLines').on('change', function() {
		getAuctionsAndOffers(userID, $(this).val(), $("#offerStatus").val());})
	
	
});

function loadListDataFromDB(data, listName)
{
	console.log("adding"+ listName);
	 $.each(data, function(index, val) {
	            $('<option value="'+val.id+'">' + val.Name + '</option>').appendTo($(listName)) ;
	 });	
}

function getAuctionsAndOffers(prID, lineID, Status) {
	var dataRequest = $.ajax({
		url : "GetDBData",
		type : "POST",
		dataType : 'json',
		data : {
			RequestType : "GetDBData-offerPerAuction",
			userID : prID,
			lineID : lineID,
			status : Status,
		}
	});
	dataRequest.done(function(returnedData) {
		if(returnedData.length == 0) {
			$("#offerPerAuction_Container").html("<h1> לא נמצאו הצעות </h1>");
		}
		else {
			loadOffersFromDB(returnedData,'#offerPerAuction_Container');
		}
	});
}

function loadOffersFromDB(data, areaName)
{
	var description;
	console.log("adding offers to "+ areaName);
	$(areaName).html("");
	for (var item in data) {
		if (data[item].m_OfferData.description== null)
		{
			description="";
		}
		else
		{
			description= data[item].m_OfferData.description;
		}
		
		var date = new Date(data[item].m_OfferData.submitDate);
		var month = date.getMonth() + 1;
		var line_img= "/Clubber/images/LineImg.jpg";
		
		if ((data[item].m_OfferData.linePhotoURL != "" ) && (data[item].m_OfferData.linePhotoURL != null ))
		{
			line_img=data[item].m_OfferData.linePhotoURL;
		}
		
		$('<div id=' +data[item].m_OfferData.id+' class="offer-item-container bg"><div  class="offer-item-line-image-container" id="'+data[item].m_OfferData.lineId.id+'" onclick="openDetails('+data[item].m_OfferData.lineId.id+');"><img src="'+line_img+'" class="offer-item-line-image" style="float: right; max-width: 100px; max-height:100px; margin-left:25px;margin-top: 8px;cursor: pointer;"></div>'
				+ ' <div class="offer-item-title"> <div class="offer-item-left-title" style="float: left">' + date.getDate() +"/" + month +"/" + date.getFullYear() +'</div> </div>'
				+ '</br><div class="offer-item-content" >'
				+ '<div class="offer-item-description">'+description+'</div>'
				+'<br/><div cless="0ffer-item-status">סטטוס: '+data[item].m_OfferData.offerStatusId.Name+'</div>'
				+'</div><br /><br /><div style="float: left; "><button onclick="offerClicked(' + data[item].m_OfferData.id + ',' + data[item].m_OfferData.auctionId + ')" style="border-radius: 20px; font-size:13px;">פרטים נוספים</button></div>'
				+'</div>').appendTo($(areaName)) ; 
	}
}

function offerClicked(offerID, aucId)
{
	$.ajax({
        url: "AuctionOfferItemClicked",
        type: "post",
        dataType: 'json',
        data:{ClickedItemType: "AuctionItemClicked", ItemID:aucId},
        success: function(data) {
             setOfferId(offerID);
            },
        error: function(data){
            	console.log("error");}
    });
	  
} 

function setOfferId(offerID) {
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
function loadDataIntoInputs(id) {
	var dataRequest = $.ajax({
		url : "GetDBData",
		type : "POST",
		dataType : 'json',
		data : {
			RequestType : "GetDBData-loadMyLinesByPRId",
			userID : id
		}
	});
	dataRequest.done(function(returnedData) {
		$.each(returnedData, function(index, val) {
			if (val.length == 0) {
				$('#lineContainer').replaceWith(
						"<h1> עדיין אין לך ליינים?! <br>  למה אתה מחכה? </h1>");
			} else {
				for (var i = 0; i < val.length; i++) {
					$(
							'<option value="' + val[i].id + '">' + val[i].Name
									+ '</option>').appendTo($("#myLines"));
				}
			}
		});
		ajaxOfferStatusFormDBData();
	});
}

function getAvailableDay(lineID) {
	var dataRequest = $.ajax({
		url : "GetDBData",
		type : "POST",
		dataType : 'json',
		data : {
			RequestType : "GetDBData-getAvailableDay",
			lineID : lineID
		}
	});
	dataRequest.done(function(returnedData) {
		$.each(returnedData, function(index, val) {
			$(function() {
				$('#datepicker').datepicker({
					beforeShowDay : function(dt) {
						return [ dt.getDay() != val, "" ];
					},
					changeMonth : true,
					changeYear : false
				});
			});
		});
	});
}

function ajaxOfferStatusFormDBData() {
    $.ajax({
        url: "GetDBData",
        type: "post",
        dataType: 'json',
        data:{RequestType: "DBDataGetOfferStatus"},
        success: function(data) {
            if (data != null) {
                loadListDataFromDB(data, '#offerStatus' );
            }},
        error: function(data){
            	console.log("error");}
    });
}

function loadOffers(line, date, status) {
	$.get("patternTemplates/offerBox.tpl", function(data) {
		var lineTemplate = data;
		console.log('template was loaded');

		var dataRequest = $.ajax({
			url : "GetDBData",
			type : "POST",
			dataType : 'json',
			data : {
				RequestType : "GetDBData-loadOffersByPrData",
				date : date,
				lineId : line,
				status : status
			}
		});
		// TODO: date & hour init
		// ******************************************************************************************
		dataRequest.done(function(returnedData) {
			$.each(returnedData, function() {
				console.log($(this));
				$('#temp_container').html(lineTemplate);
				var temp_template = $('#temp_container');
				temp_template.find('.line_box_place').html(
						$(this)[0]['m_StreetId']['Name'] + ' '
								+ $(this)[0]['m_HouseNumber'] + ', '
								+ $(this)[0]['m_CityId']['Name']);
				$('#lines_container').append($('#temp_container').html());
				console.log($(this)[0].m_Name)
			});
			$('#temp_container').html('');
		});
	});
};


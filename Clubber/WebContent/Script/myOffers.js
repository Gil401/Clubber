var userID = $("#session_user_id").val();

$(document).ready(function() {

	loadDataIntoInputs(userID);
	$('#myLines').on('change', function() {
		getAuctionsAndOffers(userID, $(this).val(), $("#status").val());
	});
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
		$.each(returnedData, function(index, val) {
			$('<option value="' + val[0].id + '">' + val[0].Name + '</option>')
					.appendTo($("#myLines"));
		});
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



/*
 * ​$("datepicker").datepicker({ beforeShowDay: function(date) { return
 * [date.getDay() == 5];
 * 
 */

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


function loadOffers (line, date, status){
	$.get( "patternTemplates/offerBox.tpl", function( data ) {
		 var lineTemplate = data;
		  console.log('template was loaded');
		  
		  var dataRequest = $.ajax({
				url : "GetDBData",
				type : "POST",
				dataType : 'json',
				data : {
					RequestType : "GetDBData-loadOffersByPrData",
					date : date,
					lineId: line,
					status: status
					}
				});
		  // TODO: date & hour init ******************************************************************************************
		  dataRequest.done(function(returnedData){
			  $.each(returnedData, function(){
				  console.log($(this));
				  $('#temp_container').html(lineTemplate);
				  var temp_template = $('#temp_container');
				  temp_template.find('.line_box_place').html($(this)[0]['m_StreetId']['Name']+' '+$(this)[0]['m_HouseNumber']+', '+$(this)[0]['m_CityId']['Name'] );
				   
				  $('#lines_container').append($('#temp_container').html());
				  console.log($(this)[0].m_Name)
			  });
			  $('#temp_container').html('');
		  });
		 
		  
		});
	
}
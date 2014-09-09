	$(function() {
		ajaxOfferFormDBData();
	});	
	
	function ajaxNewOfferCreation()
	{	
		var timeToClose= $('#closing-time').value();
		
		var businessTypes= $('#business-type').find('input').serialize();
		var final_businessType= replaceAll("%23business-type=","",businessTypes);
		var final2_businessTypes= replaceAll("%2F","",final_businessType); 
		
		var sitsTypes= $('#sitts-type').find('input').serialize();
		var final_sitsTypes= replaceAll("%23sitts-type=","",sitsTypes);
		var final2_sitsTypes= replaceAll("%2F","",final_sitsTypes); 
		
		var eventType=  $('#event-type')[0].value;
		var date= $('#datepicker')[0].value;
		var isDateFlexible= $('#is-flexible-date')[0].value;
		var guestsQuantity=  $('#guests-quantity')[0].value;
		var exceptionsDescription= $('#exceptions-description')[0].value;
		var minAge= $('#min-age')[0].value;
		var area= $('#area')[0].value;
		var certainBusiness= $('#certain-business')[0].value;
		var smoking= $('#smoking')[0].value;
		var generalDescription= $('#general-description')[0].value;
		
	    $.ajax({
	        url: "NewOffer",
	        type: "post",
	        dataType: 'json',
	        data: {ClosingTime: timeToClose, MusicStyleList: final2, Datepicker: date, IsFlexibleDate: isDateFlexible,
	        	GuestsQuantity:guestsQuantity, ExceptionsDescription: exceptionsDescription, MinAge: minAge,
	        	Area: area, BusinessTypeList: final2_businessTypes, CertainBusiness:certainBusiness, Smoking: smoking, SitsTypeList:final2_sitsTypes, GeneralDescription:generalDescription},
	        success: function(data){
	        	console.log("Offer creation succedded");}
	        });
		
	}
	
function loadListDataFromDB(data, listName)
	{
		console.log("adding"+ listName);
		 $.each(data, function(index, val) {
		            $('<option value="'+val.id+'">' + val.Name + '</option>').appendTo($(listName)) ;
		        });	
	}
	
	function loadCheckboxListDataFromDB(data, listName)
	{
		console.log("adding"+ listName);
		 $.each(data, function(index, val) {
		            $('<input id="'+val.id+'" type="checkbox" name="'+listName+'" value=' + val.id + '/>' +
		            		'<label style="padding-right:10px; font-weight:normal;" for="'+val.id+'">' + val.Name + '</label></br>').appendTo($(listName)) ;
		        });	
	}
	function ajaxNewOfferCreation()
	{	
		
	    $.ajax({
	        url: "NewOffer",
	        type: "post",
	        dataType: 'json',
	        data: {EventType: eventType, MusicStyleList: final2, Datepicker: date, IsFlexibleDate: isDateFlexible,
	        	GuestsQuantity:guestsQuantity, ExceptionsDescription: exceptionsDescription, MinAge: minAge,
	        	Area: area, BusinessTypeList: final2_businessTypes, CertainBusiness:certainBusiness, Smoking: smoking, SitsTypeList:final2_sitsTypes, GeneralDescription:generalDescription},
	        success: function(data){
	        	console.log("offer creation succedded");}
	        });
		
	}
	

	function ajaxOfferFormDBData() {
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "GetDBData-NewOffer"},
	        success: function(data) {
	            if (data != null) {
	                console.log("Loading data from DB");  
	                loadCheckboxListDataFromDB(data.lines, '#Lines_to_offer');
	                loadCheckboxListDataFromDB(data.treats, '#treats_to_offer' );
	                loadCheckboxListDataFromDB(data.sittsType, '#sitts-type' );
	            }},
	        error: function(data){
	            	console.log("error");}
	    });
	}

	function mandatoryEventDateCheck() {
		
		if ($("#datepicker").datepicker("getDate") == null)
		{
			$("#event-date-error").removeClass().addClass('error-label-displayed');
			$("#datepicker").focus();
			return false;
		}
		else
		{
			$("#event-date-error").removeClass().addClass('error-label-hidden');
			return true;
		}
	}
	
	function newOfferClicked()
	{
		if (mandatoryFieldsCheck()== true)
		{
			ajaxNewOfferCreation();
		}
	}
	
	function mandatoryFieldsCheck()
	{
		var res= new Boolean();
		res= true;
		res= mandatoryCheck("general-description", "general-description-error") && res;
		res= mandatoryCheck("min-age" , "min-age-error") && res;
		res= mandatoryCheck("guests-quantity","guests-quantity-error") && res;
		res= mandatoryEventDateCheck() && res;
		return res;
	}
	
	
	function mandatoryCheck(fieldName,errorLabelID)
	{
		if (($("#" +fieldName).val() == null) || ($("#" +fieldName).val().trim() == ""))
		{
			$("#" +errorLabelID).removeClass().addClass('error-label-displayed');
			$("#" +fieldName).focus();

			return false;
		}
		else
		{
			$("#" +errorLabelID).removeClass().addClass('error-label-hidden');
			return true;			
		}
	}
	
	function replaceAll(find, replace, str) 
	{
		  return str.replace(new RegExp(find, 'g'), replace);
	}
	
	
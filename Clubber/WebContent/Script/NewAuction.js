	$(function() {
		var date = new Date();
		var currentMonth = date.getMonth();
		var currentDate = date.getDate();
		var currentYear = date.getFullYear();
		$('#eventDatepicker').datepicker({
			minDate: new Date(currentYear, currentMonth, currentDate)});
		ajaxAuctionFormDBData();		
	});
	
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

	function ajaxAuctionFormDBData() {
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "GetDBData-NewAuction"},
	        success: function(data) {
	            if (data != null) {
	                console.log("Loading data from DB");  
	                loadListDataFromDB(data.eventTypes, '#event-type' );
	                loadCheckboxListDataFromDB(data.musicStyles, '#musicStyle' );
	                loadListDataFromDB(data.area, '#area' );
	                loadCheckboxListDataFromDB(data.businessType, '#business-type' );
	                loadListDataFromDB(data.certainBusiness, '#certain-business' );
	                loadCheckboxListDataFromDB(data.sittsType, '#sitts-type' );
	            }},
	        error: function(data){
	            	console.log("error");}
	    });
	}

	function mandatoryEventDateCheck() {
		
		if ($("#eventDatepicker").datepicker("getDate") == null)
		{
			$("#event-date-error").removeClass().addClass('error-label-displayed');
			$("#eventDatepicker").focus();
			return false;
		}
		else
		{
			$("#event-date-error").removeClass().addClass('error-label-hidden');
			return true;
		}
	}
	
	function newAuctionClicked()
	{
		if (mandatoryFieldsCheck()== true)
		{
			ajaxNewAuctionCreation();
		}
	}
	
	function mandatoryFieldsCheck()
	{
		var res= new Boolean();
		res= true;
		res= mandatoryEventDateCheck() && res;
		res= mandatoryCheck("guests-quantity","guests-quantity-error",res) && res;
		res= mandatoryCheck("min-age" , "min-age-error",res) && res;
		res= mandatoryCheck("general-description", "general-description-error", res) && res;
		return res;
	}
	
	
	function mandatoryCheck(fieldName,errorLabelID, res)
	{
		if ((res==true) && (($("#" +fieldName).val() == null) || ($("#" +fieldName).val().trim() == "")))
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
	
	function ajaxNewAuctionCreation()
	{	
		var musicStyles= $('#musicStyle').find('input').serialize();
		var final= replaceAll("%23musicStyle=","",musicStyles);
		var final2= replaceAll("%2F","",final); 
		var businessTypes= $('#business-type').find('input').serialize();
		var final_businessType= replaceAll("%23business-type=","",businessTypes);
		var final2_businessTypes= replaceAll("%2F","",final_businessType); 
		
		var sitsTypes= $('#sitts-type').find('input').serialize();
		var final_sitsTypes= replaceAll("%23sitts-type=","",sitsTypes);
		var final2_sitsTypes= replaceAll("%2F","",final_sitsTypes); 
		
		var eventType=  $('#event-type')[0].value;
		var date= $('#eventDatepicker')[0].value;
		var isDateFlexible= $('#is-flexible-date')[0].value;
		var guestsQuantity=  $('#guests-quantity')[0].value;
		var exceptionsDescription= $('#exceptions-description')[0].value;
		var minAge= $('#min-age')[0].value;
		var area= $('#area')[0].value;
		var certainBusiness= $('#certain-business')[0].value;
		var smoking= $('#smoking')[0].value;
		var generalDescription= $('#general-description')[0].value;
		
	    $.ajax({
	        url: "NewAuction",
	        type: "post",
	        dataType: 'json',
	        data: {EventType: eventType, MusicStyleList: final2, Datepicker: date, IsFlexibleDate: isDateFlexible,
	        	GuestsQuantity:guestsQuantity, ExceptionsDescription: exceptionsDescription, MinAge: minAge,
	        	Area: area, BusinessTypeList: final2_businessTypes, CertainBusiness:certainBusiness, Smoking: smoking, SitsTypeList:final2_sitsTypes, GeneralDescription:generalDescription},
	        success: function(data){
	        	console.log("Auction creation succedded");
	        	window.location.href = "AuctionManagement.jsp";
	     },
	        error: function(data){
	            	console.log("error");}
	    });
	}

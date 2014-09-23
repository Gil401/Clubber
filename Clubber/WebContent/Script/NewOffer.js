	$(function() {
		ajaxOfferFormDBData();
		
		var date = new Date();
		$('#closing-date').datepicker({
			dateFormat: "dd/mm/yy",
			minDate: date,
			changeMonth: true,
		  	changeYear: true,
		 });

		$("#closing-date").change(function(){
			var startDate = $("#startDate").val();
			
			$('#endDate').attr("disabled", false);
			
			$('#endDate').datepicker({
				dateFormat: "dd/mm/yy",
				minDate: startDate,
				changeMonth: true,
		      	changeYear: true,
			});
		});
	});	
	
	function ajaxNewOfferCreation()
	{
		var description= $('#general-description')[0].value;
		var maxArrivalTime=  convertStringToLongTime($('#max-arrival-time')[0].value);
		var endDate= $('#closing-date')[0].value;
		var lineId=$('#Lines_to_offer').val()
		
		var treats= $('#treats_to_offer').find('input').serialize();
		var final= replaceAll("treats=","",treats);
		var final2= replaceAll("%2F","",final);
		
	    $.ajax({
	        url: "NewOffer",
	        type: "post",
	        dataType: 'json',
	        data: {Treats: final2, LineName: lineId, 
	        	EndDate: endDate, Description: description, MaxArrivalTimeAsLong: maxArrivalTime},
	        success: function(data){
	        	console.log("Offer creation succedded");
	        	window.location.href = 'OfferReview.jsp';
	        }
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

	function ajaxOfferFormDBData() {
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "GetDBData-NewOffer"},
	        success: function(data) {
	        	if (data == "UserNotLoggedOn") {
					window.location.href = "Login.jsp";
				}
	        	if (data != null) {
	                console.log("Loading data from DB");  
                
                	var linesDiv = $("#Lines_to_offer");
	            		            	
            		for(var i=0; i < data.lines.length; i++){
	            		
            			var element = '<option value="'+data.lines[i].id + '">' + data.lines[i].Name + '</option>';
	            		linesDiv.append($(element));
	            	}
	                
        			var treatsDiv = ('#treats_to_offer');
	            	for(var i=0; i < data.treats.length; i++){
	            		
	              		 $('<input id="treat'+data.treats[i].id+'" type="checkbox" name="treats" value=' + data.treats[i].id+ '/>' +
	                       		'<label style="padding-right:10px; font-weight:normal;" for="'+data.treats[i].id+'">' + data.treats[i].Name + '</label></br>').appendTo($(treatsDiv)) ;
	              	}
	            }},
	        error: function(data){
	            	console.log("error");}
	    });
	}

	function mandatoryEventDateCheck() {
		
		if ($("#closing-date").datepicker("getDate") == null)
		{
			$("#event-date-error").removeClass().addClass('error-label-displayed');
			$("#closing-date").focus();
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
	
	
function formattedDate(date) {
    var d = new Date(date || Date.now()),
        month = '' + (d.getMonth() + 1),
    day = '' + d.getDate(),
    year = d.getFullYear();

	if (month.length < 2) month = '0' + month;
	if (day.length < 2) day = '0' + day;

return [day, month, year].join('/');
}

function setErrorMessages() {
	$.extend(jQuery.validator.messages, {
		required : "שדה חובה",
	});
}

function initiateFormBtns(){
	$("#editLineDel").click(function() {
		
		$('#EditLineData').show();
		$('#viewLineData').hide();
		LoadDataToInputs();	
	});
	
	$("#updateLineDel").click(function() {
		ajaxLineDtailesUpdate();
		$('#EditLineData').hide();
		$('#viewLineData').show();	
	});
}

function LoadDataToInputs()
{
	$.ajax({
		url : "GetDBData",
		type : "post",
		dataType : 'json',
		data : {
			RequestType : "GetDBData-LineProfile"
		},
		success : function(data) {
			console.log(data);
			$("#lineName").val(data.m_LineName);
			$("#businessId").val(data.business.Name);
			$("#startDate").val(formattedDate(data.startDate));
			$("#endDate").val(formattedDate(data.endDate));
			$("#minAge").val(data.minAge);
			$("#etranceFee").val(data.entranceFee);
			$("#DJ").val(data.dj);
			$("#description").val(data.description);
			
			uploadMusicStyleData(data.musicStylesIds);	
		
		},
		error : function(data) {
			console.log("error");
		}
	});
}	


function uploadMusicStyleData(lineMusicChecked){
    $.ajax({
        url: "GetDBData",
        type: "post",
        dataType: 'json',
        data:{RequestType: "DBDataGetMusicStyleData"},
        success: function(musicStyleList) {
        	var musicStyleDiv = $("#musicStyleEdt");
        	
        	//delete former data 
        	musicStyleDiv.html("");
        	
        	for(var i=0; i < musicStyleList.length; i++){
        		
        		var element = '<label><input type="checkbox" id=music'+musicStyleList[i].id+' name="musicStyle">' +musicStyleList[i].Name+ '</label>' ;
        		musicStyleDiv.append($(element));
        	}
        	
        	for (var item in lineMusicChecked) 
			{
				$('#music'+ lineMusicChecked[item].id).attr("checked", true);
			}
        	
        	uploadAllBusinessData();	
        },
        error: function(data){
            	console.log("error");}
    });
	
}


function getLineProfile() {
	$('#EditLineData').hide();
	$('#viewLineData').show();
	
	$.ajax({
		url : "GetDBData",
		type : "post",
		dataType : 'json',
		data : {
			RequestType : "GetDBData-LineProfile"
		},
		success : function(data) {
			console.log(data);
			$("#lineNameLbl").text(data.m_LineName);
			$("#businessIdLbl").text(data.business.Name);
			$("#startDateLbl").text(formattedDate(data.startDate));
			$("#endDateLbl").text(formattedDate(data.endDate));
			$("#minAgeLbl").text(data.minAge);
			$("#etranceFeeLbl").text(data.entranceFee);
			$("#DJLbl").text(data.dj);
			$("#descriptionLbl").text(data.description);
			
			for (var item in data.musicStylesIds) 
			{
				$("#musicStyleContainer").append("<div class='music-style'><img src='/Clubber/images/Check_Image.png' class='offer-item-treat-image'><label class='offer-multi-value-label'>"+data.musicStylesIds[item].Name+"</label></div><br>");
			}
			
			for (var item in data.prs) 
			{
				$("#prsContainer").append("<div class='pr-item'><img src='/Clubber/images/Check_Image.png' class='offer-item-treat-image'><label class='offer-multi-value-label'>"+data.prs[item].Name+"</label></div><br>");
			}
		
		},
		error : function(data) {
			console.log("error");
		}
	});
}


function ajaxLineDtailesUpdate()
{	
	var musicStyles= $('#musicStyleEdt').find('input').serialize();
	var final= replaceAll("%23musicStyle=","",musicStyles);
	var final2= replaceAll("%2F","",final); 

	var lineName=  $('#lineName')[0].value;
	var businessId= $('#businessId')[0].value;
	var startDate= $('#startDate')[0].value;
	var endDate=  $('#endDate')[0].value;
	var minAge= $('#minAge')[0].value;
	var description= $('#description')[0].value;
	var etranceFee= $('#etranceFee')[0].value;
	var dj= $('#DJ')[0].value;
	
    $.ajax({
        url: "UpdateLineDetails",
        type: "post",
        dataType: 'json',
        data: {MusicStyleList: final2, LineName: lineName, BusinessId: businessId,
        	StartDate:startDate, EndDate: endDate, MinAge: minAge,
        	Description: description, EtranceFee: etranceFee, DJ:dj},
        success: function(data){
        	console.log("line update succedded");
     },
        error: function(data){
            	console.log("error");}
    });
}

function uploadAllBusinessData(){
    $.ajax({
        url: "GetDBData",
        type: "post",
        dataType: 'json',
        data:{RequestType: "GetDBData-BusinessLst"},
        success: function(businessList) {
        	var businesses = $("#businessName");
        	
        	//delete former data 
        	businesses.html("");
        	
			for (var i = 0; i < businessList.length; i++) {
				businesses.append('<option id=' + businessList[i].id +'>' + businessList[i].Name + '</option>');
			}        	
        },
        error: function(data){
            	console.log("error");}
    });
	
}

$(function() {
	getLineProfile();
	setErrorMessages();
	initiateFormBtns();
});


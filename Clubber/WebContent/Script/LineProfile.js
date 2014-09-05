var line_id=-1;

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
			
			var startDate = new Date(data.startDate);
			var endDate = new Date(data.endDate);
			var startMonth = startDate.getMonth() + 1;
			var endMonth = endDate.getMonth() + 1;
			line_id= data.id;
			$("#lineName").val(data.m_LineName);
			$("#businessName").val(data.business.Name);
			$("#businessId").val(data.business.id);
			$("#startDate").val(startDate.getDate() + "/" + startMonth + "/" + startDate.getFullYear());
			$("#endDate").val(endDate.getDate() + "/" + endMonth + "/" + endDate.getFullYear());
			$("#minAge").val(data.minAge);
			$("#etranceFee").val(data.entranceFee);
			$("#DJ").val(data.dj);
			$("#description").val(data.description);
			$("#Day").val(data.m_DayInWeek);
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
			
        	
			//load all music styles:
        	for (var item in musicStyleList) 
			{
        		 $('<input id="music'+musicStyleList[item].id+'" type="checkbox" name="musicStyleEdt" value=' + musicStyleList[item].id + '/>' +
            		'<label style="padding-right:10px; font-weight:normal;" for="'+musicStyleList[item].id+'">' + musicStyleList[item].Name + '</label></br>').appendTo($('#musicStyleEdt')) ;
			} 
			 
			//mark check box in db:
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
			
			var startDate = new Date(data.startDate);
			var endDate = new Date(data.endDate);
			var startMonth = startDate.getMonth() + 1;
			var endMonth = endDate.getMonth() + 1;

			$("#lineNameLbl").text(data.m_LineName);
			$("#businessIdLbl").text(data.business.Name);
			$("#startDateLbl").text(startDate.getDate() + "/" + startMonth + "/" + startDate.getFullYear());
			$("#endDateLbl").text(endDate.getDate() + "/" + endMonth + "/" + endDate.getFullYear());
			$("#minAgeLbl").text(data.minAge);
			$("#etranceFeeLbl").text(data.entranceFee);
			$("#DJLbl").text(data.dj);
			$("#descriptionLbl").text(data.description);
			$("#DayLabel").text(convertNumToDay(data.m_DayInWeek));
			
			$("#musicStyleContainer").html("");
			
			for (var item in data.musicStylesIds) 
			{
				$("#musicStyleContainer").append("<div class='music-style'><img src='/Clubber/images/Check_Image.png' class='offer-item-treat-image'><label class='offer-multi-value-label'>"+data.musicStylesIds[item].Name+"</label></div><br>");
			}
			
			$("#prsContainer").html("");
			
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

function convertNumToDay(dayInInt)
{
	var res='';
	if(dayInInt==1)
	{
		res='א';
	}
	else if(dayInInt==2)
	{
		res='ב';
	}
	else if(dayInInt==3)
	{
		res='ג';
	}
	else if(dayInInt==4)
	{
		res='ד';
	}
	else if(dayInInt==5)
	{
		res='ה';
	}
	else if(dayInInt==6)
	{
		res='ו';
	}
	else if(dayInInt==7)
	{
		res='ש';
	}

	return res;
}
function replaceAll(find, replace, str) 
{
	  return str.replace(new RegExp(find, 'g'), replace);
}

function ajaxLineDtailesUpdate()
{	
	var musicStyles= $('#musicStyleEdt').find('input').serialize();
	var final= replaceAll("musicStyleEdt=","",musicStyles);
	var final2= replaceAll("%2F","",final); 
  
	var lineName=  $('#lineName')[0].value;
	var businessId= $('#businessName')[0].value;
	var startDate= $('#startDate')[0].value;
	var endDate=  $('#endDate')[0].value;
	var minAge= $('#minAge')[0].value;
	var description= $('#description')[0].value;
	var etranceFee= $('#etranceFee')[0].value;
	var dj= $('#DJ')[0].value;
	var day=$("#Day")[0].value;
	
    $.ajax({
        url: "UpdateLineDetails",
        type: "post",
        dataType: 'json',
        data: {Id:line_id, MusicStyleList: final2, LineName: lineName, BusinessId: businessId,
        	StartDate:startDate, EndDate: endDate, MinAge: minAge,
        	Description: description, EtranceFee: etranceFee, DJ:dj, Day:day},
        success: function(data){
        	console.log("line update succedded");
    		getLineProfile();
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
				businesses.append('<option value=' + businessList[i].id +'>' + businessList[i].Name + '</option>');
			}        	
        },
        error: function(data){
            	console.log("error");}
    });
	
}

var date = new Date();
$('#startDate').datepicker({
	dateFormat: "dd/mm/yy",
	minDate: date,
	changeMonth: true,
  	changeYear: true,
  	showOn: "button",
    buttonImage: "images/calendar.gif",
    buttonImageOnly: true
 });

$("#startDate").change(function(){
	var startDate = $("#startDate").val();
	
	$('#endDate').attr("disabled", false);
	
	$('#endDate').datepicker({
		dateFormat: "dd/mm/yy",
		minDate: startDate,
		changeMonth: true,
      	changeYear: true,
      	showOn: "button",
        buttonImage: "images/calendar.gif",
        buttonImageOnly: true
	});
});


$(function() {

	getLineProfile();
	setErrorMessages();
	initiateFormBtns();
});


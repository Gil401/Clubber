<%@page import="ClubberLogic.UserType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@  include file="patternTemplates/header.tpl" %>
	
		<style type="text/css">
		table.auctions-table {
			font-family: verdana,arial,sans-serif;
			font-size:11px;
			color:#FFF;
			border-width: 1px;
			border-color: #a9c6c9;
			border-collapse: collapse;
			width: 100%;
		}
		table.auctions-table th {
			border-width: 1px;
			padding: 8px;
			border-style: solid;
			border-color: #a9c6c9;
			background-color: #EC5538;
			text-align: right;
		}
		table.auctions-table td {
			border-width: 1px;
			padding: 8px;
			border-style: solid;
			border-color: #a9c6c9;
			cursor: hand;
			cursor: pointer;
		}
		</style>

<div id="latest-events">
	<div class="container">
		<div class="event-inner">
			<div class="row">
				<div id="latest-event-content" class="col-sm-7 col-md-8">
<div class="bg">

		<h2 id="prTitle">כל המכרזים שפורסמו במערכת:</h2>
		<br/>
		
		<div class='new-auction-field'>
		         <label class='new-auction-field-title'>סוג האירוע</label>  
		         <select class='new-auction-field-input' id="event-type" name="event-type">
		         </select> &nbsp;&nbsp;&nbsp;<input type="text" id="other" style="display: none;" > 
	    </div> 
	    
	    <div class='new-auction-field'> 
	         <label class='new-auction-field-title'> אזור </label>
	         <select class='new-auction-field-input' id= 'area' name= area>
	         </select>
	    </div>
	     
	     <div class='new-auction-field'> 
	         <label class='new-auction-field-title'> כמות אורחים </label>
	         <select class='new-auction-field-input' id= 'guestsQuantity' name= guestsQuantity>
	         	<option value="0"> ללא </option>
	         	<option value="10">עד 10 </option>
	         	<option value="20">עד 20</option>
	         	<option value="30">עד 30</option>
	         </select>
	     </div> 
	     
	    <button id="SearchAuctions" onClick="getFilteredAuctions();" type="button" >חפש</button>	    
	     <button id="ClearSearch" onClick="clearSearchFields();" type="button" >נקה</button>	  
		<br/><br/>
			    
      	<div class='all-auctions-container'>
      	
      	</div>  
      	<br/>
      	
      	<div class="search-by-my-lines-area">
			<input type="checkbox" name="searchByMyLines" id="searchByMyLines">
			סנן לפי התאמה לליינים שלי
			<br>
		</div>	
      	 
      	</div>   
      	</div>   
      	</div>   
      	</div>   
      	</div>   
      	</div>   
      	
<%@  include file="patternTemplates/footer.tpl" %>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/jquery.prettyPhoto.js"></script>
<script type="text/javascript" src="js/jquery.fitvids.js"></script>
<script type="text/javascript" src="js/soundcloud.player.api.js"></script>
<script type="text/javascript" src="js/sc-player.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/coundown-timer.js"></script>
<script type="text/javascript" src="js/jquery.backstretch.min.js"></script>
<script type="text/javascript" src="js/switcher.js"></script>
<script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<script src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
<script src="js/datepicker-he.js"></script>
	<script>
	
	$("#searchByMyLines").change(function() {
		if(this.checked){
			getAuctionsByMyLines();
		}
		else{
			getAllAuctions();
		}
	});	
	
	function getAuctionsByMyLines(){
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data: {RequestType: "searchByMyLines"},
	        success: function(data) {
	        	loadAuctionsFromDB(data,".all-auctions-container");
	        },
	        error: function(data){
	            	console.log("error");}
	    });
	}
	
	function showAuctions(data){
		console.log("adding auctions");
		var counterDescription= " הצעות התקבלו  ";
		
		$(".all-auctions-container").html("");

		var table= '<table class="auctions-table" id="auctionsTable" cellspacing="2">'
			  +'<thead><tr><th>סוג אירוע</th><th>תאריך</th><th>גיל מינימלי</th><th>איזור</th><th>מספר הצעות שהוצעו</th></tr></thead><tbody>';

		for (var item in data) {
			var date = new Date(data[item].eventDate);
			var month = date.getMonth() + 1;
			
			table += '<tr id=' +data[item].id+' title="לחץ כאן כדי לראות את פרטי המכרז" onclick="auctionClicked('+data[item].id + ');">'
					 +'<td>'+ data[item].eventType.Name+ '</td>'
 					 +'<td>'+ date.getDate() + "/" + month + "/" + date.getFullYear() +'</td>'
					 +'<td>'+data[item].minAge+'</td>'
					 +'<td>'+data[item].area.Name+'</td>'
					 +'<td>'+data[item].offerNumber+counterDescription +'</td></tr>';
		}
		
		table += '</tbody></table>';
		$(".all-auctions-container").append($(table));
	}
			
	function auctionClicked(auctionID)
	{
		  $.ajax({
		        url: "AuctionOfferItemClicked",
		        type: "post",
		        dataType: 'json',
		        data:{ClickedItemType: "AuctionItemClicked", ItemID:auctionID},
		        success: function(data) {
		        	 console.log("redirect to auction management page");
		             window.location.href = 'AuctionReview.jsp';
		            },
		        error: function(data){
		            	console.log("error");}
		    });
	}
	
	function getAllAuctions(){
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data: {RequestType: "DBDataAllAuctions"},
	        success: function(data) {
	        	loadAuctionsFromDB(data,".all-auctions-container");
	        },
	        error: function(data){
	            	console.log("error");}
	    });
	}
	
	$(function onLoad(){
		ajaxFieldsDataFormDBData();	
	});
	
    
    function loadListDataFromDB(data, listName)
	{
		console.log("adding"+ listName);
		 $.each(data, function(index, val) {
		            $('<option value="'+val.id+'">' + val.Name + '</option>').appendTo($(listName)) ;
		        });	
	}
    

	function ajaxFieldsDataFormDBData() {
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "GetDBData-NewAuction"},
	        success: function(data) {
	            if (data != null) { 
	            	$('#event-type').html("");
	            	$('#area').html("");
	            	$('#event-type').append('<option value="0"> ללא </option>');
	            	$('#area').append('<option value="0"> ללא </option>');
	                loadListDataFromDB(data.eventTypes, '#event-type' );
	                loadListDataFromDB(data.area, '#area' );
	            }
	    		getAllAuctions();},
	        error: function(data){
	            	console.log("error");}
	    });
	}
	
	function clearSearchFields()
	{
		ajaxFieldsDataFormDBData();
	}
	
	function getFilteredAuctions()
	{
		var eventTypeId= $('#event-type')[0].value;
		var areaId= $('#area')[0].value;
		var guestesQuantiny= $('#guestsQuantity')[0].value;
		
		 $.ajax({
		        url: "GetDBData",
		        type: "post",
		        dataType: 'json',
		        data: {RequestType: "DBDataFilteredAuctions", EventTypeId: eventTypeId, GuestesQuantiny: guestesQuantiny, AreaId: areaId},
		        success: function(data) {
		        	loadAuctionsFromDB(data,".all-auctions-container");
		        },
		        error: function(data){
		            	console.log("error");}
		    });
		
	}
	
	function loadAuctionsFromDB(data, areaName)
	{
		var description;
		console.log("adding current auction to "+ areaName);
		$(areaName).html("");
		for (var item in data) {
			if (data[item].description== null)
			{
				description="";
			}
			else
			{
				description= data[item].description;
			}
	
			var exceptions = 'אין';
			
			if (data[item].exceptionsDescription) {
				exceptions = data[item].exceptionsDescription;
			}
			
			var date = new Date(data[item].eventDate);
			var month = date.getMonth() + 1;
			
			var musicStyles = "לא נבחר";
			if (data[item].musicStyle.length > 0) {
				musicStyles = "";
				for (var item2 in data[item].musicStyle) 
				{
					musicStyles += data[item].musicStyle[item2].Name + " ,";
				}
				
				if (musicStyles.length > 1) {
					musicStyles = musicStyles.substring(0,musicStyles.length - 2);
				}
			}
	
			$(' <div id=' +data[item].id+' class="my-auction-container" style="padding-top: 0; border-radius: 25px; background: #CC3333; padding:10px; margin-top: 10px;"> <div class="my-auction-title title-container">'+data[item].eventType.Name+ ' ב'+ data[item].area.Name +' <div style="float: left; margin-left: 15px; font-size:20px;"> '+date.getDate() +"/" + month +"/" + date.getFullYear() +' </div></div>'
					+ '<div class="my-auction-description auction-description-field-container">'+description+'</div><br/>'
					+ '<div class="my-auction-description">'+'כמות מוזמנים: '+data[item].guestesQuantiny+'</div>'
					+ '<div class="my-auction-description">'+'סגנון מוזיקה: ' +musicStyles+'</div>'
					+ '<div class="my-auction-description">'+' תיאור מוזמנים חריגים: ' +exceptions+'</div>'
					+ '<br/><dic class="my-auction-description" style="font-weight:bold;" >'+'סטטוס:'+data[item].auctionStatus.Name
					+'<div style="float: left; "><button id="ActivateAuction" style="background-color: gray; border-radius: 20px; font-size:13px;" onClick="auctionClicked('+data[item].id + ');">הפוך לפעיל</button></div>'+
					+'</div></div>').appendTo($(areaName)) ;
			
		}
	}
	
	</script>	
	</body>
</html>
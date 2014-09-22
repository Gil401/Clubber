<%@ page contentType="text/html; charset=UTF-8" %>
<%@  include file="patternTemplates/header.tpl" %>
    
    <div class= 'site-header'>
    <div class= 'upper-header'>
    	<div class='logo'></div>
    	<div class='advertisements'></div>
    	 </div>
    </div>
<div id="latest-events">
<div class="container">
 <div class="event-inner">
 <div class="row">                       
                    <div id="latest-event-content" class="col-sm-7 col-md-8">
					    <div class='main-container'>  
	<div class="business-details bg">
		<form class="business-details-form" id="businessDetails" name="businessDetails" method="post" action="UpdateBusinessDetails" enctype="multipart/form-data">
		  	
		  	<input type="text" name="id" id="id"  hidden/>
			<h2 id="businessTitle">פרטי משתמש</h2>		  	
			<input type="file" name="pic"id="pic" style="max-width:30%; max-height:30%; float: left;">
		  	
		  	<div id="viewMode">
				<label name="viewdescription" class="descriptionLbl" id="viewdescription"></label>
	  			<br>
	  			
		  		<label id="viewbusinessNameLabel">סוג עסק</label>
			  	<label name="viewBusinessTypeName" id="viewBusinessTypeName"></label>
			  	<br>
			  	
			  	<label id="viewareaLabel">איזור</label>
			  	<label name="viewareaName" id="viewareaName"></label>
			  	<br>
	
			  	<label id="viewcityLabel">עיר</label>
			  	<label name="viewcityName" id="viewcityName" ></label>			
			  	<br>
		  	
			  	<label id="viewstreetLabel">רחוב</label>
			  	<label name="viewstreetName" id="viewstreetName" ></label>		
			  	<br>
	
			  	<label id="viewHomeNumberLabel">מס' בית</label>
			  	<label type="text" name="viewhomeNumber" id="viewhomeNumber"></label>			
			  	<br>
			  	
			  	<label id="viewphoneNumberLabel">טלפון</label>
			  	<label name="viewphoneNumber" id="viewphoneNumber"></label>			
			  	<br>
		  	
		  		<button id="editBusinessDel" type="button" >ערוך</button>
		  	</div>
		  	<div id="editMode" style="display:none">
		  	<label id="businessNameLabel">שם</label>
			  	<input type="text" name="name" id="name" required/>
		  	<br>
		  	
		  	<label id="businessNameLabel">סוג עסק</label>
		  	<input type="text" name="BusinessTypeId" id="BusinessTypeId" hidden/>
			  	<select type="text" name="BusinessTypeName" id="BusinessTypeName"></select>
		  	<br>
		  	
		  	<label id="areaLabel">איזור</label>
		  	<input type="text" name="areaId" id="areaId" hidden />			
			  	<select name="areaName" id="areaName" required></select>
		  	<br>

		  	<label id="cityLabel">עיר</label>
		  	<input type="text" name="cityId" id="cityId" hidden/>
			  	<select name="cityName" id="cityName" required></select>			
		  	<br>

		  	<label id="streetLabel">רחוב</label>
		  	<input type="text" name="streetId" id="streetId" hidden/>
			  	<input type="text" name="streetName" id="streetName" required/>			
		  	<br>

		  	<label id="streetLabel">מס' בית</label>
			  	<input type="text" name="homeNumber" id="homeNumber" required/>			
		  	<br>
		  	
		  	<label id="phoneNumberLabel">טלפון</label>
			  	<input type="text" name="phoneNumber" id="phoneNumber" required/>			
		  	<br>
			
			<label id="descriptionLabel">תיאור</label>
				<textarea class='new-auction-field-input' id="description" name="description"  rows="4" cols="50" style= "width:500px;resize: none;"> </textarea>
  			<br><br>
	  			<button id="updateBusinessDel" type="submit">שמור</button>
			</div>
		</form>		
		<br>
	</div>
	
	<div class="business-lines">
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
    <script type="text/javascript" src="js/jquery.alerts.js"></script>
<script type="text/javascript" src="js/jquery.fitvids.js"></script>
<script type="text/javascript" src="js/soundcloud.player.api.js"></script>
<script type="text/javascript" src="js/sc-player.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/coundown-timer.js"></script>
<script type="text/javascript" src="js/jquery.backstretch.min.js"></script>
<script type="text/javascript" src="js/switcher.js"></script>
<script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<script src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
	<script src="Script/Utils.js"  type="text/javascript" charset="utf-8" ></script>
	<script src="Script/BusinessProfile.js"  type="text/javascript" charset="utf-8" ></script>
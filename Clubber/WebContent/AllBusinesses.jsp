<%@page import="ClubberLogic.UserType"%>
<%@page import="Utlis.Constants"%>
<%@  include file="patternTemplates/header.tpl" %>
<br/>
<div id="latest-events">
<div class="container">
 <div class="event-inner">
 <div class="row">                       
                    <div id="latest-event-content" class="col-sm-7 col-md-8">
	<div class="all-businesses">
	</div>
	    	</div></div></div></div></div>
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
		
	function getAllBusinesses(){
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "DBDataAllBusinesses"},
	        success: function(businesses) {

				if (businesses.length > 0) {
					for (var i = 0; i < businesses.length; i++) {
						
						var business = '<div id=businesse'+businesses[i].m_Id +'class="businesse bg" style="background:rgba(0, 0, 0, 0.8); padding-right:20px;" <%if(whoAmI!= null && whoAmI == "PR") { %>onClick="goToBusinessProfile('+businesses[i].m_Id +');" <% } %> >' +
						'<div  style="visibility: hidden" >'+ businesses[i].m_Id + '</div>'+
						'<div class = "businesse-name">'+ businesses[i].m_Name + ', ' + businesses[i].m_BusinessTypeId.Name + ', ' + businesses[i].m_AreaId.Name	+ '</div>'+
						'<div class = "businesse-description">'	+ businesses[i].m_CityId.Name + '</div>'+
					/*	'<div class = "businesse-photo">' + businesses[i].m_Photo + '</div>'+ */
						'</div>';
						
						$('.all-businesses').append($(business));
									
					}
					
				}
				else {
					$(businesses).append('<h1>אין מקומות בילוי זמינים</h1>');
				}	        	
	        	
	        },
	        error: function(data){
	            	console.log("error");}
	 	});
	}
	
	$(function onLoad(){
		getAllBusinesses();
	});
	
	function goToBusinessProfile(businessId)
	{
		sessionStorage.setItem("businessId", businessId);
		 window.location.href = "BusinessProfile.jsp";
		
	}
	
	
</script>
</body>
</html>
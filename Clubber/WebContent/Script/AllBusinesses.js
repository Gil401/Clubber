function getAllBusinesses(){
	    $.ajax({
	        url: "GetDBData",
	        type: "post",
	        dataType: 'json',
	        data:{RequestType: "DBDataAllBusinesses"},
	        success: function(businesses) {

				if (businesses.length > 0) {
					for (var i = 0; i < businesses.length; i++) {
						
						var photo = "";
						if (businesses[i].m_Photo) {
							photo = '<img src="'+businesses[i].m_Photo+'" id="pic" style="max-width:100px; max-height:100px; float:right;margin-left:30px">';
						}
						
						var business = '<div id="businesse'+businesses[i].m_Id +'" class="businesses bg">' +
						'<div class = "businesse-photo">' + photo + '</div>'+
						'<div class = "businesse-name">'+'שם: '+ businesses[i].m_Name + '</div>'+
						'<div class = "businesse-type">' +'סוג: '+ businesses[i].m_BusinessTypeId.Name + '</div>'+
						'<div class = "businesse-range-ages">' +'איזור: '+ businesses[i].m_AreaId.Name	+ '</div>'+
						'<div class = "businesse-description">'	+'עיר: '+ businesses[i].m_CityId.Name + '</div>'+
						'<div class = "businesse-button"><button onClick="goToBusinessProfile('+businesses[i].m_Id +');" style="border-radius: 20px; font-size:13px;float:left">ערוך בית עסק</button></div>'+
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
	
	$(function() {
		getAllBusinesses();
	});
	
	function goToBusinessProfile(businessId)
	{
		sessionStorage.setItem("businessId", businessId);
		window.location.href = "BusinessProfile.jsp";
	}
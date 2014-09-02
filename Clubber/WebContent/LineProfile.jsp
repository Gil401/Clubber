<%@page import="Utlis.SessionUtils"%>
<%@page import="Utlis.Constants"%>
<%@page import="ClubberLogic.PR"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@  include file="patternTemplates/header.tpl"%>

<div id="latest-events">
	<div class="container">
		<div class="event-inner">
			<div class="row">
				<div id="latest-event-content" class="col-sm-7 col-md-8">
<div class="bg">


					<div class="line-details">
						<form class="line-details-form" id="lineDetails"
							name="lineDetails" >
							
							<div id='EditLineData' style="display:none">
								
								<h2>פרטי הליין</h2>
								
								<label id="lineNameLable">שם הליין</label> 
								<input type="text" name="lineName" id="lineName" required >
								<br>
								
								<label id="linebusinessNameLable">מקום הליין</label> 
								<input type="text" name="businessId" id="businessId" required hidden> 
								<select type="text" name="businessName" id="businessName" required></select>
								<br>
								
								<label id="lineDayLabel">יום בשבוע</label> 
								<select type="text" name="Day" id="Day" required>
									<option value='1'>א</option>
									<option value='2'>ב</option>
									<option value='3'>ג</option>
									<option value='4'>ד</option>
									<option value='5'>ה</option>
									<option value='6'>ו</option>
									<option value='7'>ש</option>
								</select>
								<br>
								
								<label id="lineStartDateLabel">תאריך פתיחה של הליין</label> 
								<input type="text" name="startDate" id='startDate' readonly required>
								<br>
								
								<label id="lineEndDateLabel">תאריך סגירה של הליין</label> 
								<input type="text" name="endDate" id='endDate'  readonly required>
								<br>
								
								<label id="minAgeLabel">גיל מינימלי</label> 
								<input type="text" name="minAge" id="minAge" required > 
								<br>
								
								<label id="imgLabel">תמונה של הליין</label> 
								<br>
								
								<label id="lineDescriptionLabel">תיאור</label> 
								<textarea rows="5" cols="20" type="text" name="description" id="description" required ></textarea>
								<br>
								
								<label id="lineEtranceFeeLabel">כניסה בתשלום</label> 
								<input type="text" name="etranceFee" id="etranceFee"  > 
								<br>
								
								<label id="lineDJLabel">דיג'יי</label> 
								<input type="text" name="DJ" id="DJ" > 
								<br>
								
								
								<div style="height:120px"> 
						         	<label id="lineMusicStyleLabel"> סגנון מוזיקה </label>
						         	<div id="musicStyleEdt" class="music-style-area combobox-auction-input">
						        	</div>
								</div> 
								<br>
								
								<button id="updateLineDel" type="button" >שמור</button>							
							</div>
							
							<div id='viewLineData'>
								
								<Img src="" id="lineImg"> 
								<br>
								
								<label id="lineNameLbl" style="font-size:30px;"> </label>
								<br>
								
								<label id="linebusinessNameLable">מקום הליין</label> 
								<label  id="businessIdLbl"> </label>
								<br>
								
								<label id="lineDayLabel">יום בשבוע</label> 
								<label  id="DayLabel"> </label>
								<br>
								
								<label id="lineStartDateLabel">תאריך פתיחה של הליין</label> 
								<label  id="startDateLbl"> </label>
								<br>
								
								<label id="lineEndDateLabel">תאריך סגירה של הליין</label> 
								<label  id="endDateLbl"></label> 
								<br>
								
								<label id="minAgeLabel">גיל מינימלי</label> 
								<label  id="minAgeLbl" > </label>
								<br>
								
								<label id="lineDescriptionLabel">תיאור</label> 
								<label id="descriptionLbl"></label>
								<br>
								
								<label id="lineEtranceFeeLabel">כניסה בתשלום</label> 
								<label id="etranceFeeLbl" > </label>
								<br>
								
								<label id="lineDJLabel">דיג'יי</label> 
								<label id="DJLbl" > </label>
								<br>
								
								<label id="lineMusicStyleLabel">סגנון מוזיקה</label> 
								<div id= "musicStyleContainer" class="music-style-area">
								</div>
								<br>
								
								<label id="linePrsNameLabel">יחצנים הרשומים לליין</label> 
								<div  id="prsContainer" class="prs-name-area">
								</div>
								<br>	
								
								<button id="editLineDel" type="button">ערוך</button>	
							</div>

						</form>
						<br> 
					</div>
					<div class="recomendedLines">
					</div>
					</div></div>
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
<script type="text/javascript" src="Script/LineProfile.js"></script>
<script src="js/datepicker-he.js"></script>
</body>
</html>
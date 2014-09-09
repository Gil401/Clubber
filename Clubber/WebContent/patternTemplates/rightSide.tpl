<%@ page contentType="text/html; charset=UTF-8" %>
<div class="col-sm-5 col-md-4"> 
                        <div class="single-event">
                            <h2 class="heading">  <strong>חפש</strong> ליינים פועלים</h2>
                            בחר תאריכים: 
                           <div class="datepicker" ></div>
                           <br/>
                           <%if(whoAmI!= null && whoAmI == "PR") { %>
                        <input type="button" class="btn" style="float:left" value="יצירת ליין חדש" onclick="document.location = 'AddNewLine.jsp'">
                        <% }else { %>
                        <input type="button" class="btn" style="float:left" value="יצירת אירוע חדש" onclick="document.location = 'NewAuction.jsp'">
                        <% } %>
                        <br /><br />
                        </div>
                        <!-- <div class="single-event">
                            <h2 class="heading">listing <strong>Music</strong></h2>
                            <a href="https://soundcloud.com/tokyopoliceclub" class="sc-player">Matas tracks</a>
                        </div> -->
                        <% /* <div class="single-event photo-gallery">
                            <h2 class="heading"> גלריית <strong>תמונות</strong></h2>
                           <ul>
                                <li><a href="images/home/event1.jpg" data-gallery="prettyPhoto"><img src="images/home/gal1.jpg" alt=""></a></li>
                                <li><a href="images/home/event1.jpg" data-gallery="prettyPhoto"><img src="images/home/gal2.jpg" alt=""></a></li>
                                <li><a href="images/home/event1.jpg" data-gallery="prettyPhoto"><img src="images/home/gal3.jpg" alt=""></a></li>
                                <li><a href="images/home/event1.jpg" data-gallery="prettyPhoto"><img src="images/home/gal4.jpg" alt=""></a></li>
                                <li><a href="images/home/event1.jpg" data-gallery="prettyPhoto"><img src="images/home/gal5.jpg" alt=""></a></li>
                                <li><a href="images/home/event1.jpg" data-gallery="prettyPhoto"><img src="images/home/gal6.jpg" alt=""></a></li>
                                <li><a href="images/home/event1.jpg" data-gallery="prettyPhoto"><img src="images/home/gal3.jpg" alt=""></a></li>
                                <li><a href="images/home/event1.jpg" data-gallery="prettyPhoto"><img src="images/home/gal2.jpg" alt=""></a></li>
                                <li><a href="images/home/event1.jpg" data-gallery="prettyPhoto"><img src="images/home/gal1.jpg" alt=""></a></li>
                            </ul>    
                        </div> */ %>
                        
                    </div>
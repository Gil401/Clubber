	<%@ page contentType="text/html; charset=UTF-8" %>
<%@  include file="patternTemplates/header.tpl" %>
<style>

    .ui-datepicker-calendar{
    	width: 17em;
    }
    
    .ui-datepicker-calendar tbody {
    	 text-align: left;
    }
    
    .ui-datepicker {
        padding: 15px;
    }
        
    .ui-datepicker-next{
     float: left;
     padding: 10px;
    }
   </style>

    <div id="latest-events">
        <div class="container">
            <div class="event-inner">                
                <div class="row">                       
                    <div id="latest-event-content" class="col-sm-7 col-md-8"> 
                        <div class="bg">
                            <h2 class="heading"> אירועים <strong>קרובים</strong> </h2 >       
                            <div id="temp_container"></div>                    
                            <div id="lines_container"></div>
                                                       
                             <div class="pager">
                                <ul class="pagination">
                                    <li><a href="#"><i class="fa fa-angle-left"></i></a></li>
                                    <li><a href="#" class="active" onclick="page(1)">1</a></li>
                                    <li><a href="#" onclick="page(2)">2</a></li>
                                    <li><a href="#">3</a></li>
                                    <li><a href="#">4</a></li>
                                    <li><a href="#">5</a></li>
                                    <li><a href="#">6</a></li>
                                    <li><a href="#">7</a></li>
                                    <li><a href="#">8</a></li>
                                    <li><a href="#">9</a></li>
                                    <li><a href="#">10</a></li>
                                    <li><a href="#">11</a></li>
                                    <li><a href="#">12</a></li>
                                    <li><a href="#"><i class="fa fa-angle-right"></i></a></li>
                                </ul>
                            </div> 
                        </div>                                       
                    </div> 
                    <%@  include file="patternTemplates/rightSide.tpl" %>
                </div>                          
            </div>
        </div>
    </div>

    <%@  include file="patternTemplates/footer.tpl" %>
	
	<input type="hidden" value="1" id="page_number">
	<script type="text/javascript" src="Script/Utils.js"></script>
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
    <script type="text/javascript" src="Script/welcome.js"></script>
    <script src="js/datepicker-he.js"></script>
    
</body>
</html>
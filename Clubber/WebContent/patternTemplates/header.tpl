<%@ page contentType="text/html; charset=UTF-8" %>
<%@page  import="java.util.*" %>


<!DOCTYPE html> 
<head> 
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Clubber</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">    
    <link href="css/prettyPhoto.css" rel="stylesheet"> 
    <link href="css/sc-player-standard.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <link href="css/responsive.css" rel="stylesheet">
    <link href="css/presets/preset1.css" id="preset" rel="stylesheet" type="text/css">
    <link href="css/switcher.css" rel="stylesheet" type="text/css">

    <!--[if lt IE 9]>
	    <script src="js/html5shiv.js"></script>
	    <script src="js/respond.min.js"></script> 
    <![endif]-->       
    <link rel="shortcut icon" href="images/ico/favicon.html">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.html">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.html">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.html">
    <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.html">
    <style>
    #ui-datepicker-div{
        background: #000;
        padding: 10px;
    }
    label{
    	width: 200px;
    }
    </style>
</head><!--/head-->
<body>
	<header id="navigation">      
        <div class="navbar" role="banner">
        	<div class="container">
                <div class="row">
                    <div class="col-sm-9">
                    
                    <% 
                    
                    String whoAmI = "none";
                    
                    Integer userID = (Integer)session.getAttribute("userID"); 
                    String name = (String)session.getAttribute("firstName"); 
                    String img = (String)session.getAttribute("User_Image");
                    if(session.getAttribute("whoAmI") != null)
                    	whoAmI = session.getAttribute("whoAmI").toString();
                    
                    %>
                    <input type="hidden" value="<% out.print(userID);%>" id="session_user_id">
                    
                    <% 	if(name != null) {   %>  
	                    <div style="background-color: #fff; width: 190px; color: #000; padding: 5px; margin-top: 10px; float: left">
	                            ברוך הבא <% out.println(name);%>
	                            <img src="<% out.println(img);%>"  style=" float: left; padding: 0 15px  0 0; max-width:49%; max-height:20%;">
	                            <br />
	                            <a href="<%if(whoAmI == "PR") {out.print ("PrProfile.jsp");} else { out.print("ClientProfile.jsp"); } %>" style="font-size: 12px;">פרופיל</a>
	                            <font style="padding-top:2px"> | </font> 
	                            <a href="Logout" style="font-size: 12px;">התנתק</a>  
						</div>
                      <% } %>
                       
                       
                        <nav class="navbar-right collapse navbar-collapse">
                            <ul class="nav navbar-nav">  
                            
                            <%	if(name == null) {   %>                                                                 
                                <li><a href="Login.jsp">התחבר</a></li>
                                <li><a href="SignUp.jsp">הרשמה</a></li>
                            <% } %>
                            <% if (whoAmI == "Client"){ %>
                            <li><a href="ClientProfile.jsp">פרופיל לקוח</a></li>
                            <li><a href="MyAuctions.jsp">המכרזים שלי</a></li>
                            <% } 
								if(whoAmI == "PR") { %>
                            <li><a href="MyOffers.jsp">ההצעות שלי</a></li>
                            <li><a href="MyLines.jsp">הליינים שלי</a></li>
                            <li><a href="SearchAuctions.jsp">חפש מכרזים</a></li>
                            <% } %>
                            
                                 
                                <li><a href="index.jsp">ראשי</a></li>
                            </ul>
							</div>
							
                        </nav>
                         
                       
                    
                    
                    
                    <div class="col-sm-3">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                            <a class="navbar-brand" href="index.jsp"><h1><strong>Clubber</strong></h1></a>
                        </div>
                    </div>
                </div> 
            </div>
            
            
        </div>
    </header> <!--/#navigation-->
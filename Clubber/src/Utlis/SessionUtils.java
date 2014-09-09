package Utlis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ClubberLogic.UserType;

public class SessionUtils {
	
	
    public static void setCurrentAuctionToDisplay(HttpSession request, Integer value)
    {
         request.setAttribute(Constants.AUCTION_TO_DISPLAY_ID,value);
    }
	
    public static void setCurrentOfferToDisplay(HttpSession request, Integer value)
    {
         request.setAttribute(Constants.OFFER_TO_DISPLAY_ID,value);
    }
    
    public static Integer getCurrentOfferToDisplay(HttpSession request)
    {
    	Object sessionAttribute =request.getAttribute(Constants.OFFER_TO_DISPLAY_ID);
    	return ( (sessionAttribute != null) ? (Integer)sessionAttribute : null );
    }
    
    public static Integer getCurrentAuctionToDisplay(HttpSession request)
    {
    	Object sessionAttribute= request.getAttribute(Constants.AUCTION_TO_DISPLAY_ID);
    	return ( (sessionAttribute != null) ? (Integer)sessionAttribute : null );
    }
    
    public static String getUserEmail (HttpServletRequest request) {
        Object sessionAttribute = request.getSession().getAttribute(Constants.EMAIL);
        return sessionAttribute != null ? sessionAttribute.toString() : null;
    }
       
    public static Integer getLoggedOnUserID (HttpSession request) {
        Object sessionAttribute = request.getAttribute("userID");
        return sessionAttribute != null ? (Integer)sessionAttribute : null;
    }
    public static void setUserIdToDisplay(HttpSession request, Integer value)
    {
         request.setAttribute(Constants.USER_TO_DISPLAY_ID,value);
    }
    
    public static Integer getUserIdToDisplay(HttpSession request)
    {
    	Object sessionAttribute =request.getAttribute(Constants.USER_TO_DISPLAY_ID);
    	return ( (sessionAttribute != null) ? (Integer)sessionAttribute : null );
    }
    
    public static void setUserIdToRetrieve(HttpSession request, Integer value)
    {
         request.setAttribute(Constants.USER_TO_RETRIVE,value);
    }
    
    public static Integer getUserIdToRetrieve(HttpSession request)
    {
    	Object sessionAttribute =request.getAttribute(Constants.USER_TO_RETRIVE);
    	return ( (sessionAttribute != null) ? (Integer)sessionAttribute : null );
    }
    
    public static UserType getWhoAmI(HttpSession request)
    {
    	Object sessionAttribute =request.getAttribute(Constants.WHO_AM_I);
    	return ( (sessionAttribute != null) ? (UserType)sessionAttribute : null );
    }

}

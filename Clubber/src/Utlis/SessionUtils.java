package Utlis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    
    public static void setUserIdToDisplay(HttpSession request, Integer value)
    {
         request.setAttribute(Constants.USER_TO_DISPLAY_ID,value);
    }
    
    public static Integer getUserIdToDisplay(HttpSession request)
    {
    	Object sessionAttribute =request.getAttribute(Constants.USER_TO_DISPLAY_ID);
    	return ( (sessionAttribute != null) ? (Integer)sessionAttribute : null );
    }

}

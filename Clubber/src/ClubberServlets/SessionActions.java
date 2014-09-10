package ClubberServlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mysql.jdbc.Constants;

import ClubberLogic.DAL;
import ClubberLogic.UserType;
import Utlis.*;
/**
 * Servlet implementation class SessionActions
 */
@WebServlet("/SessionActions")
public class SessionActions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionActions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setContentType("text/html; charset=UTF-8");
			
		String requestType= request.getParameter("RequestType");
		PrintWriter out = response.getWriter();
		String json=null;
        try 
        {
            
        	Gson gson = new Gson();

            if (requestType.equals(Utlis.Constants.SESSION_ACTIONS_SET_USER_IN_SESSION))
            {
            	Integer userId= Integer.parseInt(request.getParameter("UserId"));
            	
            	SessionUtils.setUserIdToDisplay(request.getSession(), userId);
            }
            
            else if (requestType.equals(Utlis.Constants.SESSION_ACTIONS_GET_USER_DATA))
            {
            	Integer userId= Integer.parseInt(request.getParameter("UserId"));
            	
            	SessionUtils.setUserIdToRetrieve(request.getSession(), userId);
            }
            else if (requestType.equals(Utlis.Constants.SESSION_ACTIONS_SET_CURR_LINE))
            {
            	Integer lineId= Integer.parseInt(request.getParameter("lineId"));
            	
            	SessionUtils.setCurrentLineToDisplay(request.getSession(), lineId);
            	
            }
            else if (requestType.equals(Utlis.Constants.SESSION_ACTIONS_GET_WHO_AM_I))
            {
            	UserType type = SessionUtils.getWhoAmI(request.getSession());
            	json = gson.toJson(type);
            }
            else if (requestType.equals(Utlis.Constants.SESSION_ACTIONS_GET_LOGGED_ON_USER_ID))
            {
            	Integer loggedOnUser = SessionUtils.getLoggedOnUserID(request.getSession());
            	json = gson.toJson(loggedOnUser);
            }
            
            System.out.println(json);
            out.print(json);
            out.flush();
        } 
        catch (Exception e) {
			e.printStackTrace();
		}
        finally 
        {
            out.close();
        }
	}

}
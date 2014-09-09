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
import Utlis.*;
/**
 * Servlet implementation class AuctionManagementActions
 */
@WebServlet("/AuctionManagementActions")
public class AuctionManagementActions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuctionManagementActions() {
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

            if (requestType.equals(Utlis.Constants.AUCTION_MANAGEMENT_OFFER_NOT_RELEVANT))
            {
            	Integer offerId= Integer.parseInt(request.getParameter("OfferId"));
            	
            	Boolean res= DAL.updateOfferStatus(offerId, OfferStatusIds.NotRelevant.getValue());
            	json = gson.toJson(res);
            }
            
            else if(requestType.equals(Utlis.Constants.AUCTION_MANAGEMENT_AUCTION_NOT_RELEVANT))
            {
            	Integer auctionId= Integer.parseInt(request.getParameter("AuctionId"));
            	
            	Boolean res= DAL.updateAuctionStatus(auctionId, AuctionStatusIds.NotRelevant.getValue());
            	json = gson.toJson(res);
            }
            else if(requestType.equals(Utlis.Constants.AUCTION_MANAGEMENT_ACTIVATE_AUCTION))
            {
            	Integer auctionId= Integer.parseInt(request.getParameter("AuctionId"));
            	Boolean res= DAL.updateAuctionStatus(auctionId, AuctionStatusIds.Active.getValue());
            	
            	json = gson.toJson(res);
            }
            else if(requestType.equals(Utlis.Constants.AUCTION_MANAGEMENT_OFFER_ACCEPTED))
            {
            	Integer offerId= Integer.parseInt(request.getParameter("OfferId"));
            	Integer displayCode= Integer.parseInt(request.getParameter("DisplayCode"));
            	Integer auctionId= Integer.parseInt(request.getParameter("AcceptedAuctionID"));
            	Boolean res= DAL.updateAuctionStatus(auctionId, AuctionStatusIds.InActive.getValue());
            	res= res && DAL.updateOfferStatus(offerId, OfferStatusIds.Accepted.getValue());
            	res=res && (DAL.updateUserDetailsCode(displayCode,auctionId));
            	
            	json = gson.toJson(res);
            }
            
            else if(requestType.equals(Utlis.Constants.AUCTION_MANAGEMENT_EXPOSE_USER_DETAILS))
            {
            	Integer displayCode= Integer.parseInt(request.getParameter("DisplayCode"));
            	Integer auctionId= Integer.parseInt(request.getParameter("AcceptedAuctionID"));
            	Boolean res=(DAL.updateUserDetailsCode(displayCode,auctionId));
            	
            	json = gson.toJson(res);
            }
            
            System.out.println(json);
            out.print(json);
		
        } 
        catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally 
        {
            out.close();
        }
	}

}

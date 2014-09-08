package ClubberServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ClubberLogic.AuctionData;
import ClubberLogic.BusinessData;
import ClubberLogic.Client;
import ClubberLogic.DAL;
import ClubberLogic.LineData;
import ClubberLogic.OfferData;
import ClubberLogic.PR;
import ClubberLogic.UserMessagesData;
import ClubberLogic.UserReviews;
import Utlis.Constants;
import Utlis.IdWithName;
import Utlis.LineManagementData;
import Utlis.SessionUtils;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetDBData
 */
@WebServlet("/GetDBData")
public class GetDBData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws SQLException 
     * @see HttpServlet#HttpServlet()
     */
    public GetDBData() throws SQLException {
        super();
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
		//get auction,offer ids:
		
		Integer auctionId= SessionUtils.getCurrentAuctionToDisplay(request.getSession());
		Integer offerID= SessionUtils.getCurrentOfferToDisplay(request.getSession());
		String userEmail= SessionUtils.getUserEmail(request);
		Integer userIdToDisplay = SessionUtils.getUserIdToDisplay(request.getSession());
		ArrayList<AuctionData> auctionsList = new ArrayList<>();
		
		ArrayList<IdWithName> musicStyleList = new ArrayList<>();
		ArrayList<UserMessagesData> joinLineRequestList = new ArrayList<>();
		
		//all returns data types:
		Object data;

		String requestType= request.getParameter("RequestType");
		PrintWriter out = response.getWriter();
		String json=null;
        try 
        {
            
        	Gson gson = new Gson();

            if (requestType.equals(Constants.DB_DATA_NEW_AUCTION))
            {
            	data= DAL.getAllNewAuctionData();
            	json = gson.toJson(data);
            }
            else if (requestType.equals(Constants.DB_DATA_MY_AUCTIONS))
            {
            	data= DAL.getAllMyAuctionsData();
            	json = gson.toJson(data);
            }
            else if (requestType.equals(Constants.DB_DATA_AUCTION_MANAGEMENT))
            {
            	data= DAL.getAllAuctionOffersData(auctionId);
            	json = gson.toJson(data);
            }
            else if (requestType.equals(Constants.DB_DATA_OFFER_REVIEW))
            {
            	data=DAL.getReviewedOfferData(offerID, auctionId);
            	json = gson.toJson(data);
            }
            else if (requestType.equals(Constants.DB_DATA_MESSAGES))
            {
            	data=DAL.getAllMassages(auctionId);
            	json = gson.toJson(data);
            }
            else if (requestType.equals(Constants.DB_DATA_ADD_MESSAGE))
            {
            	String description= request.getParameter(Constants.OUTGOING_MESSAGE_DESCRIPITON);
            	data= DAL.addNewMessage(description, auctionId);
            	json = gson.toJson(data);
            }
            else if (requestType.equals(Constants.DB_DATA_MAIN_LINES))
            {
            	data=DAL.getWelcomeScreenEvents(request.getParameter("InDate"));
            	json = gson.toJson(data);
       		}
            else if (requestType.equals(Constants.DB_DATA_PR_LINES))
            {
            	data=DAL.getLineByPR(request.getParameter("userID"));
            	json = gson.toJson(data);
       		} 
            else if(requestType.equals(Constants.DB_DATA_USER_PROFILE))
            {
            	String email = userEmail;
            	if (userIdToDisplay != null) {
            		email = DAL.getUserEmailByID(userIdToDisplay);
            	}
            	
            	PR pr= DAL.getUserProfileData(email);
            	pr.setIsUserEditalble(userEmail != null && userEmail.equals(email)); // Only the user can edit himself
            	json = gson.toJson(pr);
            }
            else if(requestType.equals(Constants.DB_DATA_USER_DATA_RETRIVE))
            {
            	String email = DAL.getUserEmailByID(userIdToDisplay);
            	Client client= DAL.getClientData(email);
            	json = gson.toJson(client);
            }
            
            else if(requestType.equals(Constants.DB_DATA_PR_PROFILE_REVIEW)){
            	UserReviews reviews = DAL.getPrProfileReview(userEmail);
            	json = gson.toJson(reviews);
            }
            else if(requestType.equals(Constants.DB_DATA_CLIENT_PROFILE_REVIEW)){
            	UserReviews reviews = DAL.getClientProfileReview(userEmail);
            	json = gson.toJson(reviews);
            	
            }
            else if(requestType.equals(Constants.DB_DATA_RECOMENDED_LINES)){
            	List<LineManagementData> recomendedLines = DAL.getCustomerRecomendedLines(userEmail);
            	json = gson.toJson(recomendedLines);
            }
            else if(requestType.equals(Constants.DB_DATA_ALL_BUSINESSES)){
            	ArrayList<BusinessData> businesses = DAL.getAllBusinesses();
            	json = gson.toJson(businesses);
            }
            else if(requestType.equals(Constants.DB_DATA_GET_BUSINESS_DATA)){
            	int businessId = Integer.parseInt(request.getParameter("businessId"));
            	BusinessData businessData = DAL.getBusinessData(businessId);
            	json = gson.toJson(businessData);
            }
            else if(requestType.equals(Constants.DB_DATA_GET_BUSINESS_AREAS_DATA))
            {
            	ArrayList<IdWithName> areasList = DAL.getBusinessAreasData();
            	json = gson.toJson(areasList);            	
            }
            else if(requestType.equals(Constants.DB_DATA_PR_MY_LINES_SERACH))
            {
            	data= DAL.getOffersAndAuctions(request.getParameter("userID"), request.getParameter("lineID"), request.getParameter("date"), request.getParameter("status"));
            	json = gson.toJson(data);            	
            }
            else if(requestType.equals(Constants.DB_DATA_GET_BUSINESS_CITIES_DATA)){
            	int areaId = Integer.parseInt(request.getParameter("areaId"));
            	ArrayList<IdWithName> citiesList = DAL.getBusinessCitiesData(areaId);
            	json = gson.toJson(citiesList);            	
            }
            else if(requestType.equals(Constants.DB_DATA_GET_BUSINESSES_TYPE_DATA))
            {
            	ArrayList<IdWithName> typesList = DAL.getBusinessesTypeData();
            	json = gson.toJson(typesList);            	
            }         
            else if(requestType.equals(Constants.SEARCH_BY_MY_LINES))
            {
            	auctionsList =  DAL.getAuctionsByPrLines(userEmail);
            	json = gson.toJson(auctionsList);
            }
            else if(requestType.equals(Constants.DB_DATA_ALL_AUCTIONS))
            {
            	auctionsList =  DAL.getAllAuctions();
            	json = gson.toJson(auctionsList);
            }
            else if(requestType.equals(Constants.DB_DATA_AUCTION_BY_ID))
            {
            	data = DAL.getAuctionById(request.getParameter("AuctionId"));
            	json = gson.toJson(auctionsList);
            }
            else if(requestType.equals(Constants.DB_DATA_GET_MUSIC_STYLE_DATA))
            {
            	musicStyleList = DAL.getMusicStyleData();
            	json = gson.toJson(musicStyleList);
            	
            }
            else if(requestType.equals(Constants.DB_DATA_GET_JOIN_LINE_REQUEST_DATA))
            {
            	joinLineRequestList = DAL.getJoinLineRequestData(userEmail);
            	json = gson.toJson(joinLineRequestList);
            	
            }
            else if(requestType.equals(Constants.DB_DATA_NEW_OFFER))
            {
            	//ArrayList<OfferData> offerList = new ArrayList<>();
            	data = DAL.getAllNewOfferData("3");
            	json = gson.toJson(data);
            	
            }
            else if (requestType.equals(Constants.DB_DATA_LINE_PROFILE))
            {
            	Integer lineId= Integer.parseInt(request.getParameter("lineId").toString());
            	data= DAL.getLineProfileData(lineId);
            	json = gson.toJson(data);
            }
            else if(requestType.equals(Constants.DB_DATA_AUCTION_REVIEW))
            {
            	String sessionAttribute= request.getParameter(Constants.CURR_AUCTION_ID);
            	Integer reviewedAuc= ( (sessionAttribute != null) ? Integer.parseInt(sessionAttribute) : null );
            	data = DAL.getReviewedAuctionData(reviewedAuc);
            	json = gson.toJson(data);
            }
            else if (requestType.equals(Constants.DB_DATA_BUSINESS_LST))
            {
            	data = DAL.getBusinessesNameAndID();
            	json = gson.toJson(data);
            }
            else if (requestType.equals(Constants.DB_DATA_OFFER_PER_AUCION))
            {
            	String userID = request.getParameter("userID");
            	String lineID = request.getParameter("lineID");
            	String status = request.getParameter("status");
            	
            	data = DAL.getPROffersAndAuctions(userID, lineID, status);
            	json = gson.toJson(data);
            }
            else if (requestType.equals(Constants.DB_DATA_MY_PR_LINES))
            {
            	data= DAL.getMyLinesData(request.getParameter("userID").toString());
            	json = gson.toJson(data);
            }
            else if (requestType.equals(Constants.DB_DATA_FILTERED_AUCTIONS))
            {
            	Integer areaId = Integer.parseInt(request.getParameter(Constants.AUCTION_SEARCH_AREA_ID));
            	Integer eventType = Integer.parseInt(request.getParameter(Constants.AUCTION_SEARCH_EVENT_TYPE));
            	Integer guestsQuantity = Integer.parseInt(request.getParameter(Constants.AUCTION_SEARCH_GUESTS_QUANTITY));
            	
            	data= DAL.getAuctionFilteredByEventType(eventType,guestsQuantity,areaId );
            	json = gson.toJson(data);
            }
            else if (requestType.equals(Constants.DB_DATA_GET_OFFER_STATUS))
            {
            	data= DAL.getOfferStatus();
            	json = gson.toJson(data);
            }
            
            
            System.out.println(json);
            out.print(json);
            out.flush();    
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

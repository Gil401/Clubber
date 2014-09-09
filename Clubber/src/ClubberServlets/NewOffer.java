package ClubberServlets;

import Utlis.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ClubberLogic.AuctionData;
import ClubberLogic.DAL;
import ClubberLogic.OfferData;

/**
 * Servlet implementation class NewAuction
 */
@WebServlet("/NewOffer")
public class NewOffer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws SQLException 
     * @see HttpServlet#HttpServlet()
     */
    public NewOffer() throws SQLException {
        super();
        // TODO Auto-generated constructor stub
        //DAL.DoLogin("sad", "password");
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
        request.setCharacterEncoding("UTF-8");
        
		PrintWriter out = response.getWriter();
		OfferData offer= new OfferData();
		try
		{
			addAlTreats(request, offer);
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date = df.parse(request.getParameter(Constants.END_DATE_OFFER_EDT));
			offer.setExpirationDate(date.getTime());
			
			offer.setLineId(new IdWithName(Integer.parseInt(request.getParameter(Constants.LINE_NAME_OFFER_EDT)),null));
			offer.setDescription(request.getParameter(Constants.DESCRIPTION_OFFER__EDT));
			offer.setPrId(new IdWithName(SessionUtils.getLoggedOnUserID(request.getSession()), null));
			offer.setMaxArrivalHourAsLong(Long.parseLong(request.getParameter(Constants.MAX_ARRIVAL_OFFER_EDT)));
			offer.setOfferStatusId(new IdWithName(OfferStatusIds.Pending.getValue(), null));
			offer.setAuctionId(SessionUtils.getCurrentAuctionToDisplay(request.getSession()));
			Integer offerId = DAL.addNewOffer(offer);
			SessionUtils.setCurrentOfferToDisplay(request.getSession(), offerId);
			System.out.println(true);
            out.print(true);
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
	
	protected void addAlTreats(HttpServletRequest request, OfferData offer)
	{
		String treates[]= request.getParameter(Constants.TREATS_OFFER_EDT).split("&");
		if (treates.length > 0 && !treates[0].equals("")) {
			for (String item : treates) {
				offer.getOfferTreats().add(new IdWithName(Integer.parseInt(item), null));
			}
		}
	}
}

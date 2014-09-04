package ClubberServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ClubberLogic.DAL;
import ClubberLogic.LineData;
import ClubberLogic.OfferData;
import Utlis.Constants;
import Utlis.IdWithName;

/**
 * Servlet implementation class UpdateOfferDetails
 */
@WebServlet("/UpdateOfferDetails")
public class UpdateOfferDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateOfferDetails() {
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
        request.setCharacterEncoding("UTF-8");
        
		PrintWriter out = response.getWriter();
		OfferData offer= new OfferData();
		try
		{
			addAlTreats(request, offer);
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date= df.parse(request.getParameter(Constants.END_DATE_OFFER_EDT));
			offer.setExpirationDate(date.getTime());
			
			offer.setLineId(new IdWithName(Integer.parseInt(request.getParameter(Constants.LINE_NAME_OFFER_EDT)),null));
			offer.setDescription(request.getParameter(Constants.DESCRIPTION_OFFER__EDT));
			offer.setId(Integer.parseInt(request.getParameter(Constants.ID_OFFER__EDT)));
			
			offer.setMaxArrivalHourAsLong(Long.parseLong(request.getParameter(Constants.MAX_ARRIVAL_OFFER_EDT)));
			
			DAL.updateOfferDetails(offer);
			
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

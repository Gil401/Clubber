package ClubberServlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import ClubberLogic.LineData;
import Utlis.Constants;
import Utlis.IdWithName;

/**
 * Servlet implementation class UpdateLineDetails
 */
@WebServlet("/UpdateLineDetails")
public class UpdateLineDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateLineDetails() {
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
		LineData line= new LineData();
		try
		{
			line.setBusiness(new IdWithName(Integer.parseInt(request.getParameter(Constants.BUSINESSS_ID_EDT)),null));
			
			addAllMusicStyles(request, line);
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date= df.parse(request.getParameter(Constants.START_DATE_EDT));
			line.setStartDate(date.getTime());
			
			date= df.parse(request.getParameter(Constants.END_DATE_EDT));
			line.setEndDate(date.getTime());
			
			line.setM_LineName(request.getParameter(Constants.LINE_NAME_EDT));
			
			line.setMinAge(Integer.parseInt(request.getParameter(Constants.MIN_AGE_EDT)));
			
			line.setDescription(request.getParameter(Constants.DESCRIPTION_EDT));
			
			line.setEntranceFee(request.getParameter(Constants.ENTRANCE_EDT));
			
			line.setDj(request.getParameter(Constants.DJ_EDT));
			
			line.setId(Integer.parseInt(request.getParameter(Constants.ID_EDT)));
			
			line.setM_DayInWeek(Integer.parseInt(request.getParameter(Constants.DAY_EDT)));
			
			DAL.updateLineDetails(line);
			
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
	
	protected void addAllMusicStyles(HttpServletRequest request, LineData line)
	{
		String musicStyles[]= request.getParameter(Constants.MUSIC_STYLE_LIST).split("&");
		if (musicStyles.length > 0 && !musicStyles[0].equals("")) {
			for (String item : musicStyles) {
				line.getMusicStylesIds().add(new IdWithName(Integer.parseInt(item), null));
			}
		}
	}
	 
}

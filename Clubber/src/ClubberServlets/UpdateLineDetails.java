package ClubberServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ClubberLogic.AuctionData;
import ClubberLogic.Client;
import ClubberLogic.DAL;
import ClubberLogic.LineData;
import ClubberLogic.UserData;
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
		
		request.setCharacterEncoding("UTF-8");

		String path = getServletContext().getRealPath(
				Constants.IMAGES_DIR + Constants.LINE_IMAGES_DIR);

		UploadLineImageServlet uploadServlet = new UploadLineImageServlet();
		LineData lineData = new LineData();

		uploadServlet.upload(request, path, Constants.IMAGES_DIR
				+ Constants.LINE_IMAGES_DIR, lineData);
		String message = "";

		boolean isSucceed = true;

		try
		{
			isSucceed = DAL.updateLineDetails(lineData);
            System.out.println(true);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isSucceed = false;
		}
		
		if (isSucceed == true) {
			message = "העדכון בוצע";
		} else {
			message = "העדכון נכשל";
		}

		request.setAttribute(Constants.MESSAGE_TEXT, message);
		getServletContext().getRequestDispatcher("/LineProfile.jsp").forward(request, response);

	}
}

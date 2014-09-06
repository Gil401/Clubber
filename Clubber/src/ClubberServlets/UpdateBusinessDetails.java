package ClubberServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ClubberLogic.BusinessData;
import ClubberLogic.DAL;
import Utlis.Constants;

/**
 * Servlet implementation class UpdateBusinessDetails
 */
@WebServlet("/UpdateBusinessDetails")
public class UpdateBusinessDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateBusinessDetails() {
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
		// TODO Auto-generated method stub
	
		request.setCharacterEncoding("UTF-8");

		String path = getServletContext().getRealPath(
				Constants.IMAGES_DIR + Constants.BUSSINESS_IMAGES_DIR);

		UploadBusinessImageServlet uploadServlet = new UploadBusinessImageServlet();
		BusinessData businessData = new BusinessData();

		uploadServlet.upload(request, path, Constants.IMAGES_DIR
				+ Constants.BUSSINESS_IMAGES_DIR, businessData);

		String message = "";

		boolean isSucceed = true;
		
		isSucceed = DAL.updateBusinessDetails(businessData);
		System.out.println(true);
		
		if (isSucceed == true) {
			message = "העדכון בוצע";
		} else {
			message = "העדכון נכשל";
		}

		request.setAttribute(Constants.MESSAGE_TEXT, message);
		getServletContext().getRequestDispatcher("/BusinessProfile.jsp").forward(request, response);
	}

}

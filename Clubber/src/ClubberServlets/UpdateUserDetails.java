package ClubberServlets;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ClubberLogic.Client;
import ClubberLogic.DAL;
import ClubberLogic.UserData;
import ClubberLogic.UserType;
import Utlis.Constants;

/**
 * Servlet implementation class UpdateUserDetails
 */
@WebServlet("/UpdateUserDetails")
public class UpdateUserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateUserDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		String path = getServletContext().getRealPath(
				Constants.IMAGES_DIR + Constants.USER_IMAGES_DIR);

		UploadServlet uploadServlet = new UploadServlet();
		UserData client = new Client();

		uploadServlet.upload(request, path, Constants.IMAGES_DIR
				+ Constants.USER_IMAGES_DIR, client);
		String message = "";

		boolean isSucceed = true;

		try {
			isSucceed = DAL.updateUserDetails(client);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isSucceed = false;
		}
		
		if (isSucceed == true) {
			request.getSession().setAttribute(Constants.FIRST_NAME, client.getFirstName());
			request.getSession().setAttribute(Constants.IMAGE, client.getImageURL());
			request.getSession().setAttribute("User", client);
			message = "העדכון בוצע";
		} else {
			message = "העדכון נכשל";
		}

		request.setAttribute(Constants.MESSAGE_TEXT, message);

		String userType = request.getSession().getAttribute(Constants.WHO_AM_I)
				.toString();

		if (userType == UserType.Client.toString())
			getServletContext().getRequestDispatcher("/ClientProfile.jsp")
					.forward(request, response);
		else
			getServletContext().getRequestDispatcher("/PrProfile.jsp").forward(
					request, response);
	}

}

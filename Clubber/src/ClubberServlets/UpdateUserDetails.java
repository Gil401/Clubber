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
        
        String path = getServletContext().getRealPath(Constants.IMAGES_DIR+Constants.USER_IMAGES_DIR);

        UploadServlet f = new UploadServlet();
        Client client = new Client();
        String temp2 = f.upload(request, path, Constants.IMAGES_DIR+Constants.USER_IMAGES_DIR, client);
        String message = "";
        
        boolean isSucceed = true;
        
		try {
			isSucceed = DAL.updateUserDetails(client);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isSucceed = false;
		}
        
        if(isSucceed == true)
        {
        	message = "העדכון בוצע";
        }
        else
        {
        	message = "העדכון נכשל";
        }
        
        request.setAttribute(Constants.MESSAGE_TEXT, message);
        
        UserType userType = client.getUserType();
        request.setAttribute("User", userType);
        
        if(userType == UserType.Client)
        	getServletContext().getRequestDispatcher("/ClientProfile.jsp").forward(request, response);
        else
        	getServletContext().getRequestDispatcher("/PrProfile.jsp").forward(request, response);
	}

}
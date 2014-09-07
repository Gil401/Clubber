package ClubberServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ClubberLogic.BusinessData;
import ClubberLogic.DAL;
import ClubberLogic.LineData;
import Utlis.Constants;
import Utlis.IdWithName;

/**
 * Servlet implementation class AddNewBusiness
 */
@WebServlet("/AddNewBusiness")
public class AddNewBusiness extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewBusiness() {
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
        
		isSucceed = DAL.addNewBusiness(businessData);
        
        if(isSucceed == true)
        {
        	message = "המקום התווסף בהצלחה";
        }
        else
        {
        	message = "הוספת המקום למערכת נכשלה";
        }
        
        request.setAttribute(Constants.MESSAGE_TEXT, message);
        getServletContext().getRequestDispatcher("/AddNewBusiness.jsp").forward(request, response);
		
		
	}

}

package ClubberServlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ClubberLogic.BusinessData;
import ClubberLogic.Client;
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
	
        response.setContentType("text/html;charset=UTF-8");
        String message = "";
        BusinessData businessData = new BusinessData();
        
        //businessData.setM_Id(Integer.parseInt(request.getParameter(Constants.BUSINESS_ID)));
        businessData.setM_Id(new Integer(2));
        businessData.setM_Name(request.getParameter(Constants.BUSINESS_NAME));
        //businessData.setM_BusinessTypeId(request.getParameter(Constants.BUSINESS_TYPE));
        //businessData.setM_AreaId(request.getParameter(Constants.BUSINESS_AREA));
        //businessData.setM_CityId(request.getParameter(Constants.BUSINESS_CITY));
        businessData.setM_HouseNumber(Integer.parseInt(request.getParameter(Constants.BUSINESS_HOME_NUMBER)));
        businessData.setM_PhoneNumber(request.getParameter(Constants.BUSINESS_PHONE_NUMBER));
        businessData.setM_Description(request.getParameter(Constants.BUSINESS_DESCRIPTION));
        //businessData.setM_Photo(request.getParameter(Constants.BUSINESS_PHOTO));
                        
        boolean isSucceed = true;
        
		isSucceed = DAL.updateBusinessDetails(businessData);
        
        if(isSucceed == true)
        {
        	message = "������ ����";
        }
        else
        {
        	message = "������ ����";
        }
        
        request.setAttribute(Constants.MESSAGE_TEXT, message);
        getServletContext().getRequestDispatcher("/BusinessProfile.jsp").forward(request, response);
		
		
	
	}

}

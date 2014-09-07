package ClubberServlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ClubberLogic.DAL;
import ClubberLogic.LineData;
import Utlis.Constants;
import Utlis.IdWithName;

/**
 * Servlet implementation class AddNewLine
 */
@WebServlet("/AddNewLine")
public class AddNewLine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewLine() {
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
				Constants.IMAGES_DIR + Constants.LINE_IMAGES_DIR);

		UploadLineImageServlet uploadServlet = new UploadLineImageServlet();
		LineData lineData = new LineData();

		uploadServlet.upload(request, path, Constants.IMAGES_DIR
				+ Constants.LINE_IMAGES_DIR, lineData);
		String message = "";

		boolean isSucceed = true;
        
        try {
			isSucceed = DAL.addNewLine(lineData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(isSucceed == true)
        {
        	message = "הליין התווסף בהצלחה";
        }
        else
        {
        	message = "הוספת הליין למערכת נכשלה";
        }
        
        request.setAttribute(Constants.MESSAGE_TEXT, message);
        getServletContext().getRequestDispatcher("/AddNewLine.jsp").forward(request, response);
		
		
	}
}

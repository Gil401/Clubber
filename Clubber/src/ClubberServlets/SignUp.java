package ClubberServlets;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ClubberLogic.Client;
import ClubberLogic.DAL;
import ClubberLogic.PR;
import ClubberLogic.UserType;
import Utlis.Constants;
import Utlis.GoogleMail;
import Utlis.PasswordGenarator;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
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
		
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        // Get User parameter
        String whoAmIParam = request.getParameter(Constants.WHO_AM_I);
        String firstNameParam = request.getParameter(Constants.FIRST_NAME);
        String lastNameParam = request.getParameter(Constants.LAST_NAME);
        String genderParam = request.getParameter(Constants.GENDER);
        
		DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
		Date date = new Date();

		try {
			String dateParam = request.getParameter(Constants.BIRTHDATE);
			date = df.parse(dateParam);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date birthdateParam = date;
                
        String phoneNumberParam = request.getParameter(Constants.PHONE_NUMBER);
        String emailParam = request.getParameter(Constants.EMAIL);
        String passwordParam = PasswordGenarator.create();
                
        boolean isSucceed = true; 
        UserType userType;
		String message = "נרשמת בהצלחה לאתר, אנו ממליצים לאחר ההתחברות לשנות את סיסמתך";	
        
        // Check if email unique
        if(DAL.isEmailUnique(emailParam) == false)
        {
        	isSucceed = false;
        	message = "כתובת המייל כבר קיימת במערכת";
        }        
        
        // Check if the user is client or PR
        if(isSucceed == true)
        {
	        if(whoAmIParam.equals("Client") == true)
	        {
	        	userType = UserType.Client;
	        	Client client = new Client(lastNameParam, firstNameParam, genderParam, phoneNumberParam, emailParam, birthdateParam, passwordParam);

	        	if( sentMail(firstNameParam, passwordParam, emailParam) == true)
	        	{
	        		try {
						isSucceed = DAL.insertNewUser(client, userType);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						isSucceed = false;
						message = "התגלתה תקלה בעת ההרשמה, אנא צור קשר clubber.cop@gmail.com";
					}
	        	}
	        	else
	        	{
	        		isSucceed = false;
	        		message = "ההרשמה נכשלה, המערכת נכשלה בשליחת מייל האימות";
	        	}
	        }
	        else if(whoAmIParam.equals("PR") == true)
	        {
	        	userType = UserType.PR;
	        	PR pr = new PR(lastNameParam, firstNameParam, genderParam, phoneNumberParam, emailParam, birthdateParam, passwordParam);

        		if(sentMail(firstNameParam, passwordParam, emailParam) == true)
        		{
        		 	try {
    					isSucceed = DAL.insertNewUser(pr, userType);
    				} catch (ParseException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    					isSucceed = false;
    					message = " clubber.cop@gmail.com :קרתה תקלה בהליך ההרשמה, אנה פנה ל ";
    				}
        		}
        		else
        		{
        			isSucceed = false;
        			message = "ההרשמה נכשלה, המערכת נכשלה בשליחת מייל האימות";
        		}
	        }
        }
        
        request.setAttribute(Constants.MESSAGE_TEXT, message);
        
        if(isSucceed == true)
        {
        	getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
        else
        {
        	getServletContext().getRequestDispatcher("/SignUp.jsp").forward(request, response);                                            
        }
	}

	
	public boolean sentMail(String name, String password, String email)
	{
		boolean isSucceed = true;
		String title = "Clubber - Registration to website";
		String message = "Hello " + name + "!"
				+ "\n\nYour password is : " + password 
				+ "\nWe recommended you to change your password for safety reasons. "
				+ "\n\nThanks Clubber Team";
				
		try {
			GoogleMail.Send(Constants.CLUBBER_USER_NAME, Constants.CLUBBER_USER_PASSWORD, email, title, message);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			isSucceed = false;
			e.printStackTrace();
		}
		
		return isSucceed;
	}
	
	
}


 
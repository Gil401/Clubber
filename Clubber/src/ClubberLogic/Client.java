package ClubberLogic;

import java.util.Date;


public class Client extends UserData{

	public Client(){
		
	}
	
	/*
	 * Maayan : 24/4/2014
	 * 			added this constructor
	 */
	public Client(String email, String password)
	{
		super(email, password);
	}
	
	/*
	 * Maayan : 24/4/2014
	 * 			* added set to filed password
	 * 			* type of gender changed
	 */		
	public Client(String lastName, String firstName, String gender, String phoneNumber, String email, long birthDate, String password, String url) 
    {
        super(lastName, firstName, gender, phoneNumber, email, birthDate, password, url);
    }
}

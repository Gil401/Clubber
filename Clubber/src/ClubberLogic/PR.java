package ClubberLogic;

import java.util.Date;


public class PR extends UserData{
	
	/*
	 * Maayan : 24/4/2014
	 * 			added this constructor
	 */
	
	public PR(){
	
	}
	
	public PR(String email, String password)
	{
		super(email, password);
	}
	
	/*
	 * Maayan : 24/4/2014
	 * 			added set to filed password
	 * 			* type of gender changed 
	 */	
	public PR(String lastName, String firstName, String gender, String phoneNumber, String email, long birthDate, String password, String url, boolean isUserEditable) 
    {
        super(lastName, firstName, gender, phoneNumber, email, birthDate, password, url, isUserEditable);
    }

}

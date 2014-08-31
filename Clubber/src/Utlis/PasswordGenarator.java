package Utlis;

import java.util.Random;

public class PasswordGenarator {
	

	public PasswordGenarator() {
		// TODO Auto-generated constructor stub
	}
	
	public static String create()
	{
		String password = "";
		Random random = new Random();
		
		for (int i = 1; i <= 8; i++){
				if(i % 2 == 0)
				{
					password += (char) (48 + random.nextInt(10));
					
				}
				else
				{
					password += (char) (97 + random.nextInt(25));
				}
		    }		
		
		return password;
	}
	

}

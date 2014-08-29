package ClubberLogic;

import java.util.Date;


/*
 * Maayan : 24/4/2014
 * 			* added filed 'password'
 * 			* field gender changed according to DB
 * 			* the set and get functions were private changed to protected
 */

public abstract class UserData {
	private Integer id;
	private String password;
	private String firstName;
	private String lastName;
	private Gender gender;
	private String phoneNumber;
	private String email;
	private long birthDate; 
	private String imageUrl;
	private UserType userType;
	
	
	public UserData(){
		
	}
	/*
	 * Maayan : 24/4/2014
	 * 			added this constructor 
	 */
	public UserData(String email, String password) {
	}
	
	/*
	 * Maayan : 24/4/2014
	 * 			added set to filed password
	 */
	
	public UserData(String lastName, String firstName, String gender, String phoneNumber, String email, long birthDate, String password, String url) 
    {
        this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setGender(gender);
		this.setPhoneNumber(phoneNumber);
		this.setEmail(email);
		this.setBirthDate(birthDate);	
		this.setPassword(password);
		this.imageUrl = url;
    }
	
	public void setId(Integer id) {
		this.id = id;
	}

	public long getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(long birthDate) {
		this.birthDate = birthDate;
	}
	
	public void setImageUrl(String url){
		this.imageUrl = url;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender.equals(Gender.Male.toString())? Gender.Male : Gender.Female;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getImageURL() {
		return imageUrl;
	}
	
	public int getUserId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType.equals(UserType.PR.toString())? UserType.PR : UserType.Client;
	}	
}

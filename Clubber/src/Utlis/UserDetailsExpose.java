package Utlis;

public enum UserDetailsExpose {
	Email(1),
	Phone(2),
	EmailAndPhone(3);
	
	private int value;
	
	private UserDetailsExpose(int value) {
		this.value = value;
  }
	
	public int getValue() {
		return value;
  }
	
	public static UserDetailsExpose forCode(int code) {
	    for (UserDetailsExpose item : UserDetailsExpose.values()) {
	        if (item.getValue() == code) {
	            return item;
	        }
	    }
	    return null;
	 }
}

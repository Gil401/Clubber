package Utlis;

public enum OfferStatusIds {
	Accepted(1,"התקבלה"),
	Pending(2,"ממתינה לאישור"),
	Denied(3, "נדחתה"),
	NotRelevant(4,"לא רלוונטית");
	
	private int value;
	private String displayName;
	
	private OfferStatusIds(int value, String displayName) {
		this.value = value;
		this.displayName= displayName;
  }
	
	public int getValue() {
		return value;
  }
	
	public String getDisplayName() {
		return displayName;
	}
}

package Utlis;

public enum AuctionStatusIds {
	Active(1,"פעיל"),
	InActive(2,"לא פעיל"),
	NotRelevant(3,"לא רלוונטי");
	
	private int value;
	private String displayName;
	private AuctionStatusIds(int value, String displayName) {
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

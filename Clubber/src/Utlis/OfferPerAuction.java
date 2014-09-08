package Utlis;

import ClubberLogic.AuctionData;
import ClubberLogic.OfferData;

public class OfferPerAuction {
	private AuctionData m_Auction;
	private OfferData m_OfferData;
	
	private String auctionId;
	private String minAge;
	private String Exceptions; 
	private String numOfGuests;
	private String eventType;
	private long eventDate;
	private boolean isFlexable; 
	private String area;
	private String bType;
	private String specPlace;
	private String desc;
	private String contacts;
	
	
	
	
	public OfferPerAuction() {
		m_Auction = new AuctionData();
		m_OfferData = new OfferData();
	}
	
	public AuctionData getM_Auction() {
		return m_Auction;
	}
	public void setM_Auction(AuctionData m_Auction) {
		this.m_Auction = m_Auction;
	}
	public OfferData getM_OfferData() {
		return m_OfferData;
	}
	public void setM_OfferData(OfferData m_OfferData) {
		this.m_OfferData = m_OfferData;
	}
	
	
}

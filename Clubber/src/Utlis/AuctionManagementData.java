package Utlis;

import java.util.List;

import ClubberLogic.AuctionData;
import ClubberLogic.OfferData;

public class AuctionManagementData {
	private List<OfferData> offers;
	private AuctionData currentAuciton;
	
	public AuctionManagementData(List<OfferData> allOffers , AuctionData auciton)
	{
		this.currentAuciton= auciton;
		this.offers= allOffers;
	}
}

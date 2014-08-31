package Utlis;

import java.util.LinkedList;
import java.util.List;

public class NewAuctionData {
	private List<IdWithName> eventTypes;
	private List<IdWithName> musicStyles;
	private List<IdWithName> area;
	private List<IdWithName> businessType;
	private List<IdWithName> certainBusiness;
	private List<IdWithName> sittsType;
	
	public NewAuctionData()
	{
		eventTypes= new LinkedList <IdWithName> (); 
		musicStyles= new LinkedList <IdWithName> (); 
		area= new LinkedList <IdWithName> (); 
		businessType= new LinkedList <IdWithName> (); 
		certainBusiness= new LinkedList <IdWithName> (); 
		sittsType= new LinkedList <IdWithName> (); 
		
	}
	
	public List<IdWithName> getEventTypes() {
		return eventTypes;
	}
	public void setEventTypes(List<IdWithName> eventTypes) {
		this.eventTypes = eventTypes;
	}
	public List<IdWithName> getMusicStyles() {
		return musicStyles;
	}
	public void setMusicStyles(List<IdWithName> musicStyles) {
		this.musicStyles = musicStyles;
	}
	public List<IdWithName> getArea() {
		return area;
	}
	public void setArea(List<IdWithName> area) {
		this.area = area;
	}
	public List<IdWithName> getBusinessType() {
		return businessType;
	}
	public void setBusinessType(List<IdWithName> businessType) {
		this.businessType = businessType;
	}
	public List<IdWithName> getCertainBusiness() {
		return certainBusiness;
	}
	public void setCertainBusiness(List<IdWithName> certainBusiness) {
		this.certainBusiness = certainBusiness;
	}
	public List<IdWithName> getSittsType() {
		return sittsType;
	}
	public void setSittsType(List<IdWithName> sittsType) {
		this.sittsType = sittsType;
	}
		

}

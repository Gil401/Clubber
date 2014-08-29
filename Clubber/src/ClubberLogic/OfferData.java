package ClubberLogic;

import java.io.File;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

import Utlis.IdWithName;


public class OfferData {
	private Integer id;
	private String description;
	private IdWithName lineId;
	private IdWithName prId;
	private long expirationDate;
	private long submitDate ;
	private Time maxArrivalHour;
	private File seatsPhoto;
	private Integer auctionId;
	private IdWithName  offerStatusId;
	private File linePhoto;
	private List<IdWithName> offerTreats;
	
	
	public OfferData()
	{
		offerTreats= new LinkedList<IdWithName>();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public IdWithName getLineId() {
		return lineId;
	}
	public void setLineId(IdWithName lineId) {
		this.lineId = lineId;
	}
	public long getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(long expirationDate) {
		this.expirationDate = expirationDate;
	}
	public Time getMaxArrivalHour() {
		return maxArrivalHour;
	}
	public void setMaxArrivalHour(Time maxArrivalHour) {
		this.maxArrivalHour = maxArrivalHour;
	}
	public File getSeatsPhoto() {
		return seatsPhoto;
	}
	public void setSeatsPhoto(File seatsPhoto) {
		this.seatsPhoto = seatsPhoto;
	}
	public Integer getAuctionId() {
		return auctionId;
	}
	public void setAuctionId(Integer auctionId) {
		this.auctionId = auctionId;
	}
	public IdWithName getOfferStatusId() {
		return offerStatusId;
	}
	public void setOfferStatusId(IdWithName offerStatusId) {
		this.offerStatusId = offerStatusId;
	}
	public File getLinePhoto() {
		return linePhoto;
	}
	public void setLinePhoto(File linePhoto) {
		this.linePhoto = linePhoto;
	}
	public IdWithName getPrId() {
		return prId;
	}
	public void setPrId(IdWithName prId) {
		this.prId = prId;
	}
	public long getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(long submitDate) {
		this.submitDate = submitDate;
	}
	public List<IdWithName> getOfferTreats() {
		return offerTreats;
	}
	public void setOfferTreats(List<IdWithName> offerTreats) {
		this.offerTreats = offerTreats;
	}
}

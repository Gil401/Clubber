package ClubberLogic;

import java.util.Date;



public class UserMessagesData {
	private Integer id;
	private Integer fromUserId;
	private Integer toUserId;
	private Integer auctionId;
	private Integer lineId;
	private Date createdOn;
	private String description;
	
	public Date getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getFromUserId() {
		return fromUserId;
	}
	
	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}
	
	public Integer getToUserId() {
		return toUserId;
	}
	
	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}
	
	public Integer getAuctionId() {
		return auctionId;
	}
	
	public void setAuctionId(Integer auctionId) {
		this.auctionId = auctionId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

}

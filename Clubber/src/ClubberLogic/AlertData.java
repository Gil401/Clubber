package ClubberLogic;

public class AlertData {
	private String alertTextMessage;
	private Integer alertTypeId;
	private boolean isAlertOpened;
	
	private String getAlertTextMessage() {
		return alertTextMessage;
	}
	
	private void setAlertTextMessage(String alertTextMessage) {
		this.alertTextMessage = alertTextMessage;
	}
	
	private Integer getAlertType() {
		return alertTypeId;
	}
	
	private void setAlertType(Integer alertType) {
		this.alertTypeId = alertType;
	}
	
	private boolean isAlertOpened() {
		return isAlertOpened;
	}
	
	private void setAlertOpened(boolean isAlertOpened) {
		this.isAlertOpened = isAlertOpened;
	}
	
}

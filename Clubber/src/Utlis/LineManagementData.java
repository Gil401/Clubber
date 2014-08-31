package Utlis;

import ClubberLogic.BusinessData;
import ClubberLogic.LineData;

public class LineManagementData {
	
	private LineData line;
	private BusinessData business ;
	
	public LineManagementData() {
	
	}
	
	public LineManagementData(LineData line, BusinessData business){
		
		this.setLine(line);
		this.setBusiness(business);
	}

	public LineData getLine() {
		return line;
	}

	public void setLine(LineData line) {
		this.line = line;
	}

	public BusinessData getBusiness() {
		return business;
	}

	public void setBusiness(BusinessData business) {
		this.business = business;
	}

}

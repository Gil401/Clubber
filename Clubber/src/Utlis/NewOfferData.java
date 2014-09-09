package Utlis;

import java.util.LinkedList;
import java.util.List;

public class NewOfferData {
	private List<IdWithName> lines;
	private List<IdWithName> treats;
	
	public NewOfferData()
	{
		lines= new LinkedList <IdWithName> (); 
		treats= new LinkedList <IdWithName> (); 		
	}
	
	public List<IdWithName> getLines() {
		return lines;
	}
	public void setLines(List<IdWithName> lines) {
		this.lines = lines;
	}
	public List<IdWithName> getTreats() {
		return treats;
	}
	public void setTreats(List<IdWithName> treats) {
		this.treats = treats;
	}
}
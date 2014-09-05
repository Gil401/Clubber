package ClubberLogic;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import Utlis.IdWithName;

public class LineData {
	private Integer id;
	private String m_LineName;
	private Integer m_DayInWeek;
	private long startDate;
	private long endDate;
	private Integer minAge;
	private File photo;
	private String description;
	private String entranceFee;
	private String dj;
	private String openingHour;
	private IdWithName pr; /*owner- created by*/
	private IdWithName business;
	private List<IdWithName> musicStyles;
	private LinkedList<IdWithName> prs;
	
	public LineData()
	{
		musicStyles= new LinkedList<IdWithName>();
		prs= new LinkedList<IdWithName>();
		m_LineName="";
		m_DayInWeek=1;
		minAge=1;
		description="";
		entranceFee="";
		dj="";
		openingHour="";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public Integer getMinAge() {
		return minAge;
	}

	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDj() {
		return dj;
	}

	public void setDj(String dj) {
		this.dj = dj;
	}

	public String getOpeningHour() {
		return openingHour;
	}

	public void setOpeningHour(String openingHour) {
		this.openingHour = openingHour;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public String getEntranceFee() {
		return entranceFee;
	}

	public void setEntranceFee(String entranceFee) {
		this.entranceFee = entranceFee;
	}

	

	public String getM_LineName() {
		return m_LineName;
	}

	public void setM_LineName(String m_LineName) {
		this.m_LineName = m_LineName;
	}

	public IdWithName getPr() {
		return pr;
	}

	public void setPr(IdWithName pr) {
		this.pr = pr;
	}

	public IdWithName getBusiness() {
		return business;
	}

	public void setBusiness(IdWithName business) {
		this.business = business;
	}
	
	public List<IdWithName> getMusicStylesIds() {
		return musicStyles;
	}

	public Integer getM_DayInWeek() {
		return m_DayInWeek;
	}

	public void setM_DayInWeek(Integer m_DayInWeek) {
		this.m_DayInWeek = m_DayInWeek;
	}

	public LinkedList<IdWithName> getPrs() {
		return prs;
	}

	public void setMusicStyle(LinkedList<IdWithName> musicStyles) {
		this.musicStyles = musicStyles;
	}

}

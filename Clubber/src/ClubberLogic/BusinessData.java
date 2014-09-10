package ClubberLogic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Utlis.IdWithName;

public class BusinessData {
	private Integer m_Id;
	private String m_Name;
	private IdWithName m_AreaId;
	private IdWithName m_CityId;
	private IdWithName m_StreetId;
	private Integer m_HouseNumber;
	private String m_PhoneNumber;
	private String m_Description;
	private String m_Photo;
	private IdWithName m_BusinessTypeId;
	private String imageUrl;	
	private ArrayList<LineData> m_Lines = new ArrayList<LineData>();
		
	
	public Integer getM_Id() {
		return m_Id;
	}
	public void setM_Id(Integer m_Id) {
		this.m_Id = m_Id;
	}
	public IdWithName getM_AreaId() {
		return m_AreaId;
	}
	public void setM_AreaId(IdWithName m_AreaId) {
		this.m_AreaId = m_AreaId;
	}
	public IdWithName getM_CityId() {
		return m_CityId;
	}
	public void setM_CityId(IdWithName m_CityId) {
		this.m_CityId = m_CityId;
	}
	public IdWithName getM_StreetId() {
		return m_StreetId;
	}
	public void setM_StreetId(IdWithName m_StreetId) {
		this.m_StreetId = m_StreetId;
	}
	public Integer getM_HouseNumber() {
		return m_HouseNumber;
	}
	public void setM_HouseNumber(Integer m_HouseNumber) {
		this.m_HouseNumber = m_HouseNumber;
	}
	public String getM_PhoneNumber() {
		return m_PhoneNumber;
	}
	public void setM_PhoneNumber(String m_BusinessPhoneNumber) {
		this.m_PhoneNumber = m_BusinessPhoneNumber;
	}
	public String getM_Description() {
		return m_Description;
	}
	public void setM_Description(String m_Description) {
		this.m_Description = m_Description;
	}
	public IdWithName getM_BusinessTypeId() {
		return m_BusinessTypeId;
	}
	public void setM_BusinessTypeId(IdWithName m_BusinessTypeId) {
		this.m_BusinessTypeId = m_BusinessTypeId;
	}
	public void setM_Lines(ArrayList<LineData> m_Lines) {
		this.m_Lines = m_Lines;
	}

	public List<LineData> getM_Lines() {
		return m_Lines;
	}
	public void Add_Line(LineData i_Line) {
		m_Lines.add(i_Line);
	}
	public String getM_Name() {
		return m_Name;
	}
	public void setM_Name(String m_Name) {
		this.m_Name = m_Name;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getM_Photo() {
		return m_Photo;
	}
	public void setM_Photo(String m_Photo) {
		this.m_Photo = m_Photo;
	}
}

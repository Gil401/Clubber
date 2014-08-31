package Utlis;

public class IdWithName {
	private Integer id;
	private String Name;
	
	public IdWithName(Integer id, String Name)
	{
		this.id = id;
		this.Name = Name;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}

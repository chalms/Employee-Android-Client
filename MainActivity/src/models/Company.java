package models;

public class Company {
	private Integer id = null;
	private String name = null;
	public Company() {};
	Company(Object companyId, String companyName) {
		setId(companyId); setName(companyName);
	}; 
	Integer getId () {
		return id; 
	}
	void setId(Object i){
		if (id instanceof Integer) {
			id = ((Integer) i);
		} else if (i instanceof String) {
			id = Integer.parseInt(((String) i));
		} else {
			id = null;
		}
	}
	void setName(String n){
		name = n;
	}
	String getName() {
		return name; 
	}
}

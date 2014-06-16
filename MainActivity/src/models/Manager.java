package models;

public class Manager {
	private String firstName = null; 
	private String lastName = null; 
	private String email = null; 
	private String id = null; 

	public Manager() {}; 

	public void setLastName(String string) {
		this.lastName = string; 
	}
	public void setFirstName(String string) {
		this.firstName = string; 
	}
	public void setEmail(String string) {
		this.email = string; 
	}
	public void setId(String string) {
		this.id = string; 
	}

	public String getLastName() {
		return this.lastName; 
	}

	public String getFirstName() {
		return this.firstName; 
	}

	public String getEmail() {
		return this.email; 
	}

	public String getId() {
		return this.id; 
	}
}

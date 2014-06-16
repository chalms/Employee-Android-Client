package models;

public class Manager {
	private String firstName = null; 
	private String lastName = null; 
	private String email = null; 
	private String id = null; 
	
	public Manager() {}; 
	public void setLastName(String string) {
		// TODO Auto-generated method stub
		this.lastName = string; 
	}
	public void setFirstName(String string) {
		// TODO Auto-generated method stub
		this.firstName = string; 
		
	}
	public void setEmail(String string) {
		// TODO Auto-generated method stub
		this.email = string; 
	}
	public void setId(String string) {
		// TODO Auto-generated method stub
		this.id = string; 
	}
	
	public String getEmail() {
		return this.email; 
	}
	
	public String getId() {
		return this.email; 
	}
	
	

}

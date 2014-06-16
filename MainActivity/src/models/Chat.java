package models;

import java.util.List;

import models.nodes.FireNode;

public class Chat extends FireNode {
	private Manager manager; 
	
	public Chat() {}; 
	
	public void addMessage()
	
	public void setManager(Manager man) {
		this.manager = man; 
	}
	
	public Manager getManager(){
		return this.manager;
	}
}

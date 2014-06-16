package models;

import java.util.HashMap;
import models.nodes.FireNode;

public class Chat extends FireNode {
	private Manager manager; 
	private HashMap <String, Message> messages = new HashMap <String, Message> (); 
	
	public Chat() {}; 
	
	
	public void addMessage(Message message) {
		messages.put(message.messageCode(), message);
	}
	
	public void setManager(Manager man) {
		this.manager = man; 
	}
	
	public Manager getManager(){
		return this.manager;
	}
}

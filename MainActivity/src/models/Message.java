package models;

import java.util.Date;

public class Message {
    private String messageBody; 
    private Chat chat; 
    private Manager manager; 
    private Date delivered_at; 
    private Boolean delivered; 
    private Boolean read;
    
    
    public String messageCode() {
    	 return (getDelivered_at().toString() + chat.getNodeID()); 
    }    
    
	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	public Chat getChat() {
		return chat;
	}
	public void setChat(Chat chat) {
		this.chat = chat;
	}
	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	public Boolean getDelivered() {
		return delivered;
	}
	public void setDelivered(Boolean delivered) {
		this.delivered = delivered;
	}
	public Boolean getRead() {
		return read;
	}
	public void setRead(Boolean read) {
		this.read = read;
	}
	public Date getDelivered_at() {
		return delivered_at;
	}
	public void setDelivered_at(Date delivered_at) {
		this.delivered_at = delivered_at;
	}
    
    
    
    
}

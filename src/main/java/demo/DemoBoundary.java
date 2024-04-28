package demo;

import java.util.Date;

public class DemoBoundary {
	private String id;
	private String message;
	private Date messageTimestamp;

	public DemoBoundary() {
	}

	public DemoBoundary(String message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getMessageTimestamp() {
		return messageTimestamp;
	}

	public void setMessageTimestamp(Date messageTimestamp) {
		this.messageTimestamp = messageTimestamp;
	}

	@Override
	public String toString() {
		return "DemoBoundary ["
			+ "id=" + id 
			+ ", message=" + message 
			+ ", messageTimestamp=" + messageTimestamp 
			+ "]";
	}

}

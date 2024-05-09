package demo;

import java.util.Date;
import java.util.Map;

public class DemoBoundary {
	private String id;
	private String message;
	private Date messageTimestamp;
	private Map<String, Object> details;
	
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

	public Map<String, Object> getDetails() {
		return details;
	}
	
	public void setDetails(Map<String, Object> details) {
		this.details = details;
	}
	
	@Override
	public String toString() {
		return "DemoBoundary ["
			+ "id=" + id 
			+ ", message=" + message 
			+ ", messageTimestamp=" + messageTimestamp 
			+ ", details=" + details
			+ "]";
	}

}

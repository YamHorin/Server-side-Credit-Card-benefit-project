package demo;

import java.util.Date;
import java.util.Map;

/*
{
  "id":"abc",
  "message":"hello",
  "messageTimestamp":"2024-05-23T14:34:00+0200",
  "details":{"key":5, "otherKey":"hello"},
  "status":"OK|NOT_OK|UNDETERMINED",
  "version":0.1,
  "name":{
    "firstName":"Jane",
    "lastName":"Smith"
  }
}
 */
public class DemoBoundary {
	private String id;
	private String message;
	private Date messageTimestamp;
	private Map<String, Object> details;
	private StatusEnum status;
	private Double version;
	private NameBoundary name;
	
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
	
	public StatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	
	
	public Double getVersion() {
		return version;
	}

	public void setVersion(Double version) {
		this.version = version;
	}

	public NameBoundary getName() {
		return name;
	}
	
	public void setName(NameBoundary name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "DemoBoundary ["
			+ "id=" + id 
			+ ", message=" + message 
			+ ", messageTimestamp=" + messageTimestamp 
			+ ", details=" + details
			+ ", status=" + status
			+ ", version=" + version
			+ ", name=" + name
			+ "]";
	}

}

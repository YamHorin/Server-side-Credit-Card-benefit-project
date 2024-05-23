package demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name = "DEMO_TBL")
public class DemoEntity {
	@Id
	private String id;

	private String message;


	@Temporal(TemporalType.TIMESTAMP)
	private Date messageTimestamp;

	// TODO change this attribute to proper representation in the DB 
	@Transient
	private Map<String, Object> details;

	@Enumerated(EnumType.STRING)
	private StatusEnumInDB status;

	private double version;

	private String firstName;
	private String lastName;

	public DemoEntity() {
		this.details = new HashMap<>();
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

	public StatusEnumInDB getStatus() {
		return status;
	}

	public void setStatus(StatusEnumInDB status) {
		this.status = status;
	}

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "DemoEntity [id=" + id + ", message=" + message + ", messageTimestamp=" + messageTimestamp + ", details="
				+ details + ", status=" + status + ", version=" + version + ", firstName=" + firstName + ", lastName="
				+ lastName + "]";
	}

}

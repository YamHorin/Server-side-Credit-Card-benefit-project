package demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DEMO_TBL")
public class DemoEntity {
	@Id private String id;
	private String message;

	// TODO add more fields to database representing all DemoBoundary attributes

	public DemoEntity() {
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

	@Override
	public String toString() {
		return "DemoEntity [id=" + id + ", message=" + message + "]";
	}

}

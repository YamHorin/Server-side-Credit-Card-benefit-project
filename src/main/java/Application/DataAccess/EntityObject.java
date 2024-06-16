package Application.DataAccess;

import java.util.Date;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import Application.business_logic.BoundaryObject;
import Application.business_logic.CreatedBy;
import Application.business_logic.ObjectId;

@Entity
@Table(name = "OBJ_TBL")
public class EntityObject {

    @Id 
    private String objectID;
    private String type;
    private String alias;
    private Boolean active;
    private Date creationTimeStamp;
    private String createdBy;
	@Convert(converter = ConverterBetweenMapAndString.class)
    private Map<String, Object> objectDetails;
	private double location_lat;
	private double location_lng;
	


    public EntityObject() {
    }


	public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(Date creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Map<String, Object> getObjectDetails() {
        return objectDetails;
    }

    public void setObjectDetails(Map<String, Object> objectDetails) {
        this.objectDetails = objectDetails;
    }

    @Override
    public String toString() {
        return "ObjectEntity{" +
                "objectID='" + objectID + '\'' +
                ", type='" + type + '\'' +
                ", alias='" + alias + '\'' +
                ", location='" + location_lat+" "+location_lng + '\'' +
                ", active=" + active +
                ", creationTimeStamp=" + creationTimeStamp +
                ", createdBy=" + createdBy +
                ", objectDetails=" + objectDetails +
                '}';
    }



	public double getLocation_lat() {
		return location_lat;
	}

	public void setLocation_lat(double location_lat) {
		this.location_lat = location_lat;
	}

	public double getLocation_lng() {
		return location_lng;
	}

	public void setLocation_lng(double location_lng) {
		this.location_lng = location_lng;
	}
}

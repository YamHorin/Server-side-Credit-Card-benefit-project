package Application.business_logic.Boundaies;

import java.util.Date;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Application.DataAccess.Entities.EntityObject;
import Application.business_logic.javaObjects.CreatedBy;
import Application.business_logic.javaObjects.Location;
import Application.business_logic.javaObjects.ObjectId;

import java.util.Map;

public class ObjectBoundary {
    private ObjectId objectId;
    private String type;
    private String alias;
    private Location location;
    private Boolean active;
    private Date creationTimestamp;
    private CreatedBy createdBy;
    private Map<String, Object> objectDetails;

    public ObjectBoundary() {
    }



    public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public ObjectId getObjectID() {
        return objectId;
    }

    public void setObjectID(ObjectId objectID) {
        this.objectId = objectID;
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

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getCreationTimeStamp() {
        return creationTimestamp;
    }

    public void setCreationTimeStamp(Date creationTimeStamp) {
        this.creationTimestamp = creationTimeStamp;
    }

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }
    
    public Boolean getActive() {
        return active;
    }

    public Map<String, Object> getObjectDetails() {
        return objectDetails;
    }

    public void setObjectDetails(Map<String, Object> objectDetails) {
        this.objectDetails = objectDetails;
    }



    @Override
    public String toString() {
        return "ObjectBoundary{" +
                "objectID=" + objectId.toString() +
                ", type='" + type + '\'' +
                ", alias='" + alias + '\'' +
                ", loction='" + location + '\'' +
                ", active=" + active +
                ", creationTimeStamp=" + creationTimestamp +
                ", createdBy=" + createdBy.toString() +
                ", objectDetails=" + objectDetails +
                '}';
    }
    
	
}

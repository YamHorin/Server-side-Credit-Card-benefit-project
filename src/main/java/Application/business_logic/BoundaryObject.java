package Application.business_logic;

import java.util.Date;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import Application.DataAccess.EntityObject;
import Application.DataAccess.Location;
import java.util.Map;

public class BoundaryObject {
    private ObjectId objectID;
    private String type;
    private String alias;
    private Location location;
    private Boolean active;
    private Date creationTimeStamp;
    private CreatedBy createdBy;
    private Map<String, Object> objectDetails;

    public BoundaryObject() {
    }



    public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public ObjectId getObjectID() {
        return objectID;
    }

    public void setObjectID(ObjectId objectID) {
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

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(Date creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
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
                "objectID=" + objectID.toString() +
                ", type='" + type + '\'' +
                ", alias='" + alias + '\'' +
                ", loction='" + location + '\'' +
                ", active=" + active +
                ", creationTimeStamp=" + creationTimeStamp +
                ", createdBy=" + createdBy.toString() +
                ", objectDetails=" + objectDetails +
                '}';
    }
    
	
}

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

    public BoundaryObject(EntityObject objectEntity) {
        String[] splitId = objectEntity.getObjectID().split("_");
        this.objectID = new ObjectId();
        this.objectID.setId(splitId[0]);
        this.objectID.setSuperApp(splitId[1]);
        setObjectDetails(objectEntity.getObjectDetails());
        setActive(objectEntity.getActive());
        setCreationTimeStamp(objectEntity.getCreationTimeStamp());
        setCreatedBy(objectEntity.getCreatedBy());
        setType(objectEntity.getType());
        setAlias(objectEntity.getAlias());
        setLocation(new Location(objectEntity.getLocation_lat() , objectEntity.getLocation_lng()));
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

    public EntityObject toEntity() {
    	EntityObject objectEntity = new EntityObject();
        objectEntity.setObjectID(this.getObjectID().getId() + "__" + this.getObjectID().getSuperApp());
        objectEntity.setType(this.getType());
        objectEntity.setCreationTimeStamp(this.getCreationTimeStamp());
        objectEntity.setCreatedBy(this.getCreatedBy());
        objectEntity.setActive(this.getActive() == null || this.getActive());
        objectEntity.setAlias(this.getAlias() == null ? "demo instance" : this.getAlias());
        objectEntity.setObjectDetails(this.getObjectDetails() == null ? new HashMap<>() : this.getObjectDetails());
        objectEntity.setLocation_lat(this.location.getLat());
        objectEntity.setLocation_lng(this.location.getLng());


        return objectEntity;
    }

    @Override
    public String toString() {
        return "ObjectBoundary{" +
                "objectID=" + objectID +
                ", type='" + type + '\'' +
                ", alias='" + alias + '\'' +
                ", loction='" + location + '\'' +
                ", active=" + active +
                ", creationTimeStamp=" + creationTimeStamp +
                ", createdBy=" + createdBy +
                ", objectDetails=" + objectDetails +
                '}';
    }
    
	
}

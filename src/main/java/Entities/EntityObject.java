package Entities;

import java.util.Date;
import UserFiles.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import Boundary.BoundaryObject;

@Entity
@Table(name = "OBJ_TBL")
public class EntityObject {

    @Id 
    private String objectID;
    private String type;
    private String alias;
    private Boolean active;
    private Date creationTimeStamp;
	@Transient
    private CreatedBy createdBy;
	@Transient
    private Map<String, Object> objectDetails;
	private Location location;


    public EntityObject() {
    }

    public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
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

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CreatedBy createdBy) {
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
                ", location='" + location + '\'' +
                ", active=" + active +
                ", creationTimeStamp=" + creationTimeStamp +
                ", createdBy=" + createdBy +
                ", objectDetails=" + objectDetails +
                '}';
    }
    public BoundaryObject toBoundary(EntityObject entity)
    {
    	BoundaryObject bounObj = new BoundaryObject();
    	bounObj.setActive(entity.getActive());
    	bounObj.setAlias(entity.getAlias());
    	bounObj.setCreatedBy(entity.getCreatedBy());
    	bounObj.setCreationTimeStamp(entity.getCreationTimeStamp());
    	bounObj.setLocation(entity.getLocation());
    	bounObj.setObjectDetails(entity.getObjectDetails());
    	ObjectId ObjectId = new ObjectId();
    	ObjectId.setId(entity.getObjectID());
    	ObjectId.setSuperApp(get_super_app_name(alias));
    	bounObj.setObjectID(ObjectId);
    	bounObj.setType(entity.getType());
    	return bounObj;
    	
    }
	@Value("${spring.application.name:SuperApppp}")
	public String get_super_app_name(String name_super_app) {
		System.err.println("**** reading from configuration default super app name: " + name_super_app);
		return name_super_app;
	}
}

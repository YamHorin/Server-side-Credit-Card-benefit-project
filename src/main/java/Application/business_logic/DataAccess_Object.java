package Application.business_logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.lang.String;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Application.DataAccess.EntityObject;
import Application.DataAccess.ObjDao;
@Service
public class DataAccess_Object implements ServicesObject{
	private ObjDao ObjectDao;
	private String superAppName;
	public DataAccess_Object(ObjDao objectDao) {
		this.ObjectDao = objectDao;
	}
	@Override
    @Transactional(readOnly = true)
	public Optional<BoundaryObject> getSpecificObj(String id) {

		Optional <EntityObject> entityObject = this.ObjectDao.findById(id);
		EntityObject entity = new EntityObject();
		Optional<BoundaryObject> boundaryObject = entityObject.map(entity::toBoundary);
		if (boundaryObject.isEmpty())
			System.err.println("* no user to return");
		else
			System.out.println(boundaryObject.toString());
		return boundaryObject;	
	}
	@Override
    @Transactional(readOnly = true)
	public List<BoundaryObject> getAllObjects() {
		List<EntityObject> entities = this.ObjectDao.findAll();
		List<BoundaryObject> boundaries = new ArrayList<>();
		for (EntityObject entity : entities) {
			boundaries.add(entity.toBoundary(entity));
		}
		
		System.err.println("all objects data from database: " + boundaries);
		return boundaries;
	}
	@Override
	@Transactional(readOnly = false)
	public BoundaryObject createObject(BoundaryObject ObjectBoundary) {
		System.err.println("* client requested to store: " + ObjectBoundary);
		ObjectBoundary.setCreationTimeStamp(new Date());
		ObjectId objId = new ObjectId();
		objId.setId(ObjectBoundary.getObjectID().getId());
		objId.setSuperApp(this.superAppName);
		ObjectBoundary.setObjectID(objId);
		EntityObject entity = ObjectBoundary.toEntity();
		entity = this.ObjectDao.save(entity);
		BoundaryObject rv  = entity.toBoundary(entity);
		System.err.println("all the data objects server stored: " + rv);
		return rv;
	}
	@Override
	@Transactional(readOnly = false)
	public void deleteAllObjs() {
		System.err.println("* deleting table for objects");
		this.ObjectDao.deleteAll();
		
	}
		
	@Override
	@Transactional(readOnly = false)
	public void updateObj(String id, BoundaryObject update) {
		System.err.println("* updating obj with id: " + id + ", with the following details: " + update);
		EntityObject objectEntity = this.ObjectDao.findById(id).orElseThrow(()->new RuntimeException(
				"Could not find object for update by id: " + id));
        if (update.getType()!=null)
        	objectEntity.setType(update.getType());
       if (update.getCreationTimeStamp()!=null)
	        objectEntity.setCreationTimeStamp(update.getCreationTimeStamp());
       
        if (update.getCreatedBy()!=null)
	        objectEntity.setCreatedBy(update.getCreatedBy());
	      
        
        if (update.getActive()!=null)  
	        objectEntity.setActive(update.getActive() == null || update.getActive());
	      
        if (update.getAlias()!=null)  
	        objectEntity.setAlias(update.getAlias() == null ? "demo instance" : update.getAlias());
        
     
      if (update.getObjectDetails()!=null)
    	  objectEntity.setObjectDetails(update.getObjectDetails());
      if(update.getLocation()!=null) 
      {
    	  objectEntity.setLocation_lat(update.getLocation().getLat());

    	  objectEntity.setLocation_lng(update.getLocation().getLng());
      }
    	  
    objectEntity = this.ObjectDao.save(objectEntity);
		System.err.println("user has been updated: * " + objectEntity);
	}
	@Value("${spring.application.name:Jill}")
    public void setsuperAppName(String superApp) {
		System.err.println("**** reading from configuration default superAppName: " + superApp);
        this.superAppName = superApp;
    }




}

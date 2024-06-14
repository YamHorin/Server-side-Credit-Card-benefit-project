package Application.business_logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.lang.String;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Application.DataConvertor;
import Application.DataAccess.EntityObject;
import Application.DataAccess.ObjDao;
import Application._a_Presentation.BoundaryIsNotFoundException;
@Service
public class DataManagerObject implements ServicesObject{
	private ObjDao ObjectDao;
	private String superAppName;
	private DataConvertor DataConvertor;

	public DataManagerObject(ObjDao objectDao , DataConvertor DataConvertor) {
		this.ObjectDao = objectDao;
		this.DataConvertor = DataConvertor;

	}
	@Override
    @Transactional(readOnly = true)
	public Optional<BoundaryObject> getSpecificObj(String id , String superApp) {
		if (this.superAppName.compareTo(superApp)!=0)
			throw new BoundaryIsNotFoundException("super app is not found...");
		id = id +"__"+superApp;
		
		Optional <EntityObject> entityObject = this.ObjectDao.findById(id);
		Optional<BoundaryObject> boundaryObject = entityObject.map(this.DataConvertor::EntityObjectTOBoundaryObject);
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
			boundaries.add(this.DataConvertor.EntityObjectTOBoundaryObject(entity));
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
		objId.setId(UUID.randomUUID().toString());
		objId.setSuperApp(this.superAppName);
		ObjectBoundary.setObjectID(objId);
		ObjectBoundary.setCreatedBy(new CreatedBy(ObjectBoundary.getCreatedBy().getUserId().getEmail() , this.superAppName));
		EntityObject entity = this.DataConvertor.BoundaryObjectTOEntityObject(ObjectBoundary);
		entity = this.ObjectDao.save(entity);
		BoundaryObject rv  = this.DataConvertor.EntityObjectTOBoundaryObject(entity);
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
	public void updateObj(String id,String superApp ,BoundaryObject update) {
		if (this.superAppName.compareTo(superApp)!=0)
		{
			System.err.println("\n"+this.superAppName.compareTo(superApp)+"\n");
			
			throw new BoundaryIsNotFoundException("super app is not found...");
		}
		System.err.println("* updating obj with id: " + id + ", with the following details: " + update);
		id = id +"__"+superApp;
		String id2 = id;
		EntityObject objectEntity = this.ObjectDao.findById(id).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find object for update by id: " + id2));
        if (update.getType()!=null)
        	objectEntity.setType(update.getType());
       if (update.getCreationTimeStamp()!=null)
	        objectEntity.setCreationTimeStamp(update.getCreationTimeStamp());
       
        if (update.getCreatedBy()!=null)
        {
        	String email = update.getCreatedBy().getUserId().getEmail();
        	objectEntity.setCreatedBy(email+"_"+this.superAppName);
        }
	      
        
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

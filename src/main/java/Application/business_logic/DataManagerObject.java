package Application.business_logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.String;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Application.DataConvertor;
import Application.DataAccess.EntityObject;
import Application.DataAccess.EntityUser;
import Application.DataAccess.Location;
import Application.DataAccess.ObjDao;
import Application.DataAccess.RoleEnumEntity;
import Application.DataAccess.UserDao;
import Application._a_Presentation.BoundaryIsNotFilledCorrectException;
import Application._a_Presentation.BoundaryIsNotFoundException;
import Application._a_Presentation.UnauthorizedException;
@Service
public class DataManagerObject implements ServicesObject{
	private ObjDao objectDao;
	private UserDao userDao;
	private String superAppName;
	private DataConvertor DataConvertor;
	
	//a value for max location value , could be change in the future
	private final double limit_location = 100;
	
	
	public DataManagerObject(UserDao userDao, ObjDao objectDao , DataConvertor DataConvertor) {
		this.objectDao = objectDao;
		this.userDao = userDao;
		this.DataConvertor = DataConvertor;

	}
	@Override
    @Transactional(readOnly = true)
	public Optional<BoundaryObject> getSpecificObj(String id , String superApp ,String userSuperapp, String email) {
		if (this.superAppName.compareTo(superApp)!=0)
			throw new BoundaryIsNotFoundException("super app is not found...");
		//check what the role of the user
		String id_user = email+"_"+userSuperapp;
		EntityUser userEntity = this.userDao.findById(id_user).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id_user));
		String id_object = id +"__"+superApp;
		Optional <EntityObject> entityObject;
		Optional<BoundaryObject> boundaryObject = java.util.Optional.empty();
		switch (userEntity.getRole())
		{
		case adm_user:
			throw new UnauthorizedException("admin can't get a Specific object ");
		case minapp_user:
			entityObject = this.objectDao.findById(id_object);
			//check if the object is active 
			entityObject = entityObject.filter(EntityObject::getActive);
			boundaryObject = entityObject.map(this.DataConvertor::EntityObjectTOBoundaryObject);
			if (boundaryObject.isEmpty())
				System.err.println("* no user to return");
			else
				System.out.println(boundaryObject.toString());
			return boundaryObject;	
		case superapp_user:
				entityObject = this.objectDao.findById(id_object);
				boundaryObject = entityObject.map(this.DataConvertor::EntityObjectTOBoundaryObject);
			if (boundaryObject.isEmpty())
				System.err.println("* no user to return");
			else
				System.out.println(boundaryObject.toString());
			return boundaryObject;
		case undetermined:
			throw new UnauthorizedException("undetermined can't get a Specific object ");
		default:
			break;
		
		}
		return boundaryObject;
	
	}
	@Override
    @Transactional(readOnly = true)
	public List<BoundaryObject> getAllObjects() {
		List<EntityObject> entities = this.objectDao.findAll();
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
		//check for null \empty Strings
		checkStringIsNullOrEmpty(ObjectBoundary.getType(), "type object");
		checkStringIsNullOrEmpty(ObjectBoundary.getAlias(), "Alias object");
		checkStringIsNullOrEmpty(ObjectBoundary.getCreatedBy().getUserId().getEmail(), "user email who created the object");
		//check for normal location
		checkLocationOfObject(ObjectBoundary.getLocation());
		
		ObjectBoundary.setCreationTimeStamp(new Date());
		ObjectId objId = new ObjectId();
		objId.setId(UUID.randomUUID().toString());
		objId.setSuperApp(this.superAppName);
		ObjectBoundary.setObjectID(objId);
		ObjectBoundary.setCreatedBy(new CreatedBy(ObjectBoundary.getCreatedBy().getUserId().getEmail() , this.superAppName));
		EntityObject entity = this.DataConvertor.BoundaryObjectTOEntityObject(ObjectBoundary);
		entity = this.objectDao.save(entity);
		BoundaryObject rv  = this.DataConvertor.EntityObjectTOBoundaryObject(entity);
		System.err.println("all the data objects server stored: " + rv);
		return rv;
	}
	private void checkLocationOfObject(Location location) {
		double lat =location.getLat();
		double lng = location.getLng();
		if (lat<=0 || lng<=0 || lat>limit_location ||lng>limit_location  )
			throw new BoundaryIsNotFilledCorrectException("location of boundary is not filled correctly");
	}
	@Override
	@Transactional(readOnly = false)
	public void deleteAllObjs(String id) {
		EntityUser userEntity = this.userDao.findById(id).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id));
		RoleEnumEntity role = userEntity.getRole();
		if (role != RoleEnumEntity.adm_user)
			throw new UnauthorizedException("only admin users can delet all users..");
		else {			
			System.err.println("* deleting table for objects");
			this.objectDao.deleteAll();
		}
	}
		
	@Override
	@Transactional(readOnly = false)
	public void updateObj(String id,String superApp, BoundaryObject update, String email, String userSuperapp) {
		if (this.superAppName.compareTo(superApp)!=0)
		{
			System.err.println("\n"+this.superAppName.compareTo(superApp)+"\n");
			
			throw new BoundaryIsNotFoundException("super app is not found...");
		}
		System.err.println("* updating obj with id: " + id + ", with the following details: " + update);
		id = id +"__"+superApp;
		String id2 = id;

		EntityObject objectEntity = this.objectDao.findById(id).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find object for update by id: " + id2));
		//check role
		String id_user = email+"_"+userSuperapp;
		EntityUser EntityUser  = this.userDao.findById(id_user).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find user for update by id: " + id_user));
		switch(EntityUser.getRole())
		{
		case adm_user:
			throw new UnauthorizedException("admin can't update object");
			
		case minapp_user:
			if (objectEntity.getActive()==false)
				throw new BoundaryIsNotFoundException("Could not find object for update by id: " + id2);
			updateObjectInAction(objectEntity, update);
			break;
		case superapp_user:
			updateObjectInAction(objectEntity, update);
			break;
		case undetermined:
			throw new UnauthorizedException("admin can't update object");
		default:
			break;
			
		
		}
    	  
    objectEntity = this.objectDao.save(objectEntity);
		System.err.println("\n\nuser has been updated: * \n" + objectEntity);
	}
	@Value("${spring.application.name:Jill}")
    public void setsuperAppName(String superApp) {
		System.err.println("**** reading from configuration default superAppName: " + superApp);
        this.superAppName = superApp;
    }

	public void checkStringIsNullOrEmpty(String str , String value)
	{
		if (str ==null || str  =="")
			throw new BoundaryIsNotFilledCorrectException("\nvalue String in the boundary is empty or null  :"+value);
	}
	
	public void isValidEmail(String email) {
		String EMAIL_PATTERN =
				"^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		if (email == null || email == "") {
			throw new BoundaryIsNotFilledCorrectException("email is empty..");
		}
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()==false)
		{
			System.err.println("enter check\n");
			throw new BoundaryIsNotFilledCorrectException("email is not filled correctly");
			
		}
		
		

	}
	@Override
	public List<BoundaryObject> searchByType(String type, int size, int page) { 
		return this.objectDao
		.findAllByMessage(type, PageRequest.of(page, size, Direction.ASC, 
				"lastName", "firstName",
				"id"))
		.stream()
		.map(entity->this.demoConverter.toBoundary(entity))
		.toList();
	}
	@Override
	public List<BoundaryObject> searchByAlias(String alias, int size, int page) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<BoundaryObject> searchByPattern(String pattern, int size, int page) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<BoundaryObject> searchByLat(String lat, int size, int page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//copy to an own function to avoid Repeated code
	public void updateObjectInAction(EntityObject objectEntity , BoundaryObject update)
	{
		checkLocationOfObject(update.getLocation());
		if (update.getType()!=null)
        	objectEntity.setType(update.getType());
       if (update.getCreationTimeStamp()!=null)
	        objectEntity.setCreationTimeStamp(update.getCreationTimeStamp());
       
        if (update.getCreatedBy()!=null)
        {
        	String email1 = update.getCreatedBy().getUserId().getEmail();
        	isValidEmail(email1);
        	objectEntity.setCreatedBy(email1+"_"+this.superAppName);
        }
	      
        
        if (update.getActive()!=null)  
	        objectEntity.setActive(update.getActive() == null || update.getActive());
	      
        if (update.getAlias()!=null)  
	        objectEntity.setAlias(update.getAlias() == null ? "object instance" : update.getAlias());
        
     
      if (update.getObjectDetails()!=null)
    	  objectEntity.setObjectDetails(update.getObjectDetails());
      if(update.getLocation()!=null) 
      {
    	  objectEntity.setLocation_lat(update.getLocation().getLat());
    	  objectEntity.setLocation_lng(update.getLocation().getLng());
      }
	}


}

package General;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import General.Data_Checks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import Boundary.BoundaryCommand;
import Boundary.BoundaryObject;
import Boundary.BoundaryUser;
import Entities.EntityUser;
import Entities.EntityObject;
import Entities.EntityCommand;
import UserFiles.User_Id;


public class H2Service_the_down_service implements DataService_Business_logic {
	private ObjDao ObjectDao;
	private UserDao UserDao;
	private MiniAppCommadDao miniAppCommandDao;
	
	//get from  data
	private String name_super_app;
	
	//converter data from Boundary to Entity 
	

	public H2Service_the_down_service(ObjDao Objectdao, UserDao userDao,
			MiniAppCommadDao miniAppCommandDao) {
		ObjectDao = Objectdao;
		UserDao = userDao;
		this.miniAppCommandDao = miniAppCommandDao;
		setDefaultFirstName("not important because it gets info from the configuration default");
	}
	@Value("${spring.application.name:SuperApppp}")
	public void setDefaultFirstName(String name_super_app) {
		System.err.println("**** reading from configuration default super app name: " + name_super_app);
		this.name_super_app = name_super_app;
	}
	@Override
    @Transactional(readOnly = true)
	public Optional<BoundaryUser> getSpecificUser(String id) {
		Optional <EntityUser> entityUser = this.UserDao.findById(id);
		//potential to bug?
		EntityUser entity = new EntityUser();
		Optional<BoundaryUser> boundaryUser = entityUser.map(entity::toBoundary);
		if (boundaryUser.isEmpty())
			System.err.println("* no user to return");
		else
			System.out.println(boundaryUser.toString());
		return boundaryUser;	
	}
	@Override
	@Transactional(readOnly = true)
	public List<BoundaryUser> getAllUsers() {
		List<EntityUser> entities = this.UserDao.findAll();
		List<BoundaryUser> boundaries = new ArrayList<>();
		for (EntityUser entity : entities) {
			boundaries.add(entity.toBoundary(entity));
		}
		
		System.err.println("* data from database: " + boundaries);
		return boundaries;
	}
	@Override
	@Transactional(readOnly = false)
	public BoundaryUser createUser(BoundaryUser UserBoundary) {
		System.err.println("* client requested to store: " + UserBoundary);
		EntityUser entity = UserBoundary.toEntity();
		entity = this.UserDao.save(entity);
		BoundaryUser rv  = entity.toBoundary(entity);
		System.err.println("* server stored: " + rv);
		return rv;
	}
	@Override
	@Transactional(readOnly = false)
	public void deleteAllUsers() {
		System.err.println("* deleting table for users");
		this.UserDao.deleteAll();
	}
	@Override
	@Transactional(readOnly = false)
	public void updateUser(String id, BoundaryUser update) {
		System.out.println("* updating user with id: " + id + ", with the following details: " + update);
		EntityUser userEntity = this.UserDao.findById(id).orElseThrow(()->new RuntimeException(
				"Could not find User for update by id: " + id));
		if (update.getUserId().getEmail()!=null)
			userEntity.setId(update.getUserId().getEmail() + "_" + update.getUserId().getSuperAPP());
		if (update.getRole()!=null)
			userEntity.setRole(update.getRole());
		if (update.getUserName()!=null)
			userEntity.setUserName(update.getUserName());
		if (update.getAvatar()!=null)
			userEntity.setAvatar(update.getAvatar());
//		//TODO maybe we need a version?
//		if (userEntity.getVersion() ==null)
//			userEntity.setVersion() = 0;
		userEntity = this.UserDao.save(userEntity);
		System.err.println("user has been updated: * " + userEntity);
	}
	@Override
    @Transactional(readOnly = true)
	public Optional<BoundaryObject> getSpecificObj(String id) {
		// TODO object
		Optional <EntityObject> entityObject = this.ObjectDao.findById(id);
		//potential to bug?
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
		
		System.err.println("* data from database: " + boundaries);
		return boundaries;
	}
	@Override
	@Transactional(readOnly = false)
	public BoundaryObject createObject(BoundaryObject ObjectBoundary) {
		System.err.println("* client requested to store: " + ObjectBoundary);
		EntityObject entity = ObjectBoundary.toEntity();
		entity = this.ObjectDao.save(entity);
		BoundaryObject rv  = entity.toBoundary(entity);
		System.err.println("* server stored: " + rv);
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
        //TODO we don't know how to put time in table
//       if (update.getCreationTimeStamp()!=null)
//	        objectEntity.setCreationTimeStamp(update.getCreationTimeStamp());
        
        //TODO created by it's just an email? don't understand 
//        if (update.getCreatedBy()!=null)
//	        objectEntity.setCreatedBy(update.getCreatedBy());
	      
        
        if (update.getActive()!=null)  
	        objectEntity.setActive(update.getActive() == null || update.getActive());
	      
        if (update.getAlias()!=null)  
	        objectEntity.setAlias(update.getAlias() == null ? "demo instance" : update.getAlias());
        
	      //TODO how to put a map in a table??????? 
//	      if (update.getObjectDetails()!=null)
//	        objectEntity.setObjectDetails(update.getObjectDetails() == null ? new HashMap<>() : update.getObjectDetails());
	    
        //TODO how to location in a table , just a long String?????  
//	      if(update.getLocation()!=null)  
//        objectEntity.setLocation(update.getLocation());
        objectEntity = this.ObjectDao.save(objectEntity);
		System.err.println("user has been updated: * " + objectEntity);
	}
	@Override
	@Transactional(readOnly = true)

	public Optional<BoundaryCommand> getSpecificMiniAppCommand(String id) {
		Optional <EntityCommand> entityCommand = this.miniAppCommandDao.findById(id);
		//potential to bug?
		EntityCommand entity = new EntityCommand();
		Optional<BoundaryCommand> boundaryCommand = entityCommand.map(entity::toBoudary);
		if (boundaryCommand.isEmpty())
			System.err.println("* no mini app command to return");
		else
			System.out.println(boundaryCommand.toString());
		return boundaryCommand;
	}
	@Override
	@Transactional(readOnly = true)

	public List<BoundaryCommand> getAllMiniAppCommands() {
		List<EntityCommand> entities = this.miniAppCommandDao.findAll();
		List<BoundaryCommand> boundaries = new ArrayList<>();
		for (EntityCommand entity : entities) {
			boundaries.add(entity.toBoudary(entity));
		}
		
		System.err.println("* data from database: " + boundaries);
		return boundaries;
	}
	@Override
	@Transactional(readOnly = false)

	public BoundaryCommand createMiniAppCommand(BoundaryCommand CommandBoundary) {
		System.err.println("* client requested to store: " + CommandBoundary);
		EntityCommand entity = CommandBoundary.toEntity();
		entity = this.miniAppCommandDao.save(entity);
		BoundaryCommand rv  = entity.toBoudary(entity);
		System.err.println("* server stored: " + rv);
		return rv;
	}
	@Override
	@Transactional(readOnly = false)

	public void deleteAlminiAppCommandes() {
		System.err.println("* deleting table for mini app commands :)");
		this.miniAppCommandDao.deleteAll();
		
	}
	@Override
	@Transactional(readOnly = false)

	public void updateminiAppCommand(String id, BoundaryCommand update) {
		System.err.println("* updating mini app command  with id: " + id + "\n, with the following details: " + update);
		EntityCommand entity = this.miniAppCommandDao.findById(id).orElseThrow(()->new RuntimeException(
				"Could not find command for update by id: " + id));
		if (update.getCommand()!=null)
	        entity.setCommand(update.getCommand());
		//TODO is targetObject is just the id of the object in the objectTable?
//		if (update.getTargetObject()!=null)
//	        entity.setTargetObject(update.getTargetObject());
		
		//TODO how to put a map in a table??????? 
//		if (update.getCommandAttributes()!=null)
//			entity.setCommandAttributes(update.getCommandAttributes());
		
        //TODO is InvokedBy is just an email of the user 
//		if (update.getInvokedBy()!=null)
//			entity.setInvokedBy(update.getInvokedBy());
		
		//TODO we don't know how to put time in table
//		if (update.getInvocationTimeStamp()!=null)
//	        entity.setInvocationTimeStamp(update.getInvocationTimeStamp());
		
		entity = this.miniAppCommandDao.save(entity);
		System.err.println("command has been updated: * " + entity);   
		
	}
	
	
	}
	
	
	


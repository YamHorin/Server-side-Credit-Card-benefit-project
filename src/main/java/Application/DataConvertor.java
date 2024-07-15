package Application;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import Application.DataAccess.Entities.EntityCommand;
import Application.DataAccess.Entities.EntityObject;
import Application.DataAccess.Entities.EntityUser;
import Application.DataAccess.Entities.RoleEnumEntity;
import Application.business_logic.Boundaies.MiniAppCommandBoundary;
import Application.business_logic.Boundaies.ObjectBoundary;
import Application.business_logic.Boundaies.UserBoundary;
import Application.business_logic.Boundaies.RoleEnumBoundary;
import Application.business_logic.javaObjects.CommandId;
import Application.business_logic.javaObjects.CreatedBy;
import Application.business_logic.javaObjects.Location;
import Application.business_logic.javaObjects.ObjectId;
import Application.business_logic.javaObjects.TargetObject;
import Application.business_logic.javaObjects.UserId;


@Component
public class DataConvertor {

    public UserBoundary EntityUserToBoundaryUser(EntityUser entity)
    {
    	UserBoundary boun = new UserBoundary();
    	RoleEnumBoundary role =   RoleEnumBoundary.valueOf(entity.getRole().name().toUpperCase());
    	boun.setRole(role);
    	boun.setAvatar(entity.getAvatar());
    	boun.setUserId(UserIDFromStringId(entity.getId()));
//    	String email = entity.getId().split("_")[0];
//    	String superAppName = entity.getId().split("_")[1];
//    	boun.setUserId(new UserId(superAppName, email));
    	boun.setUsername(entity.getUsername());
    	return boun;
    }
    public  ObjectBoundary EntityObjectTOBoundaryObject (EntityObject entity)
    {
    	ObjectBoundary bounObj = new ObjectBoundary();
    	bounObj.setActive(entity.getActive());
    	bounObj.setAlias(entity.getAlias());
    	bounObj.setCreatedBy(new CreatedBy(UserIDFromStringId(entity.getCreatedBy())));
//    	String id = entity.getCreatedBy();
//    	String email = id.split("_")[0];
//    	String superAppName = id.split("_")[1];
//    	bounObj.setCreatedBy(new CreatedBy(email, superAppName));
    	bounObj.setCreationTimeStamp(entity.getCreationTimeStamp());
    	bounObj.setLocation(new Location(entity.getLocation_lat() , entity.getLocation_lng()));
    	bounObj.setObjectDetails(entity.getObjectDetails());
    	ObjectId ObjectId = new ObjectId();
    	ObjectId.setId(entity.getObjectID().split("__")[0]);
    	ObjectId.setSuperapp(entity.getObjectID().split("__")[1]);
    	bounObj.setObjectID(ObjectId);
    	bounObj.setType(entity.getType());
    	return bounObj;
    	
    }
    public MiniAppCommandBoundary EntityCommandToBoundaryCommand (EntityCommand Entity)
	{
		MiniAppCommandBoundary boun = new MiniAppCommandBoundary();
		boun.setCommand(Entity.getCommand());
		boun.setCommandAttributes(Entity.getCommandAttributes());
		boun.setCommandId(makeCommandIdFromString(Entity.getCommandId()));
		
		boun.setInvocationTimeStamp(Entity.getInvocationTimeStamp());
    	boun.setInvokedBy(new CreatedBy(UserIDFromStringId(Entity.getInvokedBy())));
		
		TargetObject targetObject = new TargetObject ();
		ObjectId ObjectId = new ObjectId();
    	ObjectId.setId(Entity.getTargetObject().split("__")[0]);
    	ObjectId.setSuperapp(Entity.getTargetObject().split("__")[1]);
    	targetObject.setObjectId(ObjectId);
		boun.setTargetObject(targetObject);
		return boun;
	}
    
    
    public EntityCommand BoundaryCommandToEntityCommand(MiniAppCommandBoundary bCommand) {
        EntityCommand entity = new EntityCommand();
        entity.setCommand(bCommand.getCommand());
        entity.setCommandId(makeStringFromCommandId(bCommand.getCommandId()));
        entity.setMiniAppName(bCommand.getCommandId().getMiniApp());
        ObjectId TargetObject = bCommand.getTargetObject().getObjectId();
        String objectTarget = TargetObject.getId()+"__"+TargetObject.getSuperapp();
        entity.setTargetObject(objectTarget);
        entity.setCommandAttributes(bCommand.getCommandAttributes());
        UserId bUser = bCommand.getInvokedBy().getUserId();
        entity.setInvokedBy(stringIdFromUserID(bUser));
        entity.setInvocationTimeStamp(bCommand.getInvocationTimeStamp());
        return entity;
    }
    
    public EntityObject BoundaryObjectTOEntityObject(ObjectBoundary bObject) {
    	EntityObject objectEntity = new EntityObject();
        objectEntity.setObjectID(bObject.getObjectID().getId() + "__" + bObject.getObjectID().getSuperapp());
        objectEntity.setType(bObject.getType());
        objectEntity.setCreationTimeStamp(bObject.getCreationTimeStamp());
//        String email = bObject.getCreatedBy().getUserId().getEmail();
//        String superApp = bObject.getCreatedBy().getUserId().getSuperAPP();
        
    	objectEntity.setCreatedBy(stringIdFromUserID(bObject.getCreatedBy().getUserId()));
        objectEntity.setActive(bObject.getActive() == null || bObject.getActive());
        objectEntity.setAlias(bObject.getAlias() == null ? "object" : bObject.getAlias());
        objectEntity.setObjectDetails(bObject.getObjectDetails() == null ? new HashMap<>() : bObject.getObjectDetails());
        objectEntity.setLocation_lat(bObject.getLocation().getLat());
        objectEntity.setLocation_lng(bObject.getLocation().getLng());
        return objectEntity;
    }
    
	public EntityUser BoundaryUserTOEntityUser(UserBoundary bUser) {
		EntityUser userEntity = new EntityUser();
		userEntity.setId(stringIdFromUserID(bUser.getUserId()));
		RoleEnumEntity  role = RoleEnumEntity.valueOf(bUser.getRole().name().toLowerCase());
		userEntity.setRole(role);
		userEntity.setUsername(bUser.getUsername());
		userEntity.setAvatar(bUser.getAvatar());
		return userEntity;

	}
	
	public String stringIdFromUserID(UserId UserId)
	{
		String email = UserId.getEmail();
		String superAppName = UserId.getSuperapp();
		return email+" "+superAppName;
	}
	public UserId UserIDFromStringId(String id)
	{
		String email = id.split(" ")[0];
    	String superAppName = id.split(" ")[1];
		return new UserId(superAppName, email);
	}
	public String makeStringFromCommandId(CommandId id)
	{
		return id.getId()+" "+id.getMiniApp()+" "+id.getSuperApp();
	}
	public CommandId makeCommandIdFromString(String id)
	{
		String arr [] = id.split(" ");
		CommandId commandId  = new CommandId();
		commandId.setId(arr[0]);
		commandId.setMiniApp(arr[1]);
		commandId.setSuperApp(arr[2]);
		return commandId;
	}
	
	
	
}

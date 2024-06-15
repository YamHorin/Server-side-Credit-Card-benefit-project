package Application;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import Application.DataAccess.EntityCommand;
import Application.DataAccess.EntityObject;
import Application.DataAccess.EntityUser;
import Application.DataAccess.Location;
import Application.DataAccess.RoleEnumEntity;
import Application.business_logic.BoundaryCommand;
import Application.business_logic.BoundaryObject;
import Application.business_logic.BoundaryUser;
import Application.business_logic.CommandId;
import Application.business_logic.CreatedBy;
import Application.business_logic.ObjectId;
import Application.business_logic.RoleEnumBoundary;
import Application.business_logic.UserId;


@Component
public class DataConvertor {

    public BoundaryUser EntityUserToBoundaryUser(EntityUser entity)
    {
    	BoundaryUser boun = new BoundaryUser();
    	RoleEnumBoundary role =   RoleEnumBoundary.valueOf(entity.getRole().name().toUpperCase());
    	boun.setRole(role);
    	boun.setAvatar(entity.getAvatar());
    	String email = entity.getId().split("_")[0];
    	String superAppName = entity.getId().split("_")[1];
    	boun.setUserName(entity.getUserName());
    	boun.setUserId(new UserId(superAppName, email));
    	return boun;
    }
    public  BoundaryObject EntityObjectTOBoundaryObject (EntityObject entity)
    {
    	BoundaryObject bounObj = new BoundaryObject();
    	bounObj.setActive(entity.getActive());
    	bounObj.setAlias(entity.getAlias());
    	String id = entity.getCreatedBy();
    	String email = id.split("_")[0];
    	String superAppName = id.split("_")[1];
    	bounObj.setCreatedBy(new CreatedBy(email, superAppName));
    	bounObj.setCreationTimeStamp(entity.getCreationTimeStamp());
    	bounObj.setLocation(new Location(entity.getLocation_lat() , entity.getLocation_lng()));
    	bounObj.setObjectDetails(entity.getObjectDetails());
    	ObjectId ObjectId = new ObjectId();
    	ObjectId.setId(entity.getObjectID().split("__")[0]);
    	ObjectId.setSuperApp(entity.getObjectID().split("__")[1]);
    	bounObj.setObjectID(ObjectId);
    	bounObj.setType(entity.getType());
    	return bounObj;
    	
    }
    public BoundaryCommand EntityCommandToBoundaryCommand (EntityCommand Entity)
	{
		BoundaryCommand boun = new BoundaryCommand();
		boun.setCommand(Entity.getCommand());
		boun.setCommandAttributes(Entity.getCommandAttributes());
		CommandId CommandId = new CommandId();
		CommandId.setId(Entity.getCommandId());
		CommandId.setMiniApp(Entity.getMiniAppName());
		boun.setCommandId(CommandId);
		boun.setInvocationTimeStamp(Entity.getInvocationTimeStamp());
		boun.setInvokedBy(Entity.getInvokedBy());
		boun.setTargetObject(Entity.getTargetObject());
		return boun;
	}
    
    
    public EntityCommand BoundaryCommandToEntityCommand(BoundaryCommand bCommand) {
        EntityCommand entity = new EntityCommand();
        entity.setCommand(bCommand.getCommand());
        entity.setCommandId(bCommand.getCommandId().getId());
        entity.setMiniAppName(bCommand.getCommandId().getMiniApp());
        entity.setTargetObject(bCommand.getTargetObject());
        entity.setCommandAttributes(bCommand.getCommandAttributes());
        entity.setInvokedBy(bCommand.getInvokedBy());
        entity.setInvocationTimeStamp(bCommand.getInvocationTimeStamp());
        return entity;
    }
    
    public EntityObject BoundaryObjectTOEntityObject(BoundaryObject bObject) {
    	EntityObject objectEntity = new EntityObject();
        objectEntity.setObjectID(bObject.getObjectID().getId() + "__" + bObject.getObjectID().getSuperApp());
        objectEntity.setType(bObject.getType());
        objectEntity.setCreationTimeStamp(bObject.getCreationTimeStamp());
        String email = bObject.getCreatedBy().getUserId().getEmail();
        String superApp = bObject.getCreatedBy().getUserId().getSuperAPP();
    	objectEntity.setCreatedBy(email+"_"+superApp);
        objectEntity.setActive(bObject.getActive() == null || bObject.getActive());
        objectEntity.setAlias(bObject.getAlias() == null ? "demo instance" : bObject.getAlias());
        objectEntity.setObjectDetails(bObject.getObjectDetails() == null ? new HashMap<>() : bObject.getObjectDetails());
        objectEntity.setLocation_lat(bObject.getLocation().getLat());
        objectEntity.setLocation_lng(bObject.getLocation().getLng());


        return objectEntity;
    }
    
	public EntityUser BoundaryUserTOEntityUser(BoundaryUser bUser) {
		EntityUser userEntity = new EntityUser();
		userEntity.setId(bUser.getUserId().getEmail() + "_" + bUser.getUserId().getSuperAPP());
		RoleEnumEntity  role = RoleEnumEntity.valueOf(bUser.getRole().name().toLowerCase());
		userEntity.setRole(role);
		userEntity.setUserName(bUser.getUserName());
		userEntity.setAvatar(bUser.getAvatar());
		return userEntity;

	}
}

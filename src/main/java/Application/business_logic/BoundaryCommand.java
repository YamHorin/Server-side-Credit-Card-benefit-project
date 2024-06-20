package Application.business_logic;

import java.util.Date;
import java.util.Map;

import Application.DataAccess.EntityCommand;

import java.util.Date;
import java.util.Map;

public class BoundaryCommand {

    private CommandId commandId;
    private String command;
    private TargetObject targetObject;
    private Date invocationTimeStamp;
    private CreatedBy invokedBy;
    private Map<String, Object> commandAttributes;

    public BoundaryCommand() {
    }

    public BoundaryCommand(EntityCommand entity) {
		BoundaryCommand boun = new BoundaryCommand();
		boun.setCommand(entity.getCommand());
		boun.setCommandAttributes(entity.getCommandAttributes());
		CommandId CommandId = new CommandId();
		CommandId.setId(entity.getCommandId());
		CommandId.setMiniApp(entity.getMiniAppName());
		boun.setCommandId(CommandId);
		boun.setInvocationTimeStamp(entity.getInvocationTimeStamp());
		
		String email = entity.getInvokedBy().split("_")[0];
    	String superAppName = entity.getInvokedBy().split("_")[1];
    	boun.setInvokedBy(new CreatedBy(new UserId(superAppName, email)));
		
		TargetObject targetObject = new TargetObject ();
		ObjectId ObjectId = new ObjectId();
    	ObjectId.setId(entity.getTargetObject().split("__")[0]);
    	ObjectId.setSuperApp(entity.getTargetObject().split("__")[1]);
    	targetObject.setObjectId(ObjectId);
		boun.setTargetObject(targetObject);
    }



    public CommandId getCommandId() {
        return commandId;
    }


    public void setCommandId(CommandId commandId) {
        this.commandId = commandId;
    }


    public String getCommand() {
        return command;
    }


    public void setCommand(String command) {
        this.command = command;
    }


    public TargetObject getTargetObject() {
        return targetObject;
    }


    public void setTargetObject(TargetObject targetObject2) {
        this.targetObject = targetObject2;
    }


    public Date getInvocationTimeStamp() {
        return invocationTimeStamp;
    }


    public void setInvocationTimeStamp(Date invocationTimeStamp) {
        this.invocationTimeStamp = invocationTimeStamp;
    }


    public CreatedBy getInvokedBy() {
        return invokedBy;
    }


    public void setInvokedBy(CreatedBy invokedBy) {
        this.invokedBy = invokedBy;
    }


    public Map<String, Object> getCommandAttributes() {
        return commandAttributes;
    }


    public void setCommandAttributes(Map<String, Object> commandAttributes) {
        this.commandAttributes = commandAttributes;
    }


    @Override
    public String toString() {
        return "MiniAppCommandBoundary{" +
                "commandId=" + commandId +
                ", command='" + command + '\'' +
                ", targetObject=" + targetObject +
                ", invocationTimeStamp=" + invocationTimeStamp +
                ", invokedBy=" + invokedBy +
                ", commandAttributes=" + commandAttributes +
                '}';
    }

}

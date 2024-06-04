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
        String[] splitId = entity.getCommandId().split("_");
        this.commandId = new CommandId();
        this.getCommandId().setSuperApp(splitId[0]);
        this.getCommandId().setMiniApp(splitId[1]);
        this.getCommandId().setId(splitId[2]);
        this.setCommand(entity.getCommand());
        this.setTargetObject(entity.getTargetObject());
        this.setInvocationTimeStamp(entity.getInvocationTimeStamp());
        this.setInvokedBy(entity.getInvokedBy());
        this.setCommandAttributes(entity.getCommandAttributes());
    }

    public EntityCommand toEntity() {
        EntityCommand entity = new EntityCommand();
        entity.setCommand(this.getCommand());
        entity.setCommandId(this.getCommandId().getId());
        entity.setMiniAppName(this.getCommandId().getMiniApp());
        entity.setTargetObject(this.getTargetObject());
        entity.setCommandAttributes(this.getCommandAttributes());
        entity.setInvokedBy(this.getInvokedBy());
        entity.setInvocationTimeStamp(this.getInvocationTimeStamp());
        return entity;
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

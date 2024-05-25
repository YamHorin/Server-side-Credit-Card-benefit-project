package Entities;

import java.util.Date;
import UserFiles.*;
import java.util.Map;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

//@Entity
//@Table(name="COMMANDS")

import java.util.HashMap;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "DEMO_TBL")
public class EntityCommand {
	
	@Id
	private String commandId;
	private String miniAppName;
	private String command;
	@Transient
	private TargetObject targetObject;
	private Date invocationTimeStamp;
	@Transient
	private CreatedBy invokedBy;
	@Transient
	private Map<String, Object> commandAttributes;
	
	
	public EntityCommand() {}



	public String getCommandId() {
		return commandId;
	}


	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}


	public String getCommand() {
		return command;
	}

	public String getMiniAppName() {
		return miniAppName;
	}

	public void setMiniAppName(String miniAppName) {
		this.miniAppName = miniAppName;
	}

	public void setCommand(String command) {
		this.command = command;
	}


	public TargetObject getTargetObject() {
		return targetObject;
	}


	public void setTargetObject(TargetObject targetObject) {
		this.targetObject = targetObject;
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
		return "MiniAppCommandEntity{" +
				"commandId='" + commandId + '\'' +
				", miniAppName='" + miniAppName + '\'' +
				", command='" + command + '\'' +
				", targetObject=" + targetObject +
				", invocationTimeStamp=" + invocationTimeStamp +
				", invokedBy=" + invokedBy +
				", commandAttributes=" + commandAttributes +
				'}';
	}

}

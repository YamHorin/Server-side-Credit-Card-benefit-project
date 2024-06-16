package Application.DataAccess;

import java.util.Date;
import java.util.Map;


import Application.business_logic.CreatedBy;
import Application.business_logic.TargetObject;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

//@Entity
//@Table(name="COMMANDS")


import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "COMMAND_TBL")
public class EntityCommand {
	
	@Id
	private String commandId;
	
	private String miniAppName;
	private String command;
	
	//TODO to change to a string 
	@Transient
	private TargetObject targetObject;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date invocationTimeStamp;
	
	//TODO to change to a string 
	@Transient
	private CreatedBy invokedBy;
	
	
	@Lob
	@Convert(converter = ConverterBetweenMapAndString.class)
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
		return "\nMiniAppCommandEntity{" +
				"\ncommandId='" + commandId + '\'' +
				",\n miniAppName='" + miniAppName + '\'' +
				", \ncommand='" + command + '\'' +
				", \ntargetObject=" + targetObject +
				", \ninvocationTimeStamp=" + invocationTimeStamp +
				", \ninvokedBy=" + invokedBy +
				", \ncommandAttributes=" + commandAttributes +
				'}';
	}
	
	
}

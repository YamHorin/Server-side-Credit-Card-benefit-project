package Application.business_logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Application.DataAccess.EntityCommand;
import Application.DataAccess.MiniAppCommadDao;


@Service
public class DataAccess_Command implements servicesCommand{
	private MiniAppCommadDao miniAppCommandDao;
	private String name_super_app;

	public void setDefaultSuperAppName(String name_super_app) {
		System.err.println("**** reading from configuration default super app name: " + name_super_app);
		this.name_super_app = name_super_app;
	}

	public DataAccess_Command(MiniAppCommadDao miniAppCommandDao) {
		this.miniAppCommandDao = miniAppCommandDao;
		setDefaultSuperAppName("not important because it gets info from the configuration default");

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
		//ToDo
		CommandId command = new CommandId();
		command.setSuperApp(this.name_super_app);
		command.setMiniApp("null because we don't have mini app yet");
		command.setId(UUID.randomUUID().toString());
		CommandBoundary.setCommandId(command);
		CommandBoundary.setInvocationTimeStamp(new Date());
		EntityCommand entity = CommandBoundary.toEntity();
		entity = this.miniAppCommandDao.save(entity);
		BoundaryCommand rv  = entity.toBoudary(entity);
		System.err.println("* server stored: " + rv);
		return rv;
	}
	@Override
	@Transactional(readOnly = false)

	public void deleteAllminiAppCommandes() {
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
		if (update.getTargetObject()!=null)
	        entity.setTargetObject(update.getTargetObject());
		
		if (update.getCommandAttributes()!=null)
			entity.setCommandAttributes(update.getCommandAttributes());
		
		if (update.getInvokedBy()!=null)
			entity.setInvokedBy(update.getInvokedBy());
		
		if (update.getInvocationTimeStamp()!=null)
	        entity.setInvocationTimeStamp(update.getInvocationTimeStamp());
		
		entity = this.miniAppCommandDao.save(entity);
		System.err.println("command has been updated: * " + entity);   
		
	}


	
	
}

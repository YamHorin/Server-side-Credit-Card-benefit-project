package Application.business_logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Application.DataAccess.EntityCommand;
import Application.DataAccess.MiniAppCommadDao;


@Service
public class DataAccess_Command implements servicesCommand{
	private MiniAppCommadDao miniAppCommandDao;
	private String superAppName;
	
	@Value("${spring.application.name:Jill}")
    public void setsuperAppName(String superApp) {
		System.err.println("**** reading from configuration default superAppName: " + superApp);
        this.superAppName = superApp;
    }

	public DataAccess_Command(MiniAppCommadDao miniAppCommandDao) {
		this.miniAppCommandDao = miniAppCommandDao;
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
		CommandId command = new CommandId();
		command.setSuperApp(this.superAppName);
		command.setMiniApp(CommandBoundary.getCommandId().getMiniApp());
		command.setId(UUID.randomUUID().toString());
		CommandBoundary.setCommandId(command);
		CommandBoundary.setInvocationTimeStamp(new Date());
		EntityCommand entity = CommandBoundary.toEntity();
		entity = this.miniAppCommandDao.save(entity);
		System.err.println("***\n"+entity.toString()+"\n\n\n");
		BoundaryCommand rv  = entity.toBoudary(entity);
		command = rv.getCommandId();
		command.setSuperApp(this.superAppName);
		rv.setCommandId(command);
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
	@Transactional(readOnly = true)
	public List<BoundaryCommand> get_All_Mini_App_Commands(String id) {
		List<EntityCommand> entities = this.miniAppCommandDao.findAllByminiAppName(id);
		List<BoundaryCommand> boundaries = new ArrayList<>();
		for (EntityCommand entity : entities) {
			boundaries.add(entity.toBoudary(entity));
		}
		
		System.err.println("* data from database: " + boundaries);
		return boundaries;
	}





	
	
}

package Application.business_logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Application.DataConvertor;
import Application.DataAccess.EntityCommand;
import Application.DataAccess.EntityUser;
import Application.DataAccess.MiniAppCommadDao;
import Application.DataAccess.RoleEnumEntity;
import Application.DataAccess.UserDao;
import Application._a_Presentation.BoundaryIsNotFoundException;
import Application._a_Presentation.UnauthorizedException;


@Service
public class DataManagerCommand implements ServicesCommand{
	private MiniAppCommadDao miniAppCommandDao;
	private UserDao userDao;
	private String superAppName;
	private String miniAppNameDefault;
	private DataConvertor DataConvertor;
	
	
	
	public DataManagerCommand(UserDao userDao, MiniAppCommadDao miniAppCommandDao , DataConvertor DataConvertor) {
		this.miniAppCommandDao = miniAppCommandDao;
		this.userDao = userDao;
		this.DataConvertor = DataConvertor;
	}

	@Value("${spring.application.name:Jill}")
    public void setsuperAppName(String superApp) {
		System.err.println("**** reading from configuration default superAppName: " + superApp);
        this.superAppName = superApp;

    }

	
	@Override
	@Transactional(readOnly = true)

	public Optional<BoundaryCommand> getSpecificMiniAppCommand(String id) {
		Optional <EntityCommand> entityCommand = this.miniAppCommandDao.findById(id);
		Optional<BoundaryCommand> boundaryCommand = entityCommand.map(this.DataConvertor::EntityCommandToBoundaryCommand);
		if (boundaryCommand.isEmpty())
			System.err.println("* no mini app command to return");
		else
			System.out.println(boundaryCommand.toString());
		return boundaryCommand;
	}
	
//	old //
//	@Override
//	@Transactional(readOnly = true)
//
//	public List<BoundaryCommand> getAllMiniAppCommands(String id, int page, int size) {
//		List<EntityCommand> entities = this.miniAppCommandDao.findAll();
//		List<BoundaryCommand> boundaries = new ArrayList<>();
//		for (EntityCommand entity : entities) {
//			boundaries.add(this.DataConvertor.EntityCommandToBoundaryCommand(entity));
//		}
//		
//		System.err.println("* data from database: " + boundaries);
//		return boundaries;
//	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<BoundaryCommand> getAllMiniAppsCommands(String id, int page, int size) {
		EntityUser userEntity = this.userDao.findById(id).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id));
		RoleEnumEntity role = userEntity.getRole();
		if (role != RoleEnumEntity.adm_user)
			throw new UnauthorizedException("only admin users can get all miniApp commandes..");
		else {
			return this.miniAppCommandDao
					.findAll(PageRequest.of(page, size, Direction.DESC, "messageTimestamp", "id"))
					.stream()
					.map(this.DataConvertor::EntityCommandToBoundaryCommand)
					.peek(System.err::println)
					.toList();			
		}
	}
	
	
//	old		
//	@Override
//	@Transactional(readOnly = true)
//	//get all mini app commands by a specific mini app 
//	public List<BoundaryCommand> getAllCommandsOfSpecificMiniApp(String id, String idUser) {
//		List<EntityCommand> entities = this.miniAppCommandDao.findAllByminiAppName(id);
//		List<BoundaryCommand> boundaries = new ArrayList<>();
//		for (EntityCommand entity : entities) {
//			boundaries.add(this.DataConvertor.EntityCommandToBoundaryCommand(entity));
//		}
//		
//		System.err.println("* data from database: " + boundaries);
//		return boundaries;
//	}
	
//	@Override
//	@Deprecated
//	public List<BoundaryCommand> getAllCommandsOfSpecificMiniApp() {
//		throw new DemoDeprecationException("You should not invoke this method, use the pagaination supporting method instead");
//	}
	
	@Override
	@Transactional(readOnly = true)
	//get all mini app commands by a specific mini app 
	public List<BoundaryCommand> getAllCommandsOfSpecificMiniApp(String id, String idUser, int page, int size) {
		EntityUser userEntity = this.userDao.findById(idUser).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + idUser));
		RoleEnumEntity role = userEntity.getRole();
		
		if (role != RoleEnumEntity.adm_user)
			throw new UnauthorizedException("only admin users can get all commands of specific MiniApp");
		else {
			return this.miniAppCommandDao
					.findAllByminiAppName(PageRequest.of(page, size, Direction.DESC, "messageTimestamp", "id"))
					.stream()
					.map(this.DataConvertor::EntityCommandToBoundaryCommand)
					.peek(System.err::println)
					.toList();			
		}
	}
	
	@Override
	@Transactional(readOnly = false)

	public BoundaryCommand createMiniAppCommand(BoundaryCommand CommandBoundary) {
		System.err.println("* client requested to store: " + CommandBoundary);
		CommandId command = new CommandId();
		command.setSuperApp(this.superAppName);
		command.setMiniApp(this.miniAppNameDefault);
		command.setId(UUID.randomUUID().toString());
		CommandBoundary.setCommandId(command);
		CommandBoundary.setInvocationTimeStamp(new Date());
		EntityCommand entity = this.DataConvertor.BoundaryCommandToEntityCommand(CommandBoundary);
		entity = this.miniAppCommandDao.save(entity);
		System.err.println("***\n"+entity.toString()+"\n\n\n");
		BoundaryCommand rv  = DataConvertor.EntityCommandToBoundaryCommand(entity);
		command = rv.getCommandId();
		command.setSuperApp(this.superAppName);
		rv.setCommandId(command);
		System.err.println("* server stored: " + rv);
		return rv;
	}
	
	
	
	@Override
	@Transactional(readOnly = false)

	public void deleteAllminiAppCommandes(String id) {
		EntityUser userEntity = this.userDao.findById(id).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id));
		RoleEnumEntity role = userEntity.getRole();
		if (role != RoleEnumEntity.adm_user)
			throw new UnauthorizedException("only admin users can delet all miniApp commandes..");
		else {			
			System.err.println("* deleting table for mini app commands :)");
			this.miniAppCommandDao.deleteAll();
		}		
	}

	
	@Value("${spring.MiniAppName:Jill}")
	public void setMiniAppNameDefault(String miniAppNameDefault) {
		System.err.println("**** reading from configuration default mini app: " + miniAppNameDefault);
		this.miniAppNameDefault = miniAppNameDefault;
	}





	
	
}

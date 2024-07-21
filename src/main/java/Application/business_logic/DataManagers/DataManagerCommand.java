package Application.business_logic.DataManagers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Application.DataConvertor;
import Application.DataAccess.Dao.MiniAppCommadDao;
import Application.DataAccess.Dao.ObjDao;
import Application.DataAccess.Dao.UserDao;
import Application.DataAccess.Entities.EntityCommand;
import Application.DataAccess.Entities.EntityObject;
import Application.DataAccess.Entities.EntityUser;
import Application.DataAccess.Entities.RoleEnumEntity;
import Application._a_Presentation.Exceptions.BoundaryIsNotFilledCorrectException;
import Application._a_Presentation.Exceptions.BoundaryIsNotFoundException;
import Application._a_Presentation.Exceptions.UnauthorizedException;
import Application.business_logic.Boundaies.MiniAppCommandBoundary;
import Application.business_logic.Boundaies.ObjectBoundary;
import Application.business_logic.DataService.ServicesCommand;
import Application.business_logic.javaObjects.CommandId;
import Application.business_logic.javaObjects.ObjectId;
import Application.business_logic.javaObjects.TargetObject;
import Application.logic.MiniappInterface;


@Service
public class DataManagerCommand implements ServicesCommand{
	private MiniAppCommadDao miniAppCommandDao;
	private UserDao userDao;
	private ObjDao objDao;
	private String superAppName;
	private String miniAppNameDefault;
	private DataConvertor DataConvertor;
	private ApplicationContext applicationContext;
	
	
	
	public DataManagerCommand( ApplicationContext applicationContext
			, UserDao userDao
			, MiniAppCommadDao miniAppCommandDao 
			, DataConvertor DataConvertor , ObjDao objDao) {
		this.miniAppCommandDao = miniAppCommandDao;
		this.userDao = userDao;
		this.DataConvertor = DataConvertor;
		this.objDao  = objDao;
		this.applicationContext = applicationContext;
	}

	@Value("${spring.application.name:Jill}")
    public void setsuperAppName(String superApp) {
		System.err.println("**** reading from configuration default superAppName: " + superApp);
        this.superAppName = superApp;

    }

	
	@Override
	@Transactional(readOnly = true)

	public Optional<MiniAppCommandBoundary> getSpecificMiniAppCommand(String id) {
		Optional <EntityCommand> entityCommand = this.miniAppCommandDao.findById(id);
		Optional<MiniAppCommandBoundary> boundaryCommand = entityCommand.map(this.DataConvertor::EntityCommandToBoundaryCommand);
		if (boundaryCommand.isEmpty())
			System.err.println("* no mini app command to return");
		else
			System.out.println(boundaryCommand.toString());
		return boundaryCommand;
	}
	

	
	@Override
	@Transactional(readOnly = true)
	public List<MiniAppCommandBoundary> getAllMiniAppsCommands(String id, int page, int size) {
		EntityUser userEntity = this.userDao.findById(id).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id));
		RoleEnumEntity role = userEntity.getRole();
		if (role != RoleEnumEntity.adm_user)
			throw new UnauthorizedException("only admin users can get all miniApp commandes..");
		else {
			return this.miniAppCommandDao
					.findAll(PageRequest.of(page, size, Direction.DESC, "invocationTimeStamp","commandId"))
					.stream()
					.map(this.DataConvertor::EntityCommandToBoundaryCommand)
					.peek(System.err::println)
					.toList();			
		}
	}
	
	
	
	@Override
	@Transactional(readOnly = true)
	//get all mini app commands by a specific mini app 
	public List<MiniAppCommandBoundary> getAllCommandsOfSpecificMiniApp(String id, String idUser, int page, int size) {
		EntityUser userEntity = this.userDao.findById(idUser).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + idUser));
		RoleEnumEntity role = userEntity.getRole();
		
		if (role != RoleEnumEntity.adm_user)
			throw new UnauthorizedException("only admin users can get all commands of specific MiniApp");
		else {
			return this.miniAppCommandDao
					.findAllByMiniAppName(id , PageRequest.of(page, size, Direction.DESC, "invocationTimeStamp", "commandId"))
					.stream()
					.map(this.DataConvertor::EntityCommandToBoundaryCommand)
					.peek(System.err::println)
					.toList();			
		}
	}
	
	@Override
	@Transactional(readOnly = false)

	public ObjectBoundary[] createMiniAppCommand(MiniAppCommandBoundary CommandBoundary ,String idMiniAppName) {
		//checks if the object that the command does is in the table and if the user is in the table
		String idObj = CommandBoundary.getTargetObject().getObjectId().getId()+"__"+this.superAppName;
		EntityObject EntityObject = this.objDao.findById(idObj).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find object for command by id: " + idObj));
		String idUser = CommandBoundary.getInvokedBy().getUserId().getEmail() +" "+ CommandBoundary.getInvokedBy().getUserId().getSuperapp();
		EntityUser userEntity = this.userDao.findById(idUser).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for command by id: " + idUser));
		//from eyal notes 7.7
		if (userEntity.getRole()!= RoleEnumEntity.miniapp_user)
			throw new UnauthorizedException("only mini app users can make mini app commands...");
		if (EntityObject.getActive()==false)
			throw new BoundaryIsNotFilledCorrectException("Target Object must be active....");
		
		CommandId command = new CommandId();
		command.setSuperapp(this.superAppName);
		command.setMiniapp(idMiniAppName);
		command.setId(UUID.randomUUID().toString());
		
		CommandBoundary.setCommandId(command);
		CommandBoundary.setInvocationTimeStamp(new Date());
		ObjectId obj = CommandBoundary.getTargetObject().getObjectId();
		obj.setSuperapp(superAppName);
		CommandBoundary.setTargetObject(new TargetObject(obj));
		
		//new func by Yam  :)
		
		EntityCommand entity = this.DataConvertor.BoundaryCommandToEntityCommand(CommandBoundary);
		entity = this.miniAppCommandDao.save(entity);
		System.err.println("***\n"+entity.toString()+"\n\n\n");
		MiniAppCommandBoundary rv  = DataConvertor.EntityCommandToBoundaryCommand(entity);
		command = rv.getCommandId();
		command.setSuperapp(this.superAppName);
		rv.setCommandId(command);
		System.err.println("* server stored: " + rv);
		return invokeCommand(CommandBoundary);
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

	public ObjectBoundary[] invokeCommand(MiniAppCommandBoundary CommandBoundary)
	{
		MiniappInterface app = null;
		String command = CommandBoundary.getCommandId().getMiniapp()+"_"+CommandBoundary.getCommand();
		System.err.println("command = "+CommandBoundary.getCommand());
		System.err.println("miniApp = "+CommandBoundary.getCommandId().getMiniapp());
		try {
			app = this.applicationContext.getBean( command, MiniappInterface.class);
		} catch (Exception e) {
			throw new BoundaryIsNotFilledCorrectException(" command is not found!!!");
		}
		ObjectBoundary[] r = app.activateCommand(CommandBoundary).toArray(new ObjectBoundary[0]);
				return r;
	}



	
	
}

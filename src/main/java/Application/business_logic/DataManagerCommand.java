package Application.business_logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Application.DataConvertor;
import Application.DataAccess.EntityCommand;
import Application.DataAccess.EntityObject;
import Application.DataAccess.EntityUser;
import Application.DataAccess.MiniAppCommadDao;
import Application.DataAccess.ObjDao;
import Application.DataAccess.RoleEnumEntity;
import Application.DataAccess.UserDao;
import Application._a_Presentation.BoundaryIsNotFoundException;
import Application._a_Presentation.UnauthorizedException;


@Service
public class DataManagerCommand implements ServicesCommand{
	private MiniAppCommadDao miniAppCommandDao;
	private UserDao userDao;
	private ObjDao objDao;
	private String superAppName;
	private String miniAppNameDefault;
	private DataConvertor DataConvertor;
	
	
	
	public DataManagerCommand(UserDao userDao, MiniAppCommadDao miniAppCommandDao , DataConvertor DataConvertor , ObjDao objDao) {
		this.miniAppCommandDao = miniAppCommandDao;
		this.userDao = userDao;
		this.DataConvertor = DataConvertor;
		this.objDao  = objDao;
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
					.findAll(PageRequest.of(page, size, Direction.DESC, "invocationTimeStamp","commandId"))
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
					.findAllByMiniAppName(id , PageRequest.of(page, size, Direction.DESC, "invocationTimeStamp", "commandId"))
					.stream()
					.map(this.DataConvertor::EntityCommandToBoundaryCommand)
					.peek(System.err::println)
					.toList();			
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	// hare we write the mathods of the mini app, in stwich case of the name of the mini app, 
	// of each miniApp we create  switch case of all the command of specific mimiApp
	public BoundaryCommand createMiniAppCommand(BoundaryCommand CommandBoundary) {
		//checks if the object that the command does is in the table and if the user is in the table
		String idObj = CommandBoundary.getTargetObject().getObjectId().getId()+"__"+this.superAppName;
		EntityObject EntityObject = this.objDao.findById(idObj).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find object for command by id: " + idObj));
		String idUser = CommandBoundary.getInvokedBy().getUserId().getEmail() +"_"+ CommandBoundary.getInvokedBy().getUserId().getSuperAPP();
		EntityUser userEntity = this.userDao.findById(idUser).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for command by id: " + idUser));
		RoleEnumEntity role = userEntity.getRole();
		System.err.println("* client requested to store: " + CommandBoundary);
		CommandId command = new CommandId();
		command.setSuperApp(this.superAppName);
		command.setMiniApp(this.miniAppNameDefault);
		command.setId(UUID.randomUUID().toString());
		CommandBoundary.setCommandId(command);
		CommandBoundary.setInvocationTimeStamp(new Date());
		ObjectId obj = CommandBoundary.getTargetObject().getObjectId();
		obj.setSuperApp(superAppName);
		CommandBoundary.setTargetObject(new TargetObject(obj));
		switch (CommandBoundary.getCommandId().getMiniApp()){
			case "getYourBenefits":
				switch (CommandBoundary.getCommand())
				{
					case "ShowStoresWithDiscountInCreditcard": {
						ShowStoresWithDiscountInCreditcard(userEntity,EntityObject);
						break;
					}
					case "ShowStoresWithDiscountInClub":{
						ShowStoresWithDiscountInClub(userEntity,EntityObject);
						break;
					}				
				}
				break;
			case "StoreInterface":
				switch (CommandBoundary.getCommand())
				{
					case "ShowAllBenefit": {
						ShowAllBenefit( userEntity, EntityObject);
						break;
					}
					case "AddBenefitOfClub":{
						AddBenefitOfClub(userEntity, EntityObject);
						break;
					}
					case "AddBenefitOfCreditcard":{
						AddBenefitOfCreditcard(userEntity, EntityObject);
						break;
					}		
					
				}
				break;						
		}
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
	
	
	
	private void ShowStoresWithDiscountInClub(EntityUser userEntity, EntityObject entityObject) {
		// // miniApp getYourBenefits command ShowStoresWithDiscountInClub
		//entityObject -the client

		// list of the clubs of the client play the function
		List<String> clubs = (List<String>) entityObject.getObjectDetails().get("Club");
		// list of the store that active
		List <EntityObject> stores = this.objDao
		.findAllBytype("store",PageRequest.of(0, 10, Direction.DESC, "invocationTimeStamp", "commandId"))
		.stream()
		.filter(EntityObject::getActive).toList();
		// find for each store if there is the credit card - yes=show, no=remove
		for (EntityObject store : stores) {
			if (!clubs.contains(store.getObjectDetails().get("Club")))
				stores.remove(store);
		}
		//TODO find who to print the app filter list to the client
	}

	private void ShowStoresWithDiscountInCreditcard(EntityUser userEntity, EntityObject entityObject) {
		// miniApp getYourBenefits command ShowStoresWithDiscountInCreditcard
		//entityObject -the client
		
		// list of the cards of the client play the function
		List<String> cards = (List<String>) entityObject.getObjectDetails().get("CreaditCard");
		// list of the store that active
		//TODO add opsion of number of store to show -right now it's only 10
		List <EntityObject> stores = this.objDao
		.findAllBytype("store",PageRequest.of(0, 10, Direction.DESC, "invocationTimeStamp", "commandId"))
		.stream()
		.filter(EntityObject::getActive).toList();
		// find for each store if there is the credit card - yes=show, no=remove
		for (EntityObject store : stores) {
			if (!cards.contains(store.getObjectDetails().get("CreaditCard")))
				stores.remove(store);
		}
		//TODO find who to print the app filter list to the client

	}

	private void AddBenefitOfCreditcard(EntityUser userEntity, EntityObject entityObject) {
		// miniApp StoreInterface command AddBenefitOfCreditcard
		
		
	}

	private void AddBenefitOfClub(EntityUser userEntity, EntityObject entityObject) {
		// miniApp StoreInterface command AddBenefitOfClub
		
	}

	private void ShowAllBenefit(EntityUser userEntity, EntityObject entityObject) {
		// miniApp StoreInterface command ShowAllBenefit
		//show all benefits that has the store in it 
		//entityObject  = the store we wish to get all stores
//		String storeName = entityObject.getAlias();
//		this.objDao.findAllBytypeAndActiveIsTrue("Benefit", PageRequest.of(0, 10, Direction.DESC, "invocationTimeStamp"))
//		.stream().filter
		
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

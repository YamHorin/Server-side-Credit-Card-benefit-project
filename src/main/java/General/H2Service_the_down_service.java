package General;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import General.Data_Checks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import MIniAppFiles.MiniAppCommand_Boundary;
import MIniAppFiles.miniAppCommandDao;
import StoreFiles.StoreDao;
import StoreFiles.Store_Boundary;
import UserFiles.RoleEnumBoundary;
import UserFiles.UserDao;
import UserFiles.User_Boundary;
import UserFiles.User_Entity;
import UserFiles.User_Id;

public class H2Service_the_down_service implements DataService_Business_logic {
	private StoreDao StoreDao;
	private UserDao UserDao;
	private miniAppCommandDao miniAppCommandDao;
	
	//get from config data
	private String name_super_app;
	
	//converter data from Boundary to Entity 
	
	private DataConverter DataConverter;

	public H2Service_the_down_service(StoreFiles.StoreDao storeDao, UserFiles.UserDao userDao,
			MIniAppFiles.miniAppCommandDao miniAppCommandDao, General.DataConverter dataConverter) {
		StoreDao = storeDao;
		UserDao = userDao;
		this.miniAppCommandDao = miniAppCommandDao;
		DataConverter = dataConverter;
		setDefaultFirstName("not important because it gets info from the configuration default");
	}
	@Value("${spring.application.name:SuperApppp}")
	public void setDefaultFirstName(String name_super_app) {
		System.err.println("**** reading from configuration default super app name: " + name_super_app);
		this.name_super_app = name_super_app;
	}
	@Override
	@Transactional(readOnly = true)
	public Optional<User_Boundary> getSpecificUser(String id) {
		Optional <User_Entity> enitityUser = this.UserDao.findById(id);
		Optional <User_Boundary> boundaryUser = enitityUser.map(this.DataConverter::UserToBoundry);
		if (boundaryUser.isEmpty()==true)
			System.out.println("no users to return please check the id");
		else
			System.err.println(boundaryUser.toString());
		return boundaryUser;
				
			
	}
	@Override
	public List<User_Boundary> getAllUsers() {
		List<User_Entity> entities = this.UserDao.findAll();
		
		List <User_Boundary> boundaries_users = new ArrayList<>();
		for (User_Entity user_Entity : entities) {
			boundaries_users.add(this.DataConverter.UserToBoundry(user_Entity));
			System.out.println("got data user: "+user_Entity.toString());
		}
		return boundaries_users;
	}
	@Override
	public User_Boundary createUser(User_Boundary UserBoundary) {
		System.out.println("we get a new user to out data set: \n\n\n "+UserBoundary.toString());
		if(UserBoundary.getUser_id()==null)
			UserBoundary.setUser_id(new User_Id());
		UserBoundary.getUser_id().setSuperAPP(this.name_super_app);
		if (UserBoundary.getUser_id().getEmail()!=null)
		{
			//to do 
//			if(isValidEmail(UserBoundary.getUser_id().getEmail())==false)
//				throw new RuntimeException("not valid email");		
		}
		if (UserBoundary.getRole()==null)
			UserBoundary.setRole(RoleEnumBoundary.UNDETERMINED);
			
	}
	@Override
	public void deleteAllUsers() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateUser(String id, User_Boundary update) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Optional<Store_Boundary> getSpecificStore(String id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	@Override
	public List<Store_Boundary> getAllStores() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Store_Boundary createStore(Store_Boundary StoreBoundary) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void deleteAllStores() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateStore(String id, Store_Boundary update) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Optional<MiniAppCommand_Boundary> getSpecificMiniAppCommand(String id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	@Override
	public List<MiniAppCommand_Boundary> getAllMiniAppCommands() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public MiniAppCommand_Boundary createMiniAppCommand(MiniAppCommand_Boundary StoreBoundary) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void deleteAlminiAppCommandes() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateminiAppCommand(String id, MiniAppCommand_Boundary update) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}

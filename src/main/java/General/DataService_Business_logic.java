package General;

import java.util.List;
import java.util.Optional;

import MIniAppFiles.MiniAppCommand_Boundary;
import StoreFiles.Store_Boundary;
import UserFiles.User_Boundary;

public interface DataService_Business_logic  {
	//user
	public Optional<User_Boundary> getSpecificUser(String id);
	public List<User_Boundary> getAllUsers();
	
	public User_Boundary createUser (User_Boundary UserBoundary);
	
	public void deleteAllUsers ();
	
	public void updateUser (String id, User_Boundary update);
	
	//store
	public Optional<Store_Boundary> getSpecificStore(String id);
	public List<Store_Boundary> getAllStores();
	
	public Store_Boundary createStore (Store_Boundary StoreBoundary);
	
	public void deleteAllStores ();
	
	public void updateStore (String id, Store_Boundary update);
	
	//miniAppCommands
	
	
	public Optional<MiniAppCommand_Boundary> getSpecificMiniAppCommand(String id);
	public List<MiniAppCommand_Boundary> getAllMiniAppCommands();
	
	public MiniAppCommand_Boundary createMiniAppCommand (MiniAppCommand_Boundary StoreBoundary);
	
	public void deleteAlminiAppCommandes ();
	
	public void updateminiAppCommand (String id, MiniAppCommand_Boundary update);
	
	
	
}

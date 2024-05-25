package General;

import java.util.List;
import java.util.Optional;

import Boundary.BoundaryCommand;
import Boundary.BoundaryObject;
import Boundary.BoundaryUser;


public interface DataService_Business_logic  {
	//user
	public Optional<BoundaryUser> getSpecificUser(String id);
	public List<BoundaryUser> getAllUsers();
	
	public BoundaryUser createUser (BoundaryUser UserBoundary);
	
	public void deleteAllUsers ();
	
	public void updateUser (String id, BoundaryUser update);
	
	//obj
	public Optional<BoundaryObject> getSpecificObj(String id);
	public List<BoundaryObject> getAllObjects();
	
	public	BoundaryObject createObject (BoundaryObject StoreBoundary);
	
	public void deleteAllObjs ();
	
	public void updateObj (String id, BoundaryObject update);
	
	//miniAppCommands
	
	
	public Optional<BoundaryCommand> getSpecificMiniAppCommand(String id);
	public List<BoundaryCommand> getAllMiniAppCommands();
	
	public BoundaryCommand createMiniAppCommand (BoundaryCommand StoreBoundary);
	
	public void deleteAlminiAppCommandes ();
	
	public void updateminiAppCommand (String id, BoundaryCommand update);
	
	
	
}

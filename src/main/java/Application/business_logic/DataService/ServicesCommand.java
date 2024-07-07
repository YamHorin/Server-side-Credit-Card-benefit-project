package Application.business_logic.DataService;


import java.util.List;
import java.util.Optional;

import Application.business_logic.Boundaies.BoundaryCommand;
import Application.business_logic.Boundaies.BoundaryObject;



public interface ServicesCommand {
	public Optional<BoundaryCommand> getSpecificMiniAppCommand(String id);
	public List<BoundaryCommand> getAllMiniAppsCommands(String id, int page, int size);
	
	public BoundaryObject [] createMiniAppCommand (BoundaryCommand StoreBoundary , String idMiniAppName);
	
	public void deleteAllminiAppCommandes (String id);
	public List<BoundaryCommand> getAllCommandsOfSpecificMiniApp(String id, String idUser,  int page, int size);
	
}

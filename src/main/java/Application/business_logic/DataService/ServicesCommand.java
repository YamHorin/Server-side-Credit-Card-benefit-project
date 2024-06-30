package Application.business_logic.DataService;


import java.util.List;
import java.util.Optional;

import Application.business_logic.Boundaies.BoundaryCommand;



public interface ServicesCommand {
	public Optional<BoundaryCommand> getSpecificMiniAppCommand(String id);
	public List<BoundaryCommand> getAllMiniAppsCommands(String id, int page, int size);
	
	public BoundaryCommand createMiniAppCommand (BoundaryCommand StoreBoundary);
	
	public void deleteAllminiAppCommandes (String id);
	public List<BoundaryCommand> getAllCommandsOfSpecificMiniApp(String id, String idUser,  int page, int size);
	
}

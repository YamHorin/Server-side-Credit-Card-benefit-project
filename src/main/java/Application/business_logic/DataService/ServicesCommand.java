package Application.business_logic.DataService;


import java.util.List;
import java.util.Optional;

import Application.business_logic.Boundaies.MiniAppCommandBoundary;



public interface ServicesCommand {
	public Optional<MiniAppCommandBoundary> getSpecificMiniAppCommand(String id);
	public List<MiniAppCommandBoundary> getAllMiniAppsCommands(String id, int page, int size);
	
	public Object createMiniAppCommand (MiniAppCommandBoundary StoreBoundary , String idMiniAppName);
	
	public void deleteAllminiAppCommandes (String id);
	public List<MiniAppCommandBoundary> getAllCommandsOfSpecificMiniApp(String id, String idUser,  int page, int size);
	
}

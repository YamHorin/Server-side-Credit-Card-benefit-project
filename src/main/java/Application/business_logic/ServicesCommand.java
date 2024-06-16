package Application.business_logic;


import java.util.List;
import java.util.Optional;



public interface ServicesCommand {
	public Optional<BoundaryCommand> getSpecificMiniAppCommand(String id);
	public List<BoundaryCommand> getAllMiniAppCommands(String id);
	
	public BoundaryCommand createMiniAppCommand (BoundaryCommand StoreBoundary);
	
	public void deleteAllminiAppCommandes (String id);
	public List<BoundaryCommand> get_All_Mini_App_Commands(String id, String idUser);
	
}

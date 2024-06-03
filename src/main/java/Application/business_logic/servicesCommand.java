package Application.business_logic;


import java.util.List;
import java.util.Optional;



public interface servicesCommand {
	public Optional<BoundaryCommand> getSpecificMiniAppCommand(String id);
	public List<BoundaryCommand> getAllMiniAppCommands();
	
	public BoundaryCommand createMiniAppCommand (BoundaryCommand StoreBoundary);
	
	public void deleteAllminiAppCommandes ();
	
}

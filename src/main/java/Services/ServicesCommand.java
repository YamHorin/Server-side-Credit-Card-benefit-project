package Services;


import java.util.List;
import java.util.Optional;

import Boundary.BoundaryCommand;

public interface ServicesCommand {
	public Optional<BoundaryCommand> getSpecificDemo(String id);
	public List<BoundaryCommand> getAllDemoes();
	
	public BoundaryCommand createDemo (BoundaryCommand boundaryCommand);
	
	public void deleteAllDemoes ();
	
	public void updateDemo (String id, BoundaryCommand update);
}

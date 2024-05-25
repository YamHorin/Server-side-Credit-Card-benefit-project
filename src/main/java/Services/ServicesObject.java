package Services;


import java.util.List;
import java.util.Optional;

import Boundary.BoundaryObject;

public interface ServicesObject {
	public Optional<BoundaryObject> getSpecificDemo(String id);
	public List<BoundaryObject> getAllDemoes();
	
	public BoundaryObject createDemo (BoundaryObject boundaryObject);
	
	public void deleteAllDemoes ();
	
	public void updateDemo (String id, BoundaryObject update);
}

package Services;


import java.util.List;
import java.util.Optional;

import Boundary.BoundaryUser;

public interface ServicesUser {
	public Optional<BoundaryUser> getSpecificDemo(String id);
	public List<BoundaryUser> getAllDemoes();
	
	public BoundaryUser createDemo (BoundaryUser demoBoundary);
	
	public void deleteAllDemoes ();
	
	public void updateDemo (String id, BoundaryUser update);
}

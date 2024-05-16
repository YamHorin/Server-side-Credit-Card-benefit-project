package demo;

import java.util.List;
import java.util.Optional;

public interface DemoService {
	public Optional<DemoBoundary> getSpecificDemo(String id);
	public List<DemoBoundary> getAllDemoes();
	
	public DemoBoundary createDemo (DemoBoundary demoBoundary);
	
	public void deleteAllDemoes ();
	
	public void updateDemo (String id, DemoBoundary update);
}

package Application.business_logic;


import java.util.List;
import java.util.Optional;



public interface ServicesObject {
	public Optional<BoundaryObject> getSpecificObj(String id , String superApp, String userSuperapp, String email);
	public List<BoundaryObject> getAllObjects();
	
	public	BoundaryObject createObject (BoundaryObject StoreBoundary);
	
	public List<BoundaryObject> searchByType(String type, int size, int page);	
	public List<BoundaryObject> searchByAlias(String alias, int size, int page);
	public List<BoundaryObject> searchByPattern(String pattern, int size, int page);
	public List<BoundaryObject> searchByLat(String lat, int size, int page);
	public void deleteAllObjs (String id);
	
	public void updateObj (String id,String superApp, BoundaryObject update);
}

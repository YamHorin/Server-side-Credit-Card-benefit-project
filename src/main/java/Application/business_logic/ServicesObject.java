package Application.business_logic;


import java.util.List;
import java.util.Optional;



public interface ServicesObject {
	public Optional<BoundaryObject> getSpecificObj(String id , String superApp, String userSuperapp, String email);
	public List<BoundaryObject> getAllObjects(String id_user, int size, int page);
	
	public	BoundaryObject createObject (BoundaryObject StoreBoundary);
	
	public List<BoundaryObject> searchByType(String type, int size, int page);	
	public List<BoundaryObject> searchByAlias(String alias, int size, int page);
	public List<BoundaryObject> searchByPattern(String pattern, int size, int page, String email, String superapp, String superAppUser);
	public void deleteAllObjs (String id);
	
	public void updateObj (String id,String superApp, BoundaryObject update, String email, String userSuperapp);
	public List<BoundaryObject> searchByLat(double lat, double lng, double distance, int size, int page);
}

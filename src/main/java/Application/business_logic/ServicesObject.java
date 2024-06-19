package Application.business_logic;


import java.util.List;
import java.util.Optional;



public interface ServicesObject {
	public Optional<BoundaryObject> getSpecificObj(String id , String superApp, String userSuperapp, String email);
	public List<BoundaryObject> getAllObjects(String id_user, int size, int page);
	
	public	BoundaryObject createObject (BoundaryObject StoreBoundary);
	
	public List<BoundaryObject> searchByType(String type, int size, int page);	
	public List<BoundaryObject> searchObjectsByExactAlias(String alias, int size, int page);
	public List<BoundaryObject> searchObjectsByAliasPattern(String pattern, int size, int page);
	public List<BoundaryObject> searchByLocation(double lat, double lng, double distance,int size, int page);
	public void deleteAllObjs (String id);
	
	public void updateObj (String id,String superApp, BoundaryObject update, String email, String userSuperapp);
}

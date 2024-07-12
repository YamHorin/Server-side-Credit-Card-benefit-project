package Application.business_logic.DataService;


import java.util.List;
import java.util.Optional;

import Application.business_logic.Boundaies.ObjectBoundary;



public interface ServicesObject {
	public Optional<ObjectBoundary> getSpecificObj(String id , String superApp, String userSuperapp, String email);
	public List<ObjectBoundary> getAllObjects(String id_user, int size, int page);
	
	public	ObjectBoundary createObject (ObjectBoundary StoreBoundary);
	
	public List<ObjectBoundary> searchByType(String id,String type, int size, int page);	
	public List<ObjectBoundary> searchObjectsByExactAlias(String id,String alias, int size, int page);
	public List<ObjectBoundary> searchObjectsByAliasPattern(String id,String pattern, int size, int page);
	public List<ObjectBoundary> searchByLocation(String id,double lat, double lng, double distance,String distanceUnits,int size, int page);
	public void deleteAllObjs (String id);
	
	public void updateObj (String id,String superApp, ObjectBoundary update, String id_user );
	
	
}

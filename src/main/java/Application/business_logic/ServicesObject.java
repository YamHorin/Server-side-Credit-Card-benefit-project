package Application.business_logic;


import java.util.List;
import java.util.Optional;



public interface ServicesObject {
	public Optional<BoundaryObject> getSpecificObj(String id , String superApp, String userSuperapp, String email);
	public List<BoundaryObject> getAllObjects();
	
	public	BoundaryObject createObject (BoundaryObject StoreBoundary);
	
	public void deleteAllObjs (String id);
	
	public void updateObj (String id,String superApp, BoundaryObject update, String email, String userSuperapp);
}

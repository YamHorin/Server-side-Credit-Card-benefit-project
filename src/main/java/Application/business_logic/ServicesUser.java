package Application.business_logic;


import java.util.List;
import java.util.Optional;



public interface ServicesUser {
	public Optional<BoundaryUser> getSpecificUser(String id);
	public List<BoundaryUser> getAllUsers(String id,int page,int size);
	
	public BoundaryUser createUser (BoundaryUser UserBoundary);
	
	public void deleteAllUsers (String id);
	
	public void updateUser (String id, BoundaryUser update);
	
}

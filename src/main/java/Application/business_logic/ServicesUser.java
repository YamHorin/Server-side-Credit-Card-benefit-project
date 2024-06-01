package Application.business_logic;


import java.util.List;
import java.util.Optional;



public interface ServicesUser {
	public Optional<BoundaryUser> getSpecificUser(String id);
	public List<BoundaryUser> getAllUsers();
	
	public BoundaryUser createUser (BoundaryUser UserBoundary);
	
	public void deleteAllUsers ();
	
	public void updateUser (String id, BoundaryUser update);
	
}

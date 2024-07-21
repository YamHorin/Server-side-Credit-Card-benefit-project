package Application.business_logic.DataService;


import java.util.List;
import java.util.Optional;

import Application.business_logic.Boundaies.UserBoundary;



public interface ServicesUser {
	public Optional<UserBoundary> getSpecificUser(String id);
	public List<UserBoundary> getAllUsers(String id,int page,int size);
	
	public UserBoundary createUser (UserBoundary UserBoundary);
	
	public void deleteAllUsers (String id);
	
	public void updateUser (String id, UserBoundary update);
	
}

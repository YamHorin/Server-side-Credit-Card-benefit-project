package Application.business_logic.DataManagers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import Application.DataConvertor;
import Application.DataAccess.Dao.UserDao;
import Application.DataAccess.Entities.EntityUser;
import Application.DataAccess.Entities.RoleEnumEntity;
import Application._a_Presentation.Exceptions.BoundaryIsNotFilledCorrectException;
import Application._a_Presentation.Exceptions.BoundaryIsNotFoundException;
import Application._a_Presentation.Exceptions.UnauthorizedException;
import Application.business_logic.Boundaies.UserBoundary;
import Application.business_logic.DataService.ServicesUser;
import Application.business_logic.javaObjects.UserId;
@Service
public class DataManagerUser implements ServicesUser{
	
	private UserDao UserDao;
	private String name_super_app;
	private DataConvertor DataConvertor;
	public DataManagerUser(UserDao userDao , DataConvertor DataConvertor) {
		this.UserDao = userDao;
		this.DataConvertor = DataConvertor;
	}

	@Value("${spring.application.name:SuperApppp}")
	public void setname_super_app(String name_super_app) {
		System.err.println("**** reading from configuration default super app name: " + name_super_app);
		this.name_super_app = name_super_app;
	}

	@Override
	public Optional<UserBoundary> getSpecificUser(String id) {
		Optional <EntityUser> entityUser = this.UserDao.findById(id);
		Optional<UserBoundary> boundaryUser = entityUser.map(this.DataConvertor::EntityUserToBoundaryUser);
		if (boundaryUser.isEmpty())
			System.err.println("* no user to return");
		else
			System.out.println(boundaryUser.toString());
		return boundaryUser;	
	}



	public List<UserBoundary> getAllUsers(String id, int page, int size) {
		EntityUser userEntity = this.UserDao.findById(id).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id));
		RoleEnumEntity role = userEntity.getRole();
		if (role != RoleEnumEntity.adm_user)
			throw new UnauthorizedException("only admin users can get all users..");
		else {
			return this.UserDao
					.findAll(PageRequest.of(page, size, Direction.DESC, "id"))
					.stream()
					.map(this.DataConvertor::EntityUserToBoundaryUser)
					.peek(System.err::println)
					.toList();			
		}
	}
	
	@Override
	public UserBoundary createUser(UserBoundary UserBoundary) {
		System.err.println("\n\n* client requested to store: " + UserBoundary.toString());
		//check email
		isValidEmail(UserBoundary.getUserId().getEmail());
		
		//check for null \empty Strings
		checkStringIsNullOrEmpty(UserBoundary.getUserName(), "userName");
		checkStringIsNullOrEmpty(UserBoundary.getAvatar(), "avatar");
		
		UserId userId = UserBoundary.getUserId();
		userId.setSuperAPP(name_super_app);
		UserBoundary.setUserId(userId);
		EntityUser entity = this.DataConvertor.BoundaryUserTOEntityUser(UserBoundary);
		entity = this.UserDao.save(entity);
		UserBoundary rv  = this.DataConvertor.EntityUserToBoundaryUser(entity);
		System.err.println("* server stored: " + rv);
		return rv;
	}

	@Override
	public void deleteAllUsers(String id) {
		EntityUser userEntity = this.UserDao.findById(id).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id));
		RoleEnumEntity role = userEntity.getRole();
		if (role != RoleEnumEntity.adm_user)
			throw new UnauthorizedException("only admin users can delet all users..");
		else {
			System.err.println("* deleting table for users");
			this.UserDao.deleteAll();			
		}
	}

	@Override
	public void updateUser(String id, UserBoundary update) {

		EntityUser userEntity = this.UserDao.findById(id).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id));

		//check for null \empty Strings
		checkStringIsNullOrEmpty(update.getUserName(), "userName");
		checkStringIsNullOrEmpty(update.getAvatar(), "avatar");
		
		if (update.getUserId()!=null)
			if (!update.getUserId().getEmail().equalsIgnoreCase(userEntity.getId().split(" ")[0]))
				throw new UnauthorizedException("can't update email for users :( ");
		
		if (update.getRole()!=null)
			userEntity.setRole(RoleEnumEntity.valueOf(update.getRole().name().toLowerCase()));
		if (update.getUserName()!=null)
			userEntity.setUserName(update.getUserName());
		if (update.getAvatar()!=null)
			userEntity.setAvatar(update.getAvatar());

		
		userEntity = this.UserDao.save(userEntity);
		System.err.println("user has been updated: * " + userEntity);
		
	}


	
	public void isValidEmail(String email) {
		String EMAIL_PATTERN =
				"^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		if (email == null || email == "") 
			throw new BoundaryIsNotFilledCorrectException("email is empty..");
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()==false)
			throw new BoundaryIsNotFilledCorrectException("email is not filled correctly");
	}
	
	public void checkStringIsNullOrEmpty(String str , String value)
	{
		if (str ==null || str  =="")
			throw new BoundaryIsNotFilledCorrectException("\nvalue String in the boundary is empty or null "+value);
	}
	
}

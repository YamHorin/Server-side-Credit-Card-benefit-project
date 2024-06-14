package Application.business_logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import Application.DataConvertor;
import Application.DataAccess.EntityUser;
import Application.DataAccess.RoleEnumEntity;
import Application.DataAccess.UserDao;
import Application._a_Presentation.BoundaryIsNotFoundException;
import Application._a_Presentation.UnauthorizedException;
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
	public Optional<BoundaryUser> getSpecificUser(String id) {
		Optional <EntityUser> entityUser = this.UserDao.findById(id);
		Optional<BoundaryUser> boundaryUser = entityUser.map(this.DataConvertor::EntityUserToBoundaryUser);
		if (boundaryUser.isEmpty())
			System.err.println("* no user to return");
		else
			System.out.println(boundaryUser.toString());
		return boundaryUser;	
	}

	@Override
	public List<BoundaryUser> getAllUsers() {
		List<EntityUser> entities = this.UserDao.findAll();
		List<BoundaryUser> boundaries = new ArrayList<>();
		for (EntityUser entity : entities) {
			boundaries.add(this.DataConvertor.EntityUserToBoundaryUser(entity));
		}
		
		
		System.err.println("* data from database: " + boundaries);
		return boundaries;
	}

	@Override
	public BoundaryUser createUser(BoundaryUser UserBoundary) {
		System.err.println("* client requested to store: " + UserBoundary);
		UserId userId = UserBoundary.getUserId();
		userId.setSuperAPP(name_super_app);
		UserBoundary.setUserId(userId);
		if (UserBoundary.getRole()==null)
			UserBoundary.setRole(RoleEnumBoundary.UNDETERMINED);
		EntityUser entity = this.DataConvertor.BoundaryUserTOEntityUser(UserBoundary);
		entity = this.UserDao.save(entity);
		BoundaryUser rv  = this.DataConvertor.EntityUserToBoundaryUser(entity);
		System.err.println("* server stored: " + rv);
		return rv;
	}

	@Override
	public void deleteAllUsers(String id) {
		EntityUser userEntity = this.UserDao.findById(id).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id));
		RoleEnumEntity role = userEntity.getRole();
		if (role != RoleEnumEntity.adm_user)
			throw new UnauthorizedException();
		System.err.println("* deleting table for users");
		this.UserDao.deleteAll();
		
	}

	@Override
	public void updateUser(String id, BoundaryUser update) {
		System.out.println("* updating user with id: " + id + ", with the following details: " + update);
		EntityUser userEntity = this.UserDao.findById(id).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id));
		if (update.getUserId().getEmail()!=null)
			userEntity.setId(update.getUserId().getEmail() + "_" + update.getUserId().getSuperAPP());
		if (update.getRole()!=null)
			userEntity.setRole(RoleEnumEntity.valueOf(update.getRole().name().toLowerCase()));
		if (update.getUserName()!=null)
			userEntity.setUserName(update.getUserName());
		if (update.getAvatar()!=null)
			userEntity.setAvatar(update.getAvatar());

		
		userEntity = this.UserDao.save(userEntity);
		System.err.println("user has been updated: * " + userEntity);
		
	}
}

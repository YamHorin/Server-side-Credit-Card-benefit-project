package Application.business_logic.DataManagers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.lang.String;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Application.DataConvertor;
import Application.DataAccess.Dao.ObjDao;
import Application.DataAccess.Dao.UserDao;
import Application.DataAccess.Entities.EntityObject;
import Application.DataAccess.Entities.EntityUser;
import Application.DataAccess.Entities.RoleEnumEntity;
import Application._a_Presentation.Exceptions.BoundaryIsNotFilledCorrectException;
import Application._a_Presentation.Exceptions.BoundaryIsNotFoundException;
import Application._a_Presentation.Exceptions.DeprecationException;
import Application._a_Presentation.Exceptions.UnauthorizedException;
import Application.business_logic.Boundaies.BoundaryObject;
import Application.business_logic.DataService.ServicesObject;
import Application.business_logic.javaObjects.CreatedBy;
import Application.business_logic.javaObjects.Location;
import Application.business_logic.javaObjects.ObjectId;

@Service
public class DataManagerObject implements ServicesObject {
	private ObjDao objectDao;
	private UserDao userDao;
	private String superAppName;
	private DataConvertor DataConvertor;

	// a value for max location value , could be change in the future
	private final double limit_location = 1000;
	private final double KmToMile = 0.621371192;
	
	public DataManagerObject(UserDao userDao, ObjDao objectDao, DataConvertor DataConvertor) {
		this.objectDao = objectDao;
		this.userDao = userDao;
		this.DataConvertor = DataConvertor;

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<BoundaryObject> getSpecificObj(String id, String superApp, String userSuperapp, String email) {
		if (this.superAppName.compareTo(superApp) != 0)
			throw new BoundaryIsNotFoundException("super app is not found...");
		// check what the role of the user
		String id_user = email + " " + userSuperapp;
		EntityUser userEntity = this.userDao.findById(id_user).orElseThrow(() -> new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id_user));
		String id_object = id + "__" + superApp;
		Optional<EntityObject> entityObject;
		Optional<BoundaryObject> boundaryObject = java.util.Optional.empty();
		switch (userEntity.getRole()) {
			case adm_user:
				throw new UnauthorizedException("admin can't get a Specific object ");
			case miniapp_user:
				entityObject = this.objectDao.findByobjectIDAndActiveIsTrue(id_object);
				boundaryObject = entityObject.map(this.DataConvertor::EntityObjectTOBoundaryObject);
				if (boundaryObject.isEmpty())
					System.err.println("* no user to return");
				else
					System.out.println(boundaryObject.toString());
				return boundaryObject;
			case superapp_user:
				entityObject = this.objectDao.findById(id_object);
				boundaryObject = entityObject.map(this.DataConvertor::EntityObjectTOBoundaryObject);
				if (boundaryObject.isEmpty())
					System.err.println("* no user to return");
				else
					System.out.println(boundaryObject.toString());
				return boundaryObject;
			case undetermined:
				throw new UnauthorizedException("undetermined can't get a Specific object ");
			default:
				break;

		}
		return boundaryObject;

	}

	@Override
	@Transactional(readOnly = true)
	public List<BoundaryObject> getAllObjects(String id_user, int size, int page) {
		// check role
		EntityUser EntityUser = this.userDao.findById(id_user).orElseThrow(() -> new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id_user));
		List<BoundaryObject> boundaries = new ArrayList<>();
		List<EntityObject> entities;
		switch (EntityUser.getRole()) {
			case adm_user:
				throw new UnauthorizedException("adm_user can't get objects....");
			case miniapp_user:
				boolean active = true;
				entities = this.objectDao.findAllByActive(active,
						PageRequest.of(page, size, Direction.ASC, "objectID"));
				for (EntityObject entity : entities) {
					boundaries.add(this.DataConvertor.EntityObjectTOBoundaryObject(entity));
				}
				System.err.println("all objects data from database: " + boundaries);
				return boundaries;
			case superapp_user:
				entities = this.objectDao.findAll(PageRequest.of(page, size, Direction.ASC, "objectID"))
						.stream().collect(Collectors.toList());
				for (EntityObject entity : entities) {
					boundaries.add(this.DataConvertor.EntityObjectTOBoundaryObject(entity));
				}
				System.err.println("all objects data from database: " + boundaries);
				return boundaries;
			case undetermined:
				throw new UnauthorizedException("undetermined can't get objects....");

			default:
				break;

		}
		return boundaries;
	}

	@Override
	@Transactional(readOnly = false)
	public BoundaryObject createObject(BoundaryObject ObjectBoundary) {
		// check for null \empty Strings
		checkStringIsNullOrEmpty(ObjectBoundary.getType(), "type object");
		checkStringIsNullOrEmpty(ObjectBoundary.getAlias(), "Alias object");
		checkStringIsNullOrEmpty(ObjectBoundary.getCreatedBy().getUserId().getEmail(),
				"user email who created the object");
		// check for normal location
		
		checkLocationOfObject(ObjectBoundary.getLocation());

		
		
		// check Role
		String id = ObjectBoundary.getCreatedBy().getUserId().getEmail() + " " + this.superAppName;
		EntityUser userEntity = this.userDao.findById(id).orElseThrow(() -> new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id));
		if (userEntity.getRole() != RoleEnumEntity.superapp_user)
			throw new UnauthorizedException("only super app users can creat objects");

		ObjectBoundary.setCreationTimeStamp(new Date());
		ObjectId objId = new ObjectId();
		objId.setSuperApp(this.superAppName);
		objId.setId(makeObjectId(ObjectBoundary));
		ObjectBoundary.setObjectID(objId);
		ObjectBoundary.setCreatedBy(new CreatedBy(ObjectBoundary.getCreatedBy().getUserId().getEmail(), this.superAppName));
		EntityObject entity = this.DataConvertor.BoundaryObjectTOEntityObject(ObjectBoundary);
		entity = this.objectDao.save(entity);
		BoundaryObject rv = this.DataConvertor.EntityObjectTOBoundaryObject(entity);
		System.err.println("all the data objects server stored: " + rv);
		return rv;
	}

	private String makeObjectId(BoundaryObject objectBoundary) {
		//also update the id to the last count
		int counterObjectType = (int) this.objectDao.countByType(objectBoundary.getType());
		Map<String, Object> objectDetails = new HashMap<>();
		objectDetails.putAll(objectBoundary.getObjectDetails());
		switch (objectBoundary.getType())
		{
		case "store":
			objectDetails.put("idStore",counterObjectType);
			objectBoundary.setObjectDetails(objectDetails);
			return "S"+objectBoundary.getObjectDetails().get("idStore");
		case "benefit":
			objectDetails.put("idBenefit",counterObjectType);
			objectBoundary.setObjectDetails(objectDetails);
			return "B"+objectBoundary.getObjectDetails().get("idBenefit");
		case "club":
			objectDetails.put("idClub",counterObjectType);
			objectBoundary.setObjectDetails(objectDetails);
			return "C"+objectBoundary.getObjectDetails().get("idClub");
		default:
			return (UUID.randomUUID().toString());
			
		}
			
		
	}

	private void checkLocationOfObject(Location location) {
		double lat = location.getLat();
		double lng = location.getLng();
		if (lat <= 0 || lng <= 0 || lat > limit_location || lng > limit_location)
			throw new BoundaryIsNotFilledCorrectException("location of boundary is not filled correctly");
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAllObjs(String id) {
		EntityUser userEntity = this.userDao.findById(id).orElseThrow(() -> new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id));
		RoleEnumEntity role = userEntity.getRole();
		if (role != RoleEnumEntity.adm_user)
			throw new UnauthorizedException("only admin users can delet all objects..");
		else {
			System.err.println("* deleting table for objects");
			this.objectDao.deleteAll();
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void updateObj(String id, String superApp, BoundaryObject update, String email, String userSuperapp) {
		if (this.superAppName.compareTo(superApp) != 0) {
			System.err.println("\n" + this.superAppName.compareTo(superApp) + "\n");

			throw new BoundaryIsNotFoundException("super app is not found...");
		}
		//System.err.println("* updating obj with id: " + id + ", with the following details: " + update);
		id = id + "__" + superApp;
		String id2 = id;

		EntityObject objectEntity = this.objectDao.findById(id).orElseThrow(() -> new BoundaryIsNotFoundException(
				"Could not find object for update by id: " + id2));
		// check role
		String id_user = email + " " + userSuperapp;
		EntityUser EntityUser = this.userDao.findById(id_user).orElseThrow(() -> new BoundaryIsNotFoundException(
				"Could not find user for update by id: " + id_user));
		switch (EntityUser.getRole()) {
			case adm_user:
				throw new UnauthorizedException("admin can't update object");
			case miniapp_user:
				throw new UnauthorizedException("mini app user can't update object");
			case superapp_user:
				updateObjectInAction(objectEntity, update);
				break;
			default:
				break;

		}

		objectEntity = this.objectDao.save(objectEntity);
		System.err.println("\n\n********object has been updated: * \n" + objectEntity);
	}

	@Value("${spring.application.name:Jill}")
	public void setsuperAppName(String superApp) {
		System.err.println("**** reading from configuration default superAppName: " + superApp);
		this.superAppName = superApp;
	}

	public void checkStringIsNullOrEmpty(String str, String value) {
		if (str == null || str == "")
			throw new BoundaryIsNotFilledCorrectException("\nvalue String in the boundary is empty or null  :" + value);
	}

	public void isValidEmail(String email) {
		String EMAIL_PATTERN = "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		if (email == null || email == "") {
			throw new BoundaryIsNotFilledCorrectException("email is empty..");
		}
		Matcher matcher = pattern.matcher(email);
		if (matcher.matches() == false) {
			System.err.println("enter check\n");
			throw new BoundaryIsNotFilledCorrectException("email is not filled correctly");

		}

	}

	// copy to an own function to avoid Repeated code
	public void updateObjectInAction(EntityObject objectEntity, BoundaryObject update) {
		if (update.getType() != null)
			objectEntity.setType(update.getType());
		if (update.getCreationTimeStamp() != null)
			objectEntity.setCreationTimeStamp(update.getCreationTimeStamp());

		if (update.getCreatedBy() != null) {
			String email1 = update.getCreatedBy().getUserId().getEmail();
			isValidEmail(email1);
			objectEntity.setCreatedBy(email1 + " " + this.superAppName);
		}

		
		if (update.getActive() != null)
			objectEntity.setActive(update.getActive());

		System.err.println("\n\n\n\n\n*****\n\n\n"+objectEntity.getActive());
		
		
		if (update.getAlias() != null)
			objectEntity.setAlias(update.getAlias());

		if (update.getObjectDetails() != null)
			objectEntity.setObjectDetails(update.getObjectDetails());
		if (update.getLocation() != null) {
			checkLocationOfObject(update.getLocation());
			objectEntity.setLocation_lat(update.getLocation().getLat());
			objectEntity.setLocation_lng(update.getLocation().getLng());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<BoundaryObject> searchByType(String id,String type, int size, int page) {
		EntityUser userEntity = this.userDao.findById(id).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id));
		RoleEnumEntity role = userEntity.getRole();
		switch (role) {
			case adm_user:
				throw new UnauthorizedException("admin can't update object");

			case miniapp_user:
				return this.objectDao.findAllBytypeAndActiveIsTrue(type, PageRequest.of(page, size, Direction.ASC, "objectID"))
						.stream()
						.map(entity -> this.DataConvertor.EntityObjectTOBoundaryObject(entity))
						.toList();
			case superapp_user:
				return this.objectDao
						.findAllBytype(type,
								PageRequest.of(page, size, Direction.ASC, "objectID"))
						.stream()
						.map(entity -> this.DataConvertor.EntityObjectTOBoundaryObject(entity))
						.toList();	
			case undetermined:
				throw new UnauthorizedException("admin can't update object");
			default:
				break;
		}
		return null;
		
	}

	@Override
	@Transactional(readOnly = true)

	public List<BoundaryObject> searchObjectsByExactAlias(String id,String alias, int size, int page) {
		EntityUser userEntity = this.userDao.findById(id).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id));
		RoleEnumEntity role = userEntity.getRole();
		switch (role) {
			case adm_user:
				throw new UnauthorizedException("admin can't update object");

			case miniapp_user:
				return this.objectDao.findAllByaliasAndActiveIsTrue(alias, PageRequest.of(page, size, Direction.ASC, "objectID"))
						.stream()
						.map(entity -> this.DataConvertor.EntityObjectTOBoundaryObject(entity))
						.toList();
			case superapp_user:
				return this.objectDao.findAllByalias(alias, PageRequest.of(page, size, Direction.ASC, "objectID"))
						.stream()
						.map(entity -> this.DataConvertor.EntityObjectTOBoundaryObject(entity))
						.toList();		
			case undetermined:
				throw new UnauthorizedException("admin can't update object");
			default:
				break;
		}
		return null;
	}

	
	@Override
	@Transactional(readOnly = true)

	public List<BoundaryObject> searchObjectsByAliasPattern(String id,String pattern, int size, int page) {
		
		EntityUser userEntity = this.userDao.findById(id).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id));
		RoleEnumEntity role = userEntity.getRole();
		switch (role) {
			case adm_user:
				throw new UnauthorizedException("admin can't update object");

			case miniapp_user:
				return this.objectDao.findAllByaliasLikeAndActiveIsTrue(pattern, PageRequest.of(page, size, Direction.ASC, "objectID"))
						.stream()
						.map(entity -> this.DataConvertor.EntityObjectTOBoundaryObject(entity))
						.toList();
			case superapp_user:
				return this.objectDao.findAllByaliasLike(pattern, PageRequest.of(page, size, Direction.ASC, "objectID"))
						.stream()
						.map(entity -> this.DataConvertor.EntityObjectTOBoundaryObject(entity))
						.toList();				
			default:
				break;
		}
		return null;
				
		
	}

	@Override

	@Transactional(readOnly = true)
	public List<BoundaryObject> searchByLocation(String id,double lat, double lng, double distance,String distanceUnits,int size, int page) {
		EntityUser userEntity = this.userDao.findById(id).orElseThrow(()->new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id));
		RoleEnumEntity role = userEntity.getRole();
		switch (role) {
			case adm_user:
				throw new UnauthorizedException("admin can't update object");

			case miniapp_user:
				return this.objectDao.findAllWithinRadiusAndActiveIsTrue(lat,lng, distance, PageRequest.of(page, size, Direction.ASC, "objectID"))
						.stream()
						.map(entity -> this.DataConvertor.EntityObjectTOBoundaryObject(entity))
						.toList();
			case superapp_user:
				if (distanceUnits.equalsIgnoreCase("neutral") )
				{
					System.err.println("this is the natural ..\n\n");
					return this.objectDao.findAllWithinRadiusN(lat,lng, distance, PageRequest.of(page, size, Direction.ASC, "objectID"))
							.stream()
							.map(entity -> this.DataConvertor.EntityObjectTOBoundaryObject(entity))
							.toList();	
				}
				else
				{
					System.err.println("this is the KM ..\n\n");
					if (distanceUnits.equalsIgnoreCase("MILES") )
						distance = distance *KmToMile;

					return this.objectDao.findAllByLocationWithinRadiusKM(lat,lng, distance, PageRequest.of(page, size, Direction.ASC, "objectID"))
							.stream()
							.map(entity -> this.DataConvertor.EntityObjectTOBoundaryObject(entity))
							.toList();	
				}
					
			default:
				break;
		}
		return null;
		
	
		
		
		
	}

	




}
package Application.business_logic.DataManagers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
import Application.business_logic.Boundaies.ObjectBoundary;
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
	public Optional<ObjectBoundary> getSpecificObj(String id, String superApp, String userSuperapp, String email) {
		if (this.superAppName.compareTo(superApp) != 0)
			throw new BoundaryIsNotFoundException("super app is not our...");
		// check what the role of the user
		String id_user = email + " " + userSuperapp;
		EntityUser userEntity = this.userDao.findById(id_user).orElseThrow(() -> new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id_user));
		String id_object = id + "__" + superApp;
		Optional<EntityObject> entityObject;
		Optional<ObjectBoundary> boundaryObject = java.util.Optional.empty();
		switch (userEntity.getRole()) {
			case adm_user:
				throw new UnauthorizedException("admin can't get a Specific object ");
			case miniapp_user:
				entityObject = this.objectDao.findByobjectIDAndActiveIsTrue(id_object);
				boundaryObject = entityObject.map(this.DataConvertor::EntityObjectTOBoundaryObject);
				if (boundaryObject.isEmpty())
					System.err.println("*\n*\n* no super app object to return *\n*\n*");
				else
					System.out.println(boundaryObject.toString());
				return boundaryObject;
			case superapp_user:
				entityObject = this.objectDao.findById(id_object);
				boundaryObject = entityObject.map(this.DataConvertor::EntityObjectTOBoundaryObject);
				if (boundaryObject.isEmpty())
					System.err.println("*\n*\n* no super app object to return *\n*\n*");
				else
					System.out.println(boundaryObject.toString());
				return boundaryObject;

			default:
				break;

		}
		return boundaryObject;

	}

	@Override
	@Transactional(readOnly = true)
	public List<ObjectBoundary> getAllObjects(String id_user, int size, int page) {
		// check role
		EntityUser EntityUser = this.userDao.findById(id_user).orElseThrow(() -> new BoundaryIsNotFoundException(
				"Could not find User for update by id: " + id_user));
		List<ObjectBoundary> boundaries = new ArrayList<>();
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
			default:
				break;

		}
		return boundaries;
	}

	@Override
	@Transactional(readOnly = false)
	//
	public ObjectBoundary createObject(ObjectBoundary ObjectBoundary) {
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
		objId.setSuperapp(this.superAppName);
		objId.setId(makeObjectId(ObjectBoundary));
		ObjectBoundary.setObjectId(objId);
		ObjectBoundary.setCreatedBy(new CreatedBy(ObjectBoundary.getCreatedBy().getUserId().getEmail(), this.superAppName));
		
		checkSyncListsInMap(ObjectBoundary.getObjectDetails(), ObjectBoundary.getType(),
				this.superAppName, ObjectBoundary.getCreatedBy().getUserId().getEmail());
		
		EntityObject entity = this.DataConvertor.BoundaryObjectTOEntityObject(ObjectBoundary);
		entity = this.objectDao.save(entity);
		ObjectBoundary rv = this.DataConvertor.EntityObjectTOBoundaryObject(entity);
		System.err.println("all the data objects server stored: " + rv);
		return rv;
	}

	//chekcPoint
	private String makeObjectId(ObjectBoundary objectBoundary) {
		//also update the id to the last count
		int counterObjectType = (int) this.objectDao.countByType(objectBoundary.getType());
		Map<String, Object> objectDetails = new HashMap<>();
		objectDetails.putAll(objectBoundary.getObjectDetails());
		switch (objectBoundary.getType())
		{
		case "store":
			objectDetails.put("idStore",counterObjectType);
			System.err.println(objectDetails.toString());
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
	public void updateObj(String id, String superApp, ObjectBoundary update,String id_user) {
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
		
		EntityUser EntityUser = this.userDao.findById(id_user).orElseThrow(() -> new BoundaryIsNotFoundException(
				"Could not find user for update by id: " + id_user));
		switch (EntityUser.getRole()) {
			case adm_user:
				throw new UnauthorizedException("admin can't update object");
			case miniapp_user:
				throw new UnauthorizedException("mini app user can't update object");
			case superapp_user:
				updateObjectInAction(objectEntity, update);
//				checkSyncListsInMap(objectEntity.getObjectDetails(), 
//						objectEntity.getType(), userSuperapp, email);
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
	public void updateObjectInAction(EntityObject objectEntity, ObjectBoundary update) {
		if (update.getType() != null && update.getType() != "")
			objectEntity.setType(update.getType());


		
		if (update.getActive() != null)
			objectEntity.setActive(update.getActive());		
		
		if (update.getAlias() != null)
			objectEntity.setAlias(update.getAlias());

		if (update.getObjectDetails() != null)
		{
			objectEntity.setObjectDetails(updateMapOfObject(objectEntity, 
					update.getObjectDetails()));
		}
		if (update.getLocation() != null) {
			checkLocationOfObject(update.getLocation());
			objectEntity.setLocation_lat(update.getLocation().getLat());
			objectEntity.setLocation_lng(update.getLocation().getLng());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ObjectBoundary> searchByType(String id,String type, int size, int page) {
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

			default:
				break;
		}
		return null;
		
	}

	@Override
	@Transactional(readOnly = true)

	public List<ObjectBoundary> searchObjectsByExactAlias(String id,String alias, int size, int page) {
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
			default:
				break;
		}
		return null;
	}

	
	@Override
	@Transactional(readOnly = true)

	public List<ObjectBoundary> searchObjectsByAliasPattern(String id,String pattern, int size, int page) {
		
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
	public List<ObjectBoundary> searchByLocation(String id,double lat, double lng, double distance,String distanceUnits,int size, int page) {
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
	public void syncListsOfObject(Map<String, Object> ObjectDetails , 
			String typeInput,String typeObjectToSync1 ,  String typeObjectToSync2 
			, String userSuperapp, String email)
	{
		Map<String, Object> Details = new HashMap<>();
		Integer ObjectNumber = (Integer) ObjectDetails.get(String.format("id%s", typeInput));
		List<Integer> listOfobjectsType1InInputObject;
		List<Integer> listOfobjectsType2InInputObject;
		List<Integer> listOfInputTypeInType1;
		List<Integer> listOfInputTypeInType2;
		try {
			//check list of clubs and the list of store to see if they are exist 
			listOfobjectsType1InInputObject =  getAListFromMap(ObjectDetails, String.format("listOf%ssOf%s",typeObjectToSync1  ,typeInput));
			listOfobjectsType2InInputObject =  getAListFromMap(ObjectDetails, String.format("listOf%ssOf%s",typeObjectToSync2  ,typeInput));
		} catch (Exception e) {
			throw new BoundaryIsNotFilledCorrectException("map of new object is not good ,missing "+String.format("listOf%ssOf%s",typeObjectToSync1  ,typeInput)+" or "+
					String.format("listOf%ssOf%s",typeObjectToSync2  ,typeInput)+" keys.....");
		}
		//list Of objects Type1 In Input Object check
		for (Integer numberStore : listOfobjectsType1InInputObject) {
			String id_obj = typeObjectToSync1.charAt(0)+""+numberStore;
			Optional<ObjectBoundary> optional = getSpecificObj(id_obj, this.superAppName, userSuperapp, email);
			ObjectBoundary object;
			if (!optional.isPresent()|| !optional.get().getActive())
				throw new BoundaryIsNotFilledCorrectException(String.format("%s number "
						+ "%d in listOfStoresOfBenefit in ObjectDetails in the object Boundary "
						+ "is not present /active anymore in the"
						+ " databse send a new %s.... , ",typeObjectToSync1, numberStore ,typeInput));
			else
			{
				object = optional.get();
				
				Details.putAll(object.getObjectDetails());
				
				listOfInputTypeInType1 = getAListFromMap(Details, String.format("listOf%ssOf%s",typeInput,typeObjectToSync1));
				if (!listOfInputTypeInType1.contains(ObjectNumber))
					listOfInputTypeInType1.add(ObjectNumber);
				Details.put(String.format("listOf%ssOf%s ",typeInput,typeObjectToSync1), listOfInputTypeInType1);
				object.setObjectDetails(Details);
				String id_user = email+" "+ userSuperapp;
				updateObj(id_obj, this.superAppName, object, id_user );
				
			}
				//and also add the benefit number to the list of benefit in the store and in the club 
				//if it don't exist before...
		}
		Details.clear();

		//list Of objects Type2 In Input Object check
		for (Integer numberStore : listOfobjectsType2InInputObject) {
			String id_obj = Character.toUpperCase(typeObjectToSync2.charAt(0))+""+numberStore;
			Optional<ObjectBoundary> optional = getSpecificObj(id_obj, this.superAppName, userSuperapp, email);
			ObjectBoundary object;
			if (!optional.isPresent()|| !optional.get().getActive())
				throw new BoundaryIsNotFilledCorrectException(String.format("%s number "
						+ "%d in listOfStoresOfBenefit in ObjectDetails in the object Boundary "
						+ "is not present /active anymore in the"
						+ " databse send a new %s.... , ",typeObjectToSync2, numberStore ,typeInput));
			else
			{
				object = optional.get();
				
				listOfInputTypeInType2 = getAListFromMap(object.getObjectDetails(), String.format("listOf%ssOf%s",typeInput,typeObjectToSync2));
				if (!listOfInputTypeInType2.contains(ObjectNumber))
					listOfInputTypeInType2.add(ObjectNumber);
				Details.putAll(object.getObjectDetails());
				Details.put(String.format("listOf%ssOf%s ",typeInput,typeObjectToSync2), listOfInputTypeInType2);
				object.setObjectDetails(Details);
				String id_user = email+" "+ userSuperapp;
				updateObj(id_obj, this.superAppName, object, id_user );
				
			}
				
		}
	}
	
	//TODO function check for benefit if the id of store and club are in the system and sync between the two
	public void checkSyncListsInMap(Map<String, Object> ObjectDetails , String type , 
			String userSuperapp, String email)
	{

		int numberOfElementsStore = (int) this.objectDao.countByType("store");
		int numberOfElementsClub = (int) this.objectDao.countByType("club");
		int numberOfElementsBenefit = (int) this.objectDao.countByType("benefit");
		//Initializer is still working... 
		if (numberOfElementsStore <6 || numberOfElementsBenefit<10 || numberOfElementsClub<6)
			return;

		switch (type) {
		case "benefit": {
			syncListsOfObject(ObjectDetails, "Benefit", "Store","Club", userSuperapp, email);
			break;
		}
		case "store": {
			syncListsOfObject(ObjectDetails, "Store", "Benefit","Club", userSuperapp, email);
			break;
		}
		case "club": {
			syncListsOfObject(ObjectDetails, "Club", "Benefit","Store", userSuperapp, email);
			break;
		}
		default:
			break;
		}
	
	}
	
	//fuction helper to get list of the intgers from a map
	public List<Integer> getAListFromMap(Map<String, Object> objectDetails , String key)
	{		

			List<Object> objects = (List)(objectDetails.get(key));
			List <Integer> numbers = 	objects.stream().map(Object::toString)
					.map(str->Integer.parseInt(str))
					.toList();
			LinkedList<Integer> numbersLink = new LinkedList<>(numbers);
			return numbersLink;

	}
	public Map<String ,Object> updateMapOfObject(EntityObject objectEntity, Map<String ,Object> updateObjectMap)
	{
		Map<String ,Object> map = new HashMap<>(updateObjectMap);
		//for every key in the entity map if the update does not have it put it in the map 
		for (String key : objectEntity.getObjectDetails().keySet())
		{
			if (!map.containsKey(key))
				map.put(key, objectEntity.getObjectDetails().get(key));
		}
		return map;
	}


}
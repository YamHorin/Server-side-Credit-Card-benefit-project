package Application.logic.StoreInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;
import Application.business_logic.Boundaies.MiniAppCommandBoundary;
import Application.business_logic.Boundaies.ObjectBoundary;
import Application.business_logic.Boundaies.RoleEnumBoundary;
import Application.business_logic.Boundaies.UserBoundary;
import Application.business_logic.DataService.ServicesObject;
import Application.business_logic.DataService.ServicesUser;
import Application.logic.MiniappInterface;

@Component("Store Inerface_addBenefitToStore")
public class addBenefitToStore implements MiniappInterface {

private ServicesObject ServicesObject;
private ServicesUser ServicesUser;
	
	public addBenefitToStore(ServicesObject servicesObject , ServicesUser ServicesUser) {
		ServicesObject = servicesObject;
		this.ServicesUser  =ServicesUser;
	}
	
	
	
	//number of benefit will be in the detiles map of mini app boundary ....
	//TODO add jason of miniApp in the drive
	@Override
	public List<ObjectBoundary> activateCommand(MiniAppCommandBoundary miniappCommandBoundary) {
		//update user to super app user to update the object to update it back to mini app user 
		//this is really stupid....  i know
		String userSuperapp = miniappCommandBoundary.getInvokedBy().getUserId().getSuperAPP();
		String email = miniappCommandBoundary.getInvokedBy().getUserId().getEmail();
		
		String id_user = email+" "+userSuperapp;
		UserBoundary update = new UserBoundary();
		update.setRole(RoleEnumBoundary.SUPERAPP_USER);
		this.ServicesUser.updateUser(id_user, update);
		ObjectBoundary store = null;
		String storeId = miniappCommandBoundary.getTargetObject().getObjectId().getId();
		String superApp = miniappCommandBoundary.getTargetObject().getObjectId().getSuperApp();
		Optional<ObjectBoundary> club = this.ServicesObject
		.getSpecificObj(storeId ,superApp  , userSuperapp , email);
		
		if (club.isPresent()) {
			store = club.orElse(null);
		}
		
		//club to add is in the map....
		
		int benefitNumber = (int) miniappCommandBoundary.getCommandAttributes().get("benefit");
		Map<String, Object> objectDetails = store.getObjectDetails();
		List<Integer> benefits = getAListFromMap(objectDetails, "listOfBenefitOfStore");
		
		
		//TODO check if the benefit is already in the list
		//TODO check if the benefit is in the data base 
		
		//add new benefit
		benefits.add(benefitNumber);
		objectDetails.put("listOfBenefitOfStore", benefits);
		store.setObjectDetails(objectDetails);
		
		
		this.ServicesObject.updateObj(storeId, superApp, store, id_user);
		System.out.println("update is down add new Benefit To Store \n\nreturn update store..");

		update.setRole(RoleEnumBoundary.MINIAPP_USER);
		this.ServicesUser.updateUser(id_user, update);
		//need to return one object....
		return Collections.singletonList(store);
		
	}
	
	
	public List<Integer> getAListFromMap(Map<String, Object> objectDetails , String key)
	{
		List<Object> objects = (List)(objectDetails.get(key));
		List <Integer> numbers = 	objects.stream().map(Object::toString)
				.map(str->Integer.parseInt(str))
				.toList();
		return new ArrayList<>(numbers) ;
	}


}

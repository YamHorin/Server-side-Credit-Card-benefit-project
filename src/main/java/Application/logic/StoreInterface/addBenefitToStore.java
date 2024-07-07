package Application.logic.StoreInterface;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;
import Application.business_logic.Boundaies.MiniAppCommandBoundary;
import Application.business_logic.Boundaies.ObjectBoundary;
import Application.business_logic.DataService.ServicesObject;
import Application.logic.MiniappInterface;

@Component("addBenefitToStore")
public class addBenefitToStore implements MiniappInterface {

private ServicesObject ServicesObject;
	
	public addBenefitToStore(ServicesObject servicesObject) {
		ServicesObject = servicesObject;
	}
	
	
	
	//number of benefit will be in the detiles map of mini app boundary ....
	//TODO add jason of miniApp in the drive
	@Override
	public List<ObjectBoundary> activateCommand(MiniAppCommandBoundary miniappCommandBoundary) {
		ObjectBoundary store = null;
		String storeId = miniappCommandBoundary.getTargetObject().getObjectId().getId();
		String superApp = miniappCommandBoundary.getTargetObject().getObjectId().getSuperApp();
		String userSuperapp = miniappCommandBoundary.getInvokedBy().getUserId().getSuperAPP();
		String email = miniappCommandBoundary.getInvokedBy().getUserId().getEmail();
		Optional<ObjectBoundary> club = this.ServicesObject
		.getSpecificObj(storeId ,superApp  , userSuperapp , email);
		
		if (club.isPresent()) {
			store = club.orElse(null);
		}
		
		//club to add is in the map....
		
		int benefitNumber = (int) miniappCommandBoundary.getCommandAttributes().get("benefit");
		Map<String, Object> objectDetails = store.getObjectDetails();
		List<Integer> benefits = getAListFromMap(objectDetails, "listOfBenefitOfStore");
		//add new benefit
		benefits.add(benefitNumber);
		objectDetails.put("listOfBenefitOfStore", benefits);
		store.setObjectDetails(objectDetails);
		
		this.ServicesObject.updateObj(storeId, superApp, store, email, userSuperapp);
		System.out.println("update is down add new Benefit To Store \n\nreturn update store..");
        //need to return one object....
		return Collections.singletonList(store);
		
	}
	
	
	public List<Integer> getAListFromMap(Map<String, Object> objectDetails , String key)
	{
		List<Object> objects = (List)(objectDetails.get(key));
		List <Integer> numbers = 	objects.stream().map(Object::toString)
				.map(str->Integer.parseInt(str))
				.toList();
		return numbers;
	}


}

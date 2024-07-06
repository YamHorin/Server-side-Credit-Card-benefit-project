package Application.logic.FindAllBenefits;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import Application._a_Presentation.Exceptions.BoundaryIsNotFoundException;
import Application.business_logic.Boundaies.BoundaryCommand;
import Application.business_logic.Boundaies.BoundaryObject;
import Application.business_logic.DataService.ServicesObject;
import Application.logic.MiniappInterface;

public class findBenefitsByClub implements MiniappInterface {
	private ServicesObject ServicesObject;
	
	public findBenefitsByClub(ServicesObject servicesObject) {
		ServicesObject = servicesObject;
	}
	
	
	@Override
	public Object activateCommand(BoundaryCommand miniappCommandBoundary) {
		
		String clubId = miniappCommandBoundary.getTargetObject().getObjectId().getId();
		String superApp = miniappCommandBoundary.getTargetObject().getObjectId().getSuperApp();
		String userSuperapp = miniappCommandBoundary.getInvokedBy().getUserId().getSuperAPP();
		String email = miniappCommandBoundary.getInvokedBy().getUserId().getEmail();
		BoundaryObject boundaryObject = null;
		Optional<BoundaryObject> club = this.ServicesObject
		.getSpecificObj(clubId ,superApp  , userSuperapp , email);
		
		if (club.isPresent()) {
			boundaryObject = club.orElse(null);
		}
		List<Integer> benefits = getAListFromMap(boundaryObject.getObjectDetails(),"listOfBenefitOfClub");
		List<BoundaryObject> benefits_objects = new ArrayList<>();
		for (Integer benefit : benefits) {
			Optional<BoundaryObject> benefitObj =this.ServicesObject.getSpecificObj("B"+benefit, superApp, userSuperapp, email);
			if (benefitObj.isPresent()) {
				boundaryObject = benefitObj.orElse(null);
			}
			benefits_objects.add(boundaryObject);
		}
		return benefits_objects;
		
		
		
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

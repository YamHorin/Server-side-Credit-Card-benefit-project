package Application.logic.FindAllBenefits;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import Application._a_Presentation.Exceptions.BoundaryIsNotFoundException;
import Application.business_logic.Boundaies.MiniAppCommandBoundary;
import Application.business_logic.Boundaies.ObjectBoundary;
import Application.business_logic.DataService.ServicesObject;
import Application.logic.MiniappInterface;

@Component("findYourBenefit_findBenefitsByClub")
public class findBenefitsByClub implements MiniappInterface {
	private ServicesObject ServicesObject;
	
	public findBenefitsByClub(ServicesObject servicesObject) {
		ServicesObject = servicesObject;
	}
	
	
	@Override
	public List<ObjectBoundary> activateCommand(MiniAppCommandBoundary miniappCommandBoundary) {
		
		String clubId = miniappCommandBoundary.getTargetObject().getObjectId().getId();
		String superApp = miniappCommandBoundary.getTargetObject().getObjectId().getSuperapp();
		String userSuperapp = miniappCommandBoundary.getInvokedBy().getUserId().getSuperapp();
		String email = miniappCommandBoundary.getInvokedBy().getUserId().getEmail();
		ObjectBoundary boundaryObject = null;
		Optional<ObjectBoundary> club = this.ServicesObject
		.getSpecificObj(clubId ,superApp  , userSuperapp , email);
		
		if (club.isPresent()) {
			boundaryObject = club.orElse(null);
		}
		List<Integer> benefits = getAListFromMap(boundaryObject.getObjectDetails(),"listOfBenefitsOfClub");
		List<ObjectBoundary> benefits_objects = new ArrayList<>();
		for (Integer benefit : benefits) {
			Optional<ObjectBoundary> benefitObj =this.ServicesObject.getSpecificObj("B"+benefit, superApp, userSuperapp, email);
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

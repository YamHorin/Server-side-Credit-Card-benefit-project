package Application.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntFunction;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import Application.business_logic.Boundaies.MiniAppCommandBoundary;
import Application.business_logic.Boundaies.ObjectBoundary;
import Application.business_logic.DataService.ServicesObject;
import Application.business_logic.javaObjects.UserId;

@Component
public class ClubFunctions {

	public void printAllBenefitOfClub (RestClient restClient ,UserId userId , int clubNumber)
	{
		//restClient  = "http://localhost:" + port + "/superapp
		String club = "C"+clubNumber;
		String superApp = userId.getSuperapp();
		String email = userId.getEmail();
		//fix url
		ObjectBoundary clubObject =  restClient.get().uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}" ,
				superApp,
				club,
				superApp, 
				email ).
				retrieve().body(ObjectBoundary.class);
		System.out.println("link passed");

		List <Integer> benefits = getALiistFromMap(clubObject.getObjectDetails(), "listOfBenefitsOfClub");
		System.out.println("here are all the benefits in the club: \n\n");
		for (Integer benefitNumber : benefits) {
			ObjectBoundary benefit =  restClient.get().uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}" ,
					superApp,
					"B"+benefitNumber ,
					superApp , 
					email ).
					retrieve().body(ObjectBoundary.class);
			System.out.println("benefit:"+benefit.getAlias()+"\n"+"description: "
					+benefit.getObjectDetails().get("description")+"\n");
		}
		//maybe to return a list? 

	}
	public void addBenefitToClub(RestClient restClient ,UserId userId , int clubNumber , int benefitNumber)
	{
		//restClient  = "http://localhost:" + port + "/superapp
		//get club and benefit
		String club = "C"+clubNumber;
		String superApp = userId.getSuperapp();
		String email = userId.getEmail();
		//fix url
		ObjectBoundary clubObject =  restClient.get().uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}" ,
				superApp ,
				club ,
				superApp , 
				email ).
				retrieve().body(ObjectBoundary.class);
		System.out.println("link passed");

		String BenefitId = "B"+benefitNumber;
		//fix url
		ObjectBoundary benefitObject =  restClient.get().uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}" ,
				superApp, 
				BenefitId,
				superApp, 
				email ).
				retrieve().body(ObjectBoundary.class);
		System.out.println("link passed");

		List <Integer> benefits = getALiistFromMap(clubObject.getObjectDetails(),"listOfBenefitsOfClub");
		List <Integer> clubs = getALiistFromMap(benefitObject.getObjectDetails(),"listOfClubsOfBenefit");

		//update club
		Set<Integer> set = new HashSet<>(benefits);
        set.add(benefitNumber);
		HashMap<String, Object> objectDetails = (HashMap<String, Object>) clubObject.getObjectDetails();
		objectDetails.put("listOfBenefitsOfClub", new ArrayList<>(set));
		clubObject.setObjectDetails(objectDetails);
		System.out.println("benefit list of club has been updated :"+set.toString());
		
		set.clear();
		
		//update benefit 
		set.addAll(clubs);
		set.add(clubNumber);
		objectDetails = (HashMap<String, Object>) benefitObject.getObjectDetails();
		objectDetails.put("listOfClubsOfBenefit", new ArrayList<>(set));
		benefitObject.setObjectDetails(objectDetails);
		System.out.println("clubs list of benefit has been updated :"+set.toString());

		//can be a bug because put do not return nothing....
		//fix url
		restClient.put().uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}" ,
				superApp,
				club ,
				superApp , 
				email ).
				body(clubObject).
				retrieve();
		System.out.println("link passed");
		
		restClient.put().uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}" ,
				superApp,
				BenefitId ,
				superApp , 
				email ).
				body(benefitObject).
				retrieve();
		System.out.println("link passed");

		System.out.println("the updated club: \n"+clubObject.toString());

		System.out.println("the updated benefit: \n"+benefitObject.toString());
	}
	

	
	
	public List<Integer> getALiistFromMap(Map<String, Object> objectDetails , String key)
	{
		List<Object> objects = (List)(objectDetails.get(key));
		List <Integer> numbers = 	objects.stream().map(Object::toString)
				.map(str->Integer.parseInt(str))
				.toList();
		return new LinkedList<Integer>(numbers) ;
	}
	
	
	
	
}

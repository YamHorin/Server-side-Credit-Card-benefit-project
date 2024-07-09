package Application.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
		String superApp = userId.getSuperAPP();
		String email = userId.getEmail();
		//fix url
		ObjectBoundary clubObject =  restClient.get().uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}" ,
				superApp,
				club,
				superApp, 
				email ).
				retrieve().body(ObjectBoundary.class);
		System.out.println("link passed");

		List <Integer> benefits = getALiistFromMap(clubObject.getObjectDetails(), "listOfBenefitOfClub");
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
		String superApp = userId.getSuperAPP();
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

		List <Integer> benefits = getALiistFromMap(clubObject.getObjectDetails(),"listOfBenefitOfClub");
		benefits.add(benefitNumber);
		Map<String, Object> objectDetails = clubObject.getObjectDetails();
		objectDetails.put("listOfBenefitOfClub", objectDetails);
		clubObject.setObjectDetails(objectDetails);
		System.out.println("benefit list of club has been updates :"+benefits.toString());
		
		//can be a bug because put do not return nothing....
		//fix url
		restClient.put().uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}" ,
				club ,
				superApp , 
				email ).
				body(clubObject).
				retrieve();
		System.out.println("link passed");

		System.out.println("the updated club: \n"+clubObject.toString());
	}
	
	public void addBenefitToClubStringClubStringBenefit(RestClient restClient ,UserId userId , String ClubName , String benefitName)
	{
		//restClient  = "http://localhost:" + port + "/superapp
		//get club and benefit
		
		String superApp = userId.getSuperAPP();
		String email = userId.getEmail();
		//fix url
		ObjectBoundary clubObject =  restClient.get().uri("/objects/search"
				+ "/byAlias/{alias}"
				+ "{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}"
				+ "&size={size}&page={page}" ,
				ClubName ,
				superApp , 
				email,
				1,
				0).
				retrieve().body(ObjectBoundary.class);
		System.out.println("link passed");

		//fix url
		ObjectBoundary benefitObject = restClient.get().uri("/objects/search"
				+ "/byAlias/{alias}"
				+ "{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}"
				+ "&size={size}&page={page}" ,
				benefitName ,
				superApp , 
				email,
				1,
				0).
				retrieve().body(ObjectBoundary.class);
		System.out.println("link passed");

		List <Integer> benefits = getALiistFromMap(clubObject.getObjectDetails(),"listOfBenefitOfClub");
		benefits.add((Integer) benefitObject.getObjectDetails().get("idBenefit"));
		Map<String, Object> objectDetails = clubObject.getObjectDetails();
		objectDetails.put("listOfBenefitOfClub", objectDetails);
		clubObject.setObjectDetails(objectDetails);
		System.out.println("benefit list of club has been updates :"+benefits.toString());
		
		//can be a bug because put do not return nothing....
		//fix url
		restClient.put().uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}" ,
				clubObject.getObjectID().getId() ,
				superApp , 
				email ).
				body(clubObject).
				retrieve();
		System.out.println("link passed");

		System.out.println("the updated club: \n"+clubObject.toString());
	}
	
	
	public void addBenefitToClubintClubStringBenefit(RestClient restClient ,UserId userId , int ClubNumber , String benefitName)
	{
		//restClient  = "http://localhost:" + port + "/superapp
		//get club and benefit
		String club = "C"+ClubNumber;
		String superApp = userId.getSuperAPP();
		String email = userId.getEmail();
		//fix url
		ObjectBoundary clubObject =  restClient.get().uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}" ,
				superApp ,
				club ,
				superApp , 
				email ).
				retrieve().body(ObjectBoundary.class);
		System.out.println("link passed");

		//fix url
		ObjectBoundary benefitObject = restClient.get().uri("/objects/search"
				+ "/byAlias/{alias}"
				+ "{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}"
				+ "&size={size}&page={page}" ,
				benefitName ,
				superApp , 
				email,
				1,
				0).
				retrieve().body(ObjectBoundary.class);
		System.out.println("link passed");

		List <Integer> benefits = getALiistFromMap(clubObject.getObjectDetails(),"listOfBenefitOfClub");
		benefits.add((Integer) benefitObject.getObjectDetails().get("idBenefit"));
		Map<String, Object> objectDetails = clubObject.getObjectDetails();
		objectDetails.put("listOfBenefitOfClub", objectDetails);
		clubObject.setObjectDetails(objectDetails);
		System.out.println("benefit list of club has been updates :"+benefits.toString());
		
		//can be a bug because put do not return nothing....
		//fix url
		restClient.put().uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}" ,
				clubObject.getObjectID().getId() ,
				superApp , 
				email ).
				body(clubObject).
				retrieve();
		System.out.println("link passed");

		System.out.println("the updated club: \n"+clubObject.toString());
	}
	//4
	public void addBenefitToClubStringClubintBenefit(RestClient restClient ,UserId userId , String ClubName ,int benefitNumber)
	{
		//restClient  = "http://localhost:" + port + "/superapp
				//get club and benefit
				
				String superApp = userId.getSuperAPP();
				String email = userId.getEmail();
				//fix url
				ObjectBoundary clubObject =  restClient.get().uri("/objects/search"
						+ "/byAlias/{alias}"
						+ "{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}"
						+ "&size={size}&page={page}" ,
						ClubName ,
						superApp , 
						email,
						1,
						0).
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

				List <Integer> benefits = getALiistFromMap(clubObject.getObjectDetails(),"listOfBenefitOfClub");
				benefits.add(benefitNumber);
				Map<String, Object> objectDetails = clubObject.getObjectDetails();
				objectDetails.put("listOfBenefitOfClub", objectDetails);
				clubObject.setObjectDetails(objectDetails);
				System.out.println("benefit list of club has been updates :"+benefits.toString());
				
				//can be a bug because put do not return nothing....
				//fix url
				restClient.put().uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}" ,
						clubObject.getObjectID().getId() ,
						superApp , 
						email ).
						body(clubObject).
						retrieve();
				System.out.println("link passed");

				System.out.println("the updated club: \n"+clubObject.toString());
	}
	
	
	
	
	public List<Integer> getALiistFromMap(Map<String, Object> objectDetails , String key)
	{
		List<Object> objects = (List)(objectDetails.get(key));
		List <Integer> numbers = 	objects.stream().map(Object::toString)
				.map(str->Integer.parseInt(str))
				.toList();
		return numbers;
	}
	
	
	
}

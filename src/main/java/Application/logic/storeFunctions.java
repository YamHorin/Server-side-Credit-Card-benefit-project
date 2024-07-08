package Application.logic;

import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestClient;

import Application.business_logic.Boundaies.ObjectBoundary;
import Application.business_logic.javaObjects.UserId;

public class storeFunctions {

	//function already been made....
//	public void addBenefitToStore(RestClient restClient ,UserId userId , int StoreNumber , int benefitNumber)
//	{
//	//restClient  = "http://localhost:" + port + "/superapp
//		String storeId  = "S"+StoreNumber;
//		String superApp = userId.getSuperAPP();
//		String email = userId.getEmail();
//		//fix url
//		ObjectBoundary StoreObject =  restClient.get().uri("\"to_complite\" ",
//				superApp,
//				storeId,
//				superApp , 
//				email ).
//				retrieve().body(ObjectBoundary.class);
//		
//		String benefitId  = "B"+benefitNumber;
//		//fix url
//		ObjectBoundary benefitObject =  restClient.get().uri("\"to_complite\" ",
//				superApp,
//				benefitId,
//				superApp , 
//				email ).
//				retrieve().body(ObjectBoundary.class);
//		
//		List <Integer> benefits = getALiistFromMap(StoreObject.getObjectDetails(),"listOfBenefitOfStore");
//		benefits.add(benefitNumber);
//		Map<String, Object> objectDetails = StoreObject.getObjectDetails();
//		objectDetails.put("listOfBenefitOfStore", objectDetails);
//		StoreObject.setObjectDetails(objectDetails);
//		System.out.println("benefit list of store has been updates :"+benefits.toString());
//		
//		//fix url
//		StoreObject =  restClient.put().uri("\"to_complite\" ",
//				superApp,
//				storeId,
//				superApp, 
//				email).
//				body(StoreObject).
//				retrieve().body(ObjectBoundary.class);
//		System.out.println("the updated club: \n"+StoreObject.toString());
//	}
//	
//		

	public void printAllBenefitsOfStore(RestClient restClient ,UserId userId , int StoreNumber)
	{
		//restClient  = "http://localhost:" + port + "/superapp
		String storeId  = "S"+StoreNumber;
		String superApp = userId.getSuperAPP();
		String email = userId.getEmail();
		//fix url
		ObjectBoundary StoreObject =  restClient.get().uri("to_complite" ,
				storeId,
				superApp , 
				email ).
				retrieve().body(ObjectBoundary.class);
		List <Integer> benefits = getALiistFromMap(StoreObject.getObjectDetails(), "listOfBenefitOfStore");
		System.out.println("here are all the benefits in the store: \n\n");
		for (Integer benefitNumber : benefits) {
			//fix url
			ObjectBoundary benefit =  restClient.get().uri("to_complite" ,
					superApp,
					"B"+benefitNumber ,
					superApp , 
					email ).
					retrieve().body(ObjectBoundary.class);
			System.out.println("benefit:"+benefit.getAlias()+"\n"+"description: "
					+benefit.getObjectDetails().get("description")+"\n");
		}
		
		
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

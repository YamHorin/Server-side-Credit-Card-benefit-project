package Application.logic.restClientJava;

import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestClient;

import Application.business_logic.Boundaies.ObjectBoundary;
import Application.business_logic.javaObjects.UserId;

public class storeFunctions {

//TODO a new add store function?
	

	public void printAllBenefitsOfStore(RestClient restClient ,UserId userId , int StoreNumber)
	{
		//restClient  = "http://localhost:" + port + "/superapp
		String storeId  = "S"+StoreNumber;
		String superApp = userId.getSuperapp();
		String email = userId.getEmail();
		ObjectBoundary StoreObject =  restClient.get().uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}",
				superApp,
				storeId,
				superApp , 
				email ).
				retrieve().body(ObjectBoundary.class);
		System.out.println("link url sent GET :/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}\n");
		List <Integer> benefits = getALiistFromMap(StoreObject.getObjectDetails(), "listOfBenefitsOfStore");
		System.out.println("here are all the benefits in the store: \n\n");
		for (Integer benefitNumber : benefits) {
			ObjectBoundary benefit =  restClient.get().uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}" ,
					superApp,
					"B"+benefitNumber,
					superApp, 
					email ).
					retrieve().body(ObjectBoundary.class);
			System.out.println("benefit:"+benefit.getAlias()+"\n"+"description: "
					+benefit.getObjectDetails().get("description")+"\n");
		}

	}
	
	public List<Integer> getALiistFromMap(Map<String, Object> objectDetails , String key)
	{
		List<Object> objects = (List)(objectDetails.get(key));
		List <Integer> numbers = objects.stream().map(Object::toString)
				.map(str->Integer.parseInt(str))
				.toList();
		return numbers;
	}	
	
}

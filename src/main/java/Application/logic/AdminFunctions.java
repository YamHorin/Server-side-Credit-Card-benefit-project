package Application.logic;

import org.springframework.web.client.RestClient;

import Application._a_Presentation.Exceptions.UnauthorizedException;
import Application.business_logic.Boundaies.ObjectBoundary;
import Application.business_logic.Boundaies.UserBoundary;
import Application.business_logic.Boundaies.RoleEnumBoundary;
import Application.business_logic.javaObjects.UserId;

public class AdminFunctions {

	public void deleteClub(RestClient restClient ,UserId userId , int clubNumber)
	{
		
		//TODO fix it so superapp user will update 
		String idClub = "C"+clubNumber;
		String superApp = userId.getSuperapp();
		String email = userId.getEmail();
		//get user and change the role to super app so it will be can change the object
//		UserBoundary user = restClient.get().uri("/users/login/"
//				+ "{superapp}/{email}",superApp , email)
//				.retrieve()
//				.body(UserBoundary.class);
//		System.out.println("link passed");
//		if (user.getRole()!= RoleEnumBoundary.ADM_USER)
//			throw new UnauthorizedException("only admin can delete club...");
//		
//		user.setRole(RoleEnumBoundary.SUPERAPP_USER);
//		//url check
//		restClient.put().uri("/users/{superapp}/{userEmail}" , superApp ,email).body(user).retrieve();
//		System.out.println("link passed");
		

		
		ObjectBoundary clubObject = new ObjectBoundary();
		clubObject.setActive(false);
		restClient.put().uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}" ,
				superApp,
				idClub,
				superApp, 
				email).
				body(clubObject).
				retrieve();
		System.out.println("link passed");
//		 String uri = String.format("/objects/%s/%s?userSuperapp=%s&userEmail=%s",
//	                superApp, idClub, superApp, email);
		clubObject =  restClient.get()
				.uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}" ,
				superApp,
				idClub ,
				superApp, 
				email).
				retrieve().
				body(ObjectBoundary.class);
		System.out.println("link passed");
		System.out.println("the new club Object: \n"+clubObject.toString());
		
//		user.setRole(RoleEnumBoundary.ADM_USER);
//
//		System.out.println("link passed");
//		restClient.put().uri("/users/{superapp}/{userEmail}"  , superApp ,email).body(user).retrieve();
		
		
	}
	public void deleteStore(RestClient restClient ,UserId userId , int storeNumber)
	{
		String idStore = "S"+storeNumber;
		String superApp = userId.getSuperapp();
		String email = userId.getEmail();
		//get user and change the role to super app so it will be can change the object
//		UserBoundary user = restClient.get().uri("/users/login/"
//				+ "{superapp}/{email}",superApp , email)
//				.retrieve()
//				.body(UserBoundary.class);
//		System.out.println("link passed");
//		if (user.getRole()!= RoleEnumBoundary.ADM_USER)
//			throw new UnauthorizedException("only admin can delete club...");
//		
//		user.setRole(RoleEnumBoundary.SUPERAPP_USER);
//		//url check
//		restClient.put().uri("/users/{superapp}/{userEmail}" , superApp ,email).body(user).retrieve();
//		System.out.println("link passed");
//		

		
		ObjectBoundary clubObject = new ObjectBoundary();
		clubObject.setActive(false);
		restClient.put().uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}" ,
				superApp,
				idStore,
				superApp, 
				email).
				body(clubObject).
				retrieve();
//		 String uri = String.format("/objects/%s/%s?userSuperapp=%s&userEmail=%s",
//	                superApp, idClub, superApp, email);
		clubObject =  restClient.get()
				.uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}" ,
				superApp,
				idStore ,
				superApp, 
				email).
				retrieve().
				body(ObjectBoundary.class);
		System.out.println("the new store Object: \n"+clubObject.toString());
		
//		user.setRole(RoleEnumBoundary.ADM_USER);
//
//		System.out.println("link passed");
//		restClient.put().uri("/users/{superapp}/{userEmail}"  , superApp ,email).body(user).retrieve();
	}
	
}

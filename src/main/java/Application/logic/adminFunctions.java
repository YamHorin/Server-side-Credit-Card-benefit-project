package Application.logic;

import org.springframework.web.client.RestClient;

import Application._a_Presentation.Exceptions.UnauthorizedException;
import Application.business_logic.Boundaies.BoundaryObject;
import Application.business_logic.Boundaies.BoundaryUser;
import Application.business_logic.Boundaies.RoleEnumBoundary;
import Application.business_logic.javaObjects.UserId;

public class adminFunctions {

	public void deleteClub(RestClient restClient ,UserId userId , int clubNumber)
	{
		String idClub = "C"+clubNumber;
		String superApp = userId.getSuperAPP();
		String superApp1 = userId.getSuperAPP();
		String email = userId.getEmail();
		//get user and change the role to super app so it will be can change the object
		
		BoundaryUser user = restClient.get().uri("/users/login/{superapp}/{email}",
				superApp ,email).
				retrieve().body(BoundaryUser.class);
		if (user.getRole()!= RoleEnumBoundary.ADM_USER)
			throw new UnauthorizedException("only admin can delet club...");
		
		user.setRole(RoleEnumBoundary.SUPERAPP_USER);
		//url check
		restClient.put().uri("/users/{superapp}/{userEmail}" , superApp ,email).body(user).retrieve();
		
		//System.out.println("user update to super app so it can update the club....\n\n");
		
//		BoundaryObject clubObject =  restClient.get()
//				.uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&email={email}",
//				superApp,
//				idClub ,
//				superApp, 
//				email).
//				retrieve().
//				body(BoundaryObject.class);
		
		BoundaryObject clubObject = new BoundaryObject();
		clubObject.setActive(false);
		//can't enter the link and change for some reason....
		restClient.put().uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&email={email}",
				superApp,
				idClub,
				superApp, 
				email).
				body(clubObject).
				retrieve();
		
		clubObject =  restClient.get()
				.uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&email={email}",
				superApp,
				idClub ,
				superApp, 
				email).
				retrieve().
				body(BoundaryObject.class);
		System.out.println("clubObject: "+clubObject.toString());
		
		user.setRole(RoleEnumBoundary.ADM_USER);

		restClient.put().uri("/users/{superapp}/{userEmail}" , superApp ,email).body(user).retrieve();
		
		
	}
	public void deleteStore(RestClient restClient ,UserId userId , int storeNumber)
	{
		String idStore = "S"+storeNumber;
		String superApp = userId.getSuperAPP();
		String email = userId.getEmail();
		//get user and change the role to super app so it will be can change the object
		
		BoundaryUser user = restClient.get().uri("/users/login/{superapp}/{email}",
				superApp ,email).
				retrieve().body(BoundaryUser.class);
		if (user.getRole()!= RoleEnumBoundary.ADM_USER)
			throw new UnauthorizedException("only admin can delet club...");
		
		user.setRole(RoleEnumBoundary.SUPERAPP_USER);
		
		restClient.put().uri("/users/{superapp}/{userEmail}" , superApp ,email).body(user);
		System.out.println("user update to super app so it can update the club....\n\n");
		
		BoundaryObject clubObject =  restClient.get().uri("/objects/{superapp}"
				+ "/{id}"
				+ "?userSuperapp={2024B.gal.angel}&email={DanielAnderson%40gmail.com}",
				superApp,
				idStore ,
				superApp , 
				email ).
				retrieve().body(BoundaryObject.class);
		clubObject.setActive(false);
		restClient.put().uri("/objects/{superapp}/{id}"
				+ "?userSuperApp ={userSuperapp}&email = {email}",
				superApp,
				idStore,
				superApp , 
				email ).
				body(clubObject).
				retrieve();
		user.setRole(RoleEnumBoundary.ADM_USER);

		restClient.put().uri("/users/{superapp}/{userEmail}" , superApp ,email).body(user);
	}
	
}

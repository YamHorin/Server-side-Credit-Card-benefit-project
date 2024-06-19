package demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import Application.DataAccess.Location;
import Application._a_Presentation.BoundaryIsNotFoundException;
import Application.business_logic.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class Applicationtests2 {
	private RestClient restClientObj;
	private RestClient restClientUser;
	private RestClient restClientCommand;
	private RestClient restClientAdmin;
    @Value("${super.app.name}")
	private String superAppName;

	
	@Value("${server.port:8080}")
	public void setPort(int port, String superAppName) {
		this.restClientObj = RestClient.create("http://localhost:" + port + "/superapp/objects");
		this.restClientAdmin = RestClient.create("http://localhost:" + port +"/superapp/admin" );
		this.restClientUser = RestClient.create("http://localhost:" + port +"/superapp/users" );
		this.restClientCommand = RestClient.create("http://localhost:" + port +"/superapp/commands");
		this.superAppName =superAppName;
	
	}

	@BeforeEach
	public void setup() {
	}
	
	@AfterEach
	public void tearDown() {

	}
	
	@Test
	public void contextLoads() {
		
	}
	@Test
	public void testGetAllObjectsToUserSuperApp()throws Exception
	{
		// GIVEN the server is up
		// AND the database contains 4 active objects
		// AND the database contains 4 non active objects
		// AND the database contains 1 super app user
		String username = "superUser";
		BoundaryUser user  = new BoundaryUser();
		user.setUserName(username);
		user.setRole(RoleEnumBoundary.SUPERAPP_USER);
		UserId UserId = new UserId();
		UserId.setEmail(username+"@aa.com");
		user.setUserId(UserId);
		//post a super app user 
		this.restClientUser.post().body(user).retrieve().body(BoundaryUser.class);
		List<BoundaryObject> actives = new ArrayList<>();
		String type = "type_test";
		String alias = "alias_test";
		String createdBy = "yam_test_@yam.com";
		for (int i = 0; i < 4; i++) {
			BoundaryObject obj = new BoundaryObject();
			obj.setActive(true);
			obj.setLocation(new Location(0.2+i , 0.2+i));
			obj.setType(type+" "+i);
			obj.setAlias(alias+" "+i);
			CreatedBy CreatedBy  = new CreatedBy();
			UserId UserId1 = new UserId();
			UserId1.setEmail(createdBy+i);
			UserId1.setSuperAPP("");
			CreatedBy.setUserId(UserId1);
			obj.setCreatedBy(CreatedBy);
			obj.setObjectDetails(Collections.singletonMap("person", "Jane #" + i));
			// POST Objects to server

			obj = this.restClientObj.post().body(obj).retrieve().body(BoundaryObject.class);
			actives.add(obj);
		}
		List<BoundaryObject> non_actives = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			BoundaryObject obj = new BoundaryObject();
			obj.setActive(false);
			obj.setLocation(new Location(0.2+i , 0.2+i));
			obj.setType(type+" "+i);
			obj.setAlias(alias+" "+i);
			CreatedBy CreatedBy  = new CreatedBy();
			UserId UserId1 = new UserId();
			UserId1.setEmail(createdBy+i+"non_active");
			UserId1.setSuperAPP("");
			CreatedBy.setUserId(UserId1);
			obj.setCreatedBy(CreatedBy);
			obj.setObjectDetails(Collections.singletonMap("non_active", "non_active #" + i));
			// POST Objects to server

			obj = this.restClientObj.post().body(obj).retrieve().body(BoundaryObject.class);
			non_actives.add(obj);
		}
		// /superapp/objects?userSuperApp ={userSuperApp}&userEmail = {email}&size = {size}&page = {page}
		// WHEN I invoke GET /superapp/objects?userSuperApp ={2024B.gal.angel}&userEmail = {superUser@aa.com}&size = {10}&page = {1}
		BoundaryObject [] actual = this.restClientObj.get().uri("/superapp/objects?userSuperApp ={userSuperApp}&userEmail = {email}&size = {size}&page = {page}"
				,this.superAppName ,username+"@aa.com" , 10,0 )
				.retrieve().body(BoundaryObject [].class);
		
		// THEN the server responds with the same 8 objects generated above
		assertThat(actual).hasSize(8)
						.usingRecursiveFieldByFieldElementComparator()
						.containsExactlyInAnyOrderElementsOf(actives)
						.usingRecursiveFieldByFieldElementComparator()
						.doesNotContainAnyElementsOf(non_actives);
		

		
	}
	@Test
	public void testGetAllObjectsToUserMiniApp()throws Exception
	{
		// GIVEN the server is up
		// AND the database contains 4 active objects
		// AND the database contains 4 non active objects
		// AND the database contains 1 mini app user
		String username = "miniAppUser";
		BoundaryUser user  = new BoundaryUser();
		user.setUserName(username);
		user.setRole(RoleEnumBoundary.MINIAPP_USER);
		UserId UserId = new UserId();
		UserId.setEmail(username+"@aa.com");
		user.setUserId(UserId);
		//post a super app user 
		this.restClientUser.post().body(user).retrieve().body(BoundaryUser.class);
		List<BoundaryObject> actives = new ArrayList<>();
		String type = "type_test";
		String alias = "alias_test";
		String createdBy = "yam_test_@yam.com";
		for (int i = 0; i < 4; i++) {
			BoundaryObject obj = new BoundaryObject();
			obj.setActive(true);
			obj.setLocation(new Location(0.2+i , 0.2+i));
			obj.setType(type+" "+i);
			obj.setAlias(alias+" "+i);
			CreatedBy CreatedBy  = new CreatedBy();
			UserId UserId1 = new UserId();
			UserId1.setEmail(createdBy+i);
			UserId1.setSuperAPP("");
			CreatedBy.setUserId(UserId1);
			obj.setCreatedBy(CreatedBy);
			obj.setObjectDetails(Collections.singletonMap("person", "Jane #" + i));
			// POST Objects to server

			obj = this.restClientObj.post().body(obj).retrieve().body(BoundaryObject.class);
			actives.add(obj);
		}
		List<BoundaryObject> non_actives = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			BoundaryObject obj = new BoundaryObject();
			obj.setActive(false);
			obj.setLocation(new Location(0.2+i , 0.2+i));
			obj.setType(type+" "+i);
			obj.setAlias(alias+" "+i);
			CreatedBy CreatedBy  = new CreatedBy();
			UserId UserId1 = new UserId();
			UserId1.setEmail(createdBy+i+"non_active");
			UserId1.setSuperAPP("");
			CreatedBy.setUserId(UserId1);
			obj.setCreatedBy(CreatedBy);
			obj.setObjectDetails(Collections.singletonMap("non_active", "non_active #" + i));
			// POST Objects to server

			obj = this.restClientObj.post().body(obj).retrieve().body(BoundaryObject.class);
			non_actives.add(obj);
		}
		// /superapp/objects?userSuperApp ={userSuperApp}&userEmail = {email}&size = {size}&page = {page}
		// WHEN I invoke GET /superapp/objects?userSuperApp ={2024B.gal.angel}&userEmail = {superUser@aa.com}&size = {10}&page = {1}
		BoundaryObject [] actual = this.restClientObj.get().uri("/superapp/objects?userSuperApp ={userSuperApp}&userEmail = {email}&size = {size}&page = {page}"
				,this.superAppName ,username+"@aa.com" , 10,0 )
				.retrieve().body(BoundaryObject [].class);
		
		// THEN the server responds with  4 objects
		assertThat(actual).hasSize(4)
						.usingRecursiveFieldByFieldElementComparator()
						.containsExactlyInAnyOrderElementsOf(actives);
		
	}
	@Test
	public void testGetSpecificObjectForUserMiniApp()throws Exception
	{
		// GIVEN the server is up
		// AND the database contains 1  non active objects
		// AND the database contains 1 active objects
		// AND the database contains 1 mini app user
		
		String username = "miniAppUser";
		BoundaryUser user  = new BoundaryUser();
		user.setUserName(username);
		user.setRole(RoleEnumBoundary.MINIAPP_USER);
		UserId UserId = new UserId();
		UserId.setEmail(username+"@aa.com");
		user.setUserId(UserId);
		
		this.restClientUser.post().body(user).retrieve().body(BoundaryUser.class);
		String type = "type_test";
		String alias = "alias_test";
		String createdBy = username+"@aa.com";
		
		BoundaryObject obj = new BoundaryObject();
		obj.setActive(true);
		obj.setLocation(new Location(0.2 , 0.2));
		obj.setType(type+" ");
		obj.setAlias(alias+" ");
		CreatedBy CreatedBy  = new CreatedBy();
		UserId UserId1 = new UserId();
		UserId1.setEmail(createdBy);
		UserId1.setSuperAPP("");
		CreatedBy.setUserId(UserId1);
		obj.setCreatedBy(CreatedBy);
		obj.setObjectDetails(Collections.singletonMap("person", "Jane #"));
		// POST Objects to server
		obj = this.restClientObj.post().body(obj).retrieve().body(BoundaryObject.class);
		
		BoundaryObject obj1 = new BoundaryObject();
		obj1.setActive(false);
		obj1.setLocation(new Location(0.2 , 0.2));
		obj1.setType(type+" ");
		obj1.setAlias(alias+" ");
		CreatedBy CreatedBy1  = new CreatedBy();
		UserId UserId11 = new UserId();
		UserId11.setEmail(createdBy);
		UserId11.setSuperAPP("");
		CreatedBy1.setUserId(UserId11);
		obj1.setCreatedBy(CreatedBy1);
		obj1.setObjectDetails(Collections.singletonMap("person", "Jane #"));
		// POST Objects to server
		obj1 = this.restClientObj.post().body(obj1).retrieve().body(BoundaryObject.class);
		
		// WHEN I invoke GET on the false active object  /superapp/objects?userSuperApp ={2024B.gal.angel}&userEmail = {superUser@aa.com}
		// THEN I get an BoundaryIsNotFoundException
		try {
		BoundaryObject obj_return  = this.restClientObj.get().uri("/superapp/objects/{superapp}/{id}?userSuperApp ={userSuperApp}&userEmail = {email}",
				this.superAppName,obj1.getObjectID().getId(),this.superAppName ,username+"@aa.com").retrieve().body(BoundaryObject.class);
		
		}
		
		catch (BoundaryIsNotFoundException e)
		{
			System.out.println("we got a BoundaryIsNotFoundException yayyyyyyyyyyyy ");
		}
		// WHEN I invoke GET on the false active object  /superapp/objects?userSuperApp ={2024B.gal.angel}&userEmail = {superUser@aa.com}
		// THEN I get an BoundaryIsNotFoundException
		BoundaryObject obj_return  = this.restClientObj.get().uri("/superapp/objects/{superapp}/{id}?userSuperApp ={userSuperApp}&userEmail = {email}",
				this.superAppName,obj.getObjectID().getId(),this.superAppName ,username+"@aa.com").retrieve().body(BoundaryObject.class);
		assertThat(obj_return).isNotNull();
		assertThat(obj_return.getObjectID().getId()).isEqualTo(obj.getObjectID().getId());
	}
	
	@Test
	public void testGetSpecificObjectForUserSuperApp()throws Exception
	{
		// GIVEN the server is up
		// AND the database contains 1  non active objects
		// AND the database contains 1 active objects
		// AND the database contains 1 super app user
		
		String username = "SuperAppUser";
		BoundaryUser user  = new BoundaryUser();
		user.setUserName(username);
		user.setRole(RoleEnumBoundary.SUPERAPP_USER);
		UserId UserId = new UserId();
		UserId.setEmail(username+"@aa.com");
		user.setUserId(UserId);
		
		this.restClientUser.post().body(user).retrieve().body(BoundaryUser.class);
		String type = "type_test";
		String alias = "alias_test";
		String createdBy = username+"@aa.com";
		
		BoundaryObject obj = new BoundaryObject();
		obj.setActive(true);
		obj.setLocation(new Location(0.2 , 0.2));
		obj.setType(type+" ");
		obj.setAlias(alias+" ");
		CreatedBy CreatedBy  = new CreatedBy();
		UserId UserId1 = new UserId();
		UserId1.setEmail(createdBy);
		UserId1.setSuperAPP("");
		CreatedBy.setUserId(UserId1);
		obj.setCreatedBy(CreatedBy);
		obj.setObjectDetails(Collections.singletonMap("person", "Jane #"));
		// POST Objects to server
		obj = this.restClientObj.post().body(obj).retrieve().body(BoundaryObject.class);
		
		BoundaryObject obj1 = new BoundaryObject();
		obj1.setActive(false);
		obj1.setLocation(new Location(0.2 , 0.2));
		obj1.setType(type+" ");
		obj1.setAlias(alias+" ");
		CreatedBy CreatedBy1  = new CreatedBy();
		UserId UserId11 = new UserId();
		UserId11.setEmail(createdBy);
		UserId11.setSuperAPP("");
		CreatedBy1.setUserId(UserId11);
		obj1.setCreatedBy(CreatedBy1);
		obj1.setObjectDetails(Collections.singletonMap("person", "Jane #"));
		// POST Objects to server
		obj1 = this.restClientObj.post().body(obj1).retrieve().body(BoundaryObject.class);
		
		// WHEN I invoke GET on the false active object  /superapp/objects?userSuperApp ={2024B.gal.angel}&userEmail = {superUser@aa.com}
		// THEN I get an object

		BoundaryObject obj_return  = this.restClientObj.get().uri("/superapp/objects/{superapp}/{id}?userSuperApp ={userSuperApp}&userEmail = {email}",
				this.superAppName,obj1.getObjectID().getId(),this.superAppName ,username+"@aa.com").retrieve().body(BoundaryObject.class);
		assertThat(obj_return).isNotNull();
		assertThat(obj_return.getObjectID().getId()).isEqualTo(obj.getObjectID().getId());
	}
	public void testAdminGetAllMiniAppCommands()
	{
		// GIVEN the server is up
		// AND the database contains 1  non active objects
		// AND the database contains 1 active objects
		// AND the database contains 1 super app user
		
		String username = "AdminAppUser";
		BoundaryUser user  = new BoundaryUser();
		user.setUserName(username);
		user.setRole(RoleEnumBoundary.ADM_USER);
		UserId UserId = new UserId();
		UserId.setEmail(username+"@aa.com");
		user.setUserId(UserId);
		
		this.restClientUser.post().body(user).retrieve().body(BoundaryUser.class);
		String type = "type_test";
		String alias = "alias_test";
		String createdBy = username+"@aa.com";
		
		BoundaryObject obj = new BoundaryObject();
		obj.setActive(true);
		obj.setLocation(new Location(0.2 , 0.2));
		obj.setType(type+" ");
		obj.setAlias(alias+" ");
		CreatedBy CreatedBy  = new CreatedBy();
		UserId UserId1 = new UserId();
		UserId1.setEmail(createdBy);
		UserId1.setSuperAPP("");
		CreatedBy.setUserId(UserId1);
		obj.setCreatedBy(CreatedBy);
		obj.setObjectDetails(Collections.singletonMap("person", "Jane #"));
		// POST Objects to server
		obj = this.restClientObj.post().body(obj).retrieve().body(BoundaryObject.class);
		
		BoundaryObject obj1 = new BoundaryObject();
		obj1.setActive(false);
		obj1.setLocation(new Location(0.2 , 0.2));
		obj1.setType(type+" ");
		obj1.setAlias(alias+" ");
		CreatedBy CreatedBy1  = new CreatedBy();
		UserId UserId11 = new UserId();
		UserId11.setEmail(createdBy);
		UserId11.setSuperAPP("");
		CreatedBy1.setUserId(UserId11);
		obj1.setCreatedBy(CreatedBy1);
		obj1.setObjectDetails(Collections.singletonMap("person", "Jane #"));
		// POST Objects to server
		obj1 = this.restClientObj.post().body(obj1).retrieve().body(BoundaryObject.class);
		
		// WHEN I invoke GET on the false active object  /superapp/objects?userSuperApp ={2024B.gal.angel}&userEmail = {superUser@aa.com}
		// THEN I get an object

		BoundaryObject obj_return  = this.restClientObj.get().uri("/superapp/objects/{superapp}/{id}?userSuperApp ={userSuperApp}&userEmail = {email}",
				this.superAppName,obj1.getObjectID().getId(),this.superAppName ,username+"@aa.com").retrieve().body(BoundaryObject.class);
		assertThat(obj_return).isNotNull();
		assertThat(obj_return.getObjectID().getId()).isEqualTo(obj.getObjectID().getId());
	}
	
	
	
	
	
}

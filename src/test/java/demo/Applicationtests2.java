package demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import Application.DataAccess.Location;
import Application.business_logic.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

class Applicationtests2 {
	private RestClient restClientObj;
	private RestClient restClientUser;
	private RestClient restClientCommand;
	private RestClient restClientAdmin;
    @Value("${super.app.name}")
	private String superAppName;

	
	@Value("${server.port:8080}")
	public void setPort(int port, String superAppName) {
		this.restClientObj = RestClient.create(
			"http://localhost:" + port + "/superapp/users");
		this.restClientAdmin = RestClient.create("http://localhost:" + port +"/superapp/admin" );
		this.restClientUser = RestClient.create("http://localhost:" + port +"/superapp/users" );
		this.superAppName =superAppName;
	
	}

	@BeforeEach
	public void setup() {
		// DO NOTHING
	}
	
	@AfterEach
	public void tearDown() {
		// invoke DELETE
		this.restClientAdmin
			.delete()
			.retrieve();
	}
	
	@Test
	public void contextLoads() {
		
	}
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
	
	
	
	
	
	
	
}

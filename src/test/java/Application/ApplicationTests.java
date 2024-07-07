package Application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.web.client.RestClient;

import Application.business_logic.*;
import Application.business_logic.Boundaies.ObjectBoundary;
import Application.business_logic.Boundaies.UserBoundary;
import Application.business_logic.Boundaies.NewUserBoundary;
import Application.business_logic.Boundaies.RoleEnumBoundary;
import Application.business_logic.javaObjects.CreatedBy;
import Application.business_logic.javaObjects.Location;
import Application.business_logic.javaObjects.UserId;


@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ApplicationTests {
	private RestClient restClientObj;
	private RestClient restClientUser;
	private RestClient restClientCommand;
	private RestClient restClientAdmin;
    @Value("${spring.application.name}")
	private String superAppName;

	
	@Value("${server.port:8080}")
	public void setPort(int port) {
		this.restClientObj = RestClient.create("http://localhost:" + port + "/superapp/objects");
		this.restClientAdmin = RestClient.create("http://localhost:" + port +"/superapp/admin" );
		this.restClientUser = RestClient.create("http://localhost:" + port +"/superapp/users" );
		this.restClientCommand = RestClient.create("http://localhost:" + port +"/superapp/commands");
	
	}
	
    @BeforeEach
    public void setUp() {
        
    }
    @AfterEach
    public void tearDown() {
        try {
            String username = "admUser";
            NewUserBoundary user = new NewUserBoundary();
            user.setUserName(username);
            user.setRole(RoleEnumBoundary.ADM_USER);
            user.setEmail(username + "@aa.com");
            user.setAvatar("houj");
            
            // Post a app user 
            this.restClientUser.post().body(user).retrieve().body(UserBoundary.class);
            System.out.println("Posted ADM_USER: " + username);


            // Delete all objects
            this.restClientAdmin.delete().uri("/objects?userSuperapp={userSuperApp}&email={email}",
                this.superAppName, username + "@aa.com").retrieve();
            System.out.println("Deleted all objects");

            // Delete all commands
            this.restClientAdmin.delete().uri("/miniapp?userSuperapp={userSuperApp}&email={email}",
                this.superAppName, username + "@aa.com").retrieve();
            System.out.println("Deleted all commands");
            
            // Delete all users
            this.restClientAdmin.delete().uri("/users?userSuperapp={userSuperApp}&email={email}",
            		this.superAppName, username + "@aa.com").retrieve();
            System.out.println("Deleted all users");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error during tearDown: " + e.getMessage());
        }
    }

    @Test
    public void testCreateUser() {
    	// GIVEN the server is up
        UserBoundary BoundaryUser  = new UserBoundary();
        String username = "superUser";
		NewUserBoundary user  = new NewUserBoundary();
		user.setUserName(username);
		user.setRole(RoleEnumBoundary.SUPERAPP_USER);
		user.setEmail(username+"@aa.com");
		user.setAvatar("testAvatar");
		// WHEN I invoke POST /superapp/users
		
		BoundaryUser = this.restClientUser.post().body(user).retrieve().body(UserBoundary.class);
        
		// THEN the server responds with the same user
 		assertThat(BoundaryUser).isNotNull();
 		assertThat(BoundaryUser.getUserName()).isEqualTo("superUser");
 		assertThat(BoundaryUser.getRole().name()).isEqualTo("SUPERAPP_USER");
 		assertThat(BoundaryUser.getAvatar()).isEqualTo("testAvatar");
	
 	}
    
    @Test
    public void testObject() {
    	// GIVEN the server is up
    	//and user super app is on 
    	
    	 UserBoundary BoundaryUser  = new UserBoundary();
         String username = "superUser";
 		NewUserBoundary user  = new NewUserBoundary();
 		user.setUserName(username);
 		user.setRole(RoleEnumBoundary.SUPERAPP_USER);
 		user.setEmail(username+"@aa.com");
 		user.setAvatar("testAvatar");
 		// WHEN I invoke POST /superapp/users
 		
 		this.restClientUser.post().body(user).retrieve().body(UserBoundary.class);
    	//Create a Object
    	ObjectBoundary BoundaryObject  = new ObjectBoundary();
    	//Set values for the object's attributes
    	ObjectBoundary obj = new ObjectBoundary();
		obj.setActive(false);
		obj.setLocation(new Location(0.2 , 0.2));
		obj.setType("type");
		obj.setAlias("alias");
		
		CreatedBy CreatedBy  = new CreatedBy();
		UserId UserId1 = new UserId();
		UserId1.setEmail(username+"@aa.com");
		UserId1.setSuperAPP(this.superAppName);
		CreatedBy.setUserId(UserId1);
		obj.setCreatedBy(CreatedBy);
		obj.setObjectDetails(Collections.singletonMap("test", "test"));
		// WHEN i invoke POST in /superapp/objects

		BoundaryObject = this.restClientObj.post().body(obj).retrieve().body(ObjectBoundary.class);


		// THEN the server responds with the same  object generated above
        assertThat(BoundaryObject).isNotNull();
        assertThat(BoundaryObject.getType()).isEqualTo("type");
        assertThat(BoundaryObject.getAlias()).isEqualTo("alias");
        assertThat(BoundaryObject.getActive()).isFalse();
 	}

   
    
    
}
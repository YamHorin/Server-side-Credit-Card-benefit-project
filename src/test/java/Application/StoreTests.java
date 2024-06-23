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


@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class StoreTests {
	private RestClient restClient;
	private RestClient restClientObj;
	private RestClient restClientAdmin;
	private RestClient restClientUser;
	private RestClient restClientCommand;
	
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

    @Test
    public void testCreateUser() {
        // Create a new user
        NewUserBoundary newUser = new NewUserBoundary();
        BoundaryUser BoundaryUser  = new BoundaryUser();
        newUser.setEmail("testemail@example.com");
        newUser.setUserName("Test User");
        newUser.setRole("USER");
        newUser.setAvatar("testAvatar");
        
        BoundaryUser = this.restClientUser
				.post()
				.body(newUser)
				.retrieve()
				.body(BoundaryUser.class);
        
        // Check if the user is initialized correctly
 		assertThat(BoundaryUser).isNotNull();
 		assertThat(BoundaryUser.getUserId().getEmail()).isEqualTo("testemail@example.com");
 		assertThat(BoundaryUser.getUserName()).isEqualTo("Test User");
 		assertThat(BoundaryUser.getRole().name()).isEqualTo("USER");
 		assertThat(BoundaryUser.getAvatar()).isEqualTo("testAvatar");
	
 	}
    
    @Test
    public void testObject() {
    	//Create a Object
    	BoundaryObject BoundaryObject  = new BoundaryObject();
    	//Set values for the object's attributes
    	ObjectId ob = new ObjectId();
    	ob.setId("1245");
    	BoundaryObject.setObjectID(ob);
    	BoundaryObject.setType("Type");
    	BoundaryObject.setAlias("Alias");
    	BoundaryObject.setActive(true);
    	CreatedBy cb = new CreatedBy();
		String createdBy = "yam_test_@yam.com";
		CreatedBy CreatedBy  = new CreatedBy();
		UserId UserId1 = new UserId();
		UserId1.setEmail(createdBy);
		UserId1.setSuperAPP("");
		CreatedBy.setUserId(UserId1);
		BoundaryObject.setObjectDetails(new HashMap<>());
        
       BoundaryObject = this.restClientObj
			.post()
			.body(BoundaryObject)
			.retrieve()
			.body(BoundaryObject.class);

        //Check if the object is initialized correctly
        assertThat(BoundaryObject).isNotNull();
        assertThat(BoundaryObject.getObjectID().toString()).isEqualTo("12345");
        assertThat(BoundaryObject.getType()).isEqualTo("Type");
        assertThat(BoundaryObject.getAlias()).isEqualTo("Alias");
        assertThat(BoundaryObject.getActive()).isTrue();
 	}

    @Test
    public void testcommand() {
      //Create a command
       BoundaryCommand BoundaryCommand  = new BoundaryCommand();
     //Set values for the object's attributes
       CommandId ci = new CommandId();
       ci.setId("1234567");
       BoundaryCommand.setCommandId(ci);
       BoundaryCommand.setCommand("Test Command");
       BoundaryCommand.setTargetObject(new TargetObject());
       String createdBy = "yam_test_@yam.com";
	   CreatedBy CreatedBy  = new CreatedBy();
	   UserId UserId1 = new UserId();
	   UserId1.setEmail(createdBy);
	   UserId1.setSuperAPP("");
	   CreatedBy.setUserId(UserId1);
       BoundaryCommand.setCommandAttributes(new HashMap<>());
        
       BoundaryCommand = this.restClientCommand
			.post()
			.body(BoundaryCommand)
			.retrieve()
			.body(BoundaryCommand.class);

     //Check if the object is initialized correctly
       assertThat(BoundaryCommand).isNotNull();
       assertThat(BoundaryCommand.getCommand()).isEqualTo("Test Command");
       assertThat(BoundaryCommand.getTargetObject()).isNotNull();
 	}
    
    @Test
    public void putUser() {
        NewUserBoundary newUser = new NewUserBoundary();
        BoundaryUser BoundaryUser  = new BoundaryUser();
        newUser.setAvatar("testAvatar");
        
        BoundaryUser = this.restClientUser
				.put()
				.body(newUser)
				.retrieve()
				.body(BoundaryUser.class);
        

        assertThat(BoundaryUser).isNotNull();
 		assertThat(BoundaryUser.getAvatar()).isEqualTo("testAvatar");

    }
    @Test
    public void putCommand() {
        BoundaryCommand BoundaryCommand  = new BoundaryCommand();
        BoundaryCommand.setCommand("Test Command");
        
        BoundaryCommand = this.restClientCommand
    			.put()
    			.body(BoundaryCommand)
    			.retrieve()
    			.body(BoundaryCommand.class);
        

        assertThat(BoundaryCommand).isNotNull();
        assertThat(BoundaryCommand.getCommand()).isEqualTo("Test Command");

    }
    
    @Test
    public void putObject() {
    	BoundaryObject BoundaryObject  = new BoundaryObject();
    	BoundaryObject.setType("Type");
        
        BoundaryObject = this.restClientObj
    			.post()
    			.body(BoundaryObject)
    			.retrieve()
    			.body(BoundaryObject.class);
        

        assertThat(BoundaryObject).isNotNull();
        assertThat(BoundaryObject.getType()).isEqualTo("Type");

    }
    
    
}
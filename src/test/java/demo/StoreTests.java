package demo;

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

import Application.business_logic.BoundaryObject;
import Application.business_logic.BoundaryUser;
import Application.business_logic.NewUserBoundary;
import Application.business_logic.NewObjectBoundary;


@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class StoreTests {
	private RestClient restClient;
	
	@Value("${server.port:8080}")
	public void setPort(int port) {
		this.restClient = RestClient.create(
			"http://localhost:" + port + "/superapp/users");
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
        
        BoundaryUser = this.restClient
				.post()
				.body(newUser)
				.retrieve()
				.body(BoundaryUser.class);
    
        // THEN the server responds with the same 4 important messages generated above
 		assertThat(BoundaryUser).isNotNull();
 		assertThat(BoundaryUser.getUserId().getEmail()).isEqualTo("testemail@example.com");
 		assertThat(BoundaryUser.getUserName()).isEqualTo("Test User");
 		assertThat(BoundaryUser.getRole().name()).isEqualTo("USER");
 		assertThat(BoundaryUser.getAvatar()).isEqualTo("testAvatar");
	
 	}
    @Test
    public void testObject() {
        
        NewObjectBoundary newObject = new NewObjectBoundary();
        BoundaryObject BoundaryUser  = new BoundaryObject();
        newObject.setEmail("testemail@example.com");
        newObject.setUserName("Test User");
        newObject.setRole("USER");
        newObject.setAvatar("testAvatar");
        
        BoundaryObject = this.restClient
				.post()
				.body(newObject)
				.retrieve()
				.body(BoundaryUser.class);
    
        // THEN the server responds with the same 4 important messages generated above
 		assertThat(BoundaryUser).isNotNull();
 		assertThat(BoundaryUser.getUserId().getEmail()).isEqualTo("testemail@example.com");
 		assertThat(BoundaryUser.getUserName()).isEqualTo("Test User");
 		assertThat(BoundaryUser.getRole().name()).isEqualTo("USER");
 		assertThat(BoundaryUser.getAvatar()).isEqualTo("testAvatar");
	
 	}

}
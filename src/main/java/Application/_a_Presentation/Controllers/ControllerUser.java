package Application._a_Presentation.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Application._a_Presentation.Exceptions.BoundaryIsNotFoundException;
import Application.business_logic.Boundaies.UserBoundary;
import Application.business_logic.Boundaies.NewUserBoundary;
import Application.business_logic.DataService.ServicesUser;

@RestController
@RequestMapping(path = { "/superapp/users" })
public class ControllerUser {
	private ServicesUser servicesUser;
	
	public ControllerUser(ServicesUser serviceUser) {
		this.servicesUser = serviceUser;
	}

	@PostMapping(
		consumes = MediaType.APPLICATION_JSON_VALUE, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary createUser(@RequestBody NewUserBoundary message) {
		UserBoundary user = message.newUserToUserBoundary();
		return this.servicesUser.createUser(user);			
		
	}

	@GetMapping(
		path = { "/login/{superapp}/{email}" }, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary getSpecificUser(@PathVariable("email") String email ,@PathVariable("superapp") String superapp ) {
		String id  = email+" "+superapp;
		Optional<UserBoundary> User = this.servicesUser
			.getSpecificUser(id);
		
		if (User.isPresent()) {
			return User.get();
		}else {
			throw new BoundaryIsNotFoundException("could not find Specific User by id: " + id);
		}
	
	}
	@Value("${spring.application.name:Jill}")
	private String getSuperAppName(String defaultFirstName) {
		return defaultFirstName;
		
	}

	
	@PutMapping(
		path = {"/{superapp}/{userEmail}"},
		consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateUser (@PathVariable("userEmail") String email ,@PathVariable("superapp") String superapp, 
			@RequestBody UserBoundary update) {
		String id  = email+" "+superapp;
		this.servicesUser
			.updateUser(id, update);
	}
}





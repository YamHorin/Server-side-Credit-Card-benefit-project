package Application._a_Presentation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Application.business_logic.BoundaryUser;
import Application.business_logic.NewUserBoundary;
import Application.business_logic.ServicesUser;

@RestController
@RequestMapping(path = { "/Credit_Card_Benefit_app/users" })
public class ControllerUser {
	private ServicesUser servicesUser;
	
	public ControllerUser(ServicesUser serviceUser) {
		this.servicesUser = serviceUser;
	}

	@PostMapping(
		consumes = MediaType.APPLICATION_JSON_VALUE, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryUser createUser(@RequestBody NewUserBoundary message) {
		BoundaryUser user = message.newUserToUserBoundary();
		try {
			return this.servicesUser.createUser(user);			
		}
		catch (Boundary_is_not_filled_correct e) {
		}
		return null;
		
	}

	@GetMapping(
		path = { "/Credit_Card_Benefit_app/users/login/{superapp}/{email}" }, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryUser getSpecificUser(@PathVariable("email") String email ,@PathVariable("superapp") String superapp ) {
		String id  = email+"_"+superapp;
		Optional<BoundaryUser> User = this.servicesUser
			.getSpecificUser(id);
		
		if (User.isPresent()) {
			return User.get();
		}else {
			throw new Boundary_is_not_found_exception("could not find Specific User by id: " + id);
		}
	
	}
	@Value("${spring.application.name:Jill}")
	private String getSuperAppName(String defaultFirstName) {
		return defaultFirstName;
		
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryUser[] getAllUsers() {
		return this.servicesUser
			.getAllUsers()
			.toArray(new BoundaryUser[0]);
	}
	
	@DeleteMapping
	public void deleteAll() {
		this.servicesUser.deleteAllUsers();
	}
	
	@PutMapping(
		path = {"/Credit_Card_Benefit_app/users/login/{superapp}/{Useremail}"},
		consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateUser (@PathVariable("email") String email ,@PathVariable("superapp") String superapp, 
			@RequestBody BoundaryUser update) {
		String id  = email+"_"+superapp;
		this.servicesUser
			.updateUser(id, update);
	}
}





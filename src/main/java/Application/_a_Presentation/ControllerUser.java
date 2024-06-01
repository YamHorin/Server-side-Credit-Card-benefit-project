package Application._a_Presentation;

import java.util.Optional;

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
import Application.business_logic.ServicesUser;

@RestController
@RequestMapping(path = { "/demo" })
public class ControllerUser {
	private ServicesUser servicesUser;
	
	public ControllerUser(ServicesUser serviceUser) {
		this.servicesUser = serviceUser;
	}

	@PostMapping(
		consumes = MediaType.APPLICATION_JSON_VALUE, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryUser store(@RequestBody BoundaryUser message) {
		return this.servicesUser.createUser(message);
	}

	@GetMapping(
		path = { "/{id}" }, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryUser getSpecificMessage(@PathVariable("id") String id) {
		Optional<BoundaryUser> demoOp = this.servicesUser
			.getSpecificUser(id);
		
		if (demoOp.isPresent()) {
			return demoOp.get();
		}else {
			throw new RuntimeException("could not find message by id: " + id);
		}
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryUser[] getAllMessages() {
		return this.servicesUser
			.getAllUsers()
			.toArray(new BoundaryUser[0]);
	}
	
	@DeleteMapping
	public void deleteAll() {
		this.servicesUser.deleteAllUsers();
	}
	
	@PutMapping(
		path = {"/{id}"},
		consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void update (
			@PathVariable("id") String id, 
			@RequestBody BoundaryUser update) {
		this.servicesUser
			.updateUser(id, update);
	}
}





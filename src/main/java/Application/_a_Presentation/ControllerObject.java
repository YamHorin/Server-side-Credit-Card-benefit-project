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

import Application.business_logic.BoundaryObject;
import Application.business_logic.ServicesObject;

@RestController
@RequestMapping(path = { "/superapp/objects" })
public class ControllerObject {
	private ServicesObject servicesObject;
	
	public ControllerObject(ServicesObject servicesObject) {
		this.servicesObject = servicesObject;
	}

	@PostMapping(
		consumes = MediaType.APPLICATION_JSON_VALUE, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryObject Create_an_object(@RequestBody BoundaryObject message) {
		return this.servicesObject.createObject(message);
	}

	@GetMapping(
		path = { "/superapp/objects/{Credit_Card_Benefit_app}/{id}" }, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryObject Retrieve_object (@PathVariable("id") String id) {
		Optional<BoundaryObject> demoOp = this.servicesObject
			.getSpecificObj(id);
		
		if (demoOp.isPresent()) {
			return demoOp.get();
		}else {
			throw new RuntimeException("could not find message by id: " + id);
		}
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryObject[] Get_all_objects() {
		return this.servicesObject
			.getAllObjects()
			.toArray(new BoundaryObject[0]);
	}
	
	@DeleteMapping
	public void deleteAll() {
		this.servicesObject.deleteAllObjs();
	}
	
	@PutMapping(
		path = {"/superapp/objects/{Credit_Card_Benefit_app}/{id}"},
		consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void update (
			@PathVariable("id") String id, 
			@RequestBody BoundaryObject update) {
		this.servicesObject
			.updateObj(id, update);
	}
}





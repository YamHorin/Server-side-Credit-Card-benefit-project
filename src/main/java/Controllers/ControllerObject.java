package Controllers;

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
import Services.ServicesObject;
import Boundary.BoundaryObject;

@RestController
@RequestMapping(path = { "/demo" })
public class ControllerObject {
	private ServicesObject servicesObject;
	
	public ControllerObject(ServicesObject servicesObject) {
		this.servicesObject = servicesObject;
	}

	@PostMapping(
		consumes = MediaType.APPLICATION_JSON_VALUE, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryObject store(@RequestBody BoundaryObject message) {
		return this.servicesObject.createDemo(message);
	}

	@GetMapping(
		path = { "/{id}" }, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryObject getSpecificMessage(@PathVariable("id") String id) {
		Optional<BoundaryObject> demoOp = this.servicesObject
			.getSpecificDemo(id);
		
		if (demoOp.isPresent()) {
			return demoOp.get();
		}else {
			throw new RuntimeException("could not find message by id: " + id);
		}
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryObject[] getAllMessages() {
		return this.servicesObject
			.getAllDemoes()
			.toArray(new BoundaryObject[0]);
	}
	
	@DeleteMapping
	public void deleteAll() {
		this.servicesObject.deleteAllDemoes();
	}
	
	@PutMapping(
		path = {"/{id}"},
		consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void update (
			@PathVariable("id") String id, 
			@RequestBody BoundaryObject update) {
		this.servicesObject
			.updateDemo(id, update);
	}
}





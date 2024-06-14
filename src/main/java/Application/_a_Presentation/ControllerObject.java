package Application._a_Presentation;

import java.util.Optional;

import org.springframework.http.MediaType;
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
	public BoundaryObject CreateAnObject(@RequestBody BoundaryObject message) {
		try {
			return this.servicesObject.createObject(message);			
		}
		catch (BoundaryIsNotFilledCorrectException e) {
		}
		return null;
		
	}

	@GetMapping(
		path = { "/{superapp}/{id}" }, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryObject RetrieveAnObject ( @PathVariable("superapp") String superapp , @PathVariable("id") String id ) {
		
		Optional<BoundaryObject> demoOp = this.servicesObject
			.getSpecificObj(id ,superapp);
		
		if (demoOp.isPresent()) {
			return demoOp.get();
		}else {
			throw new BoundaryIsNotFoundException("could not find object by id: " + id);
		}
	}
	
	@GetMapping(
			
			produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryObject[] GetAllObjects() {
		return this.servicesObject
			.getAllObjects()
			.toArray(new BoundaryObject[0]);
	}
	

	
	@PutMapping(
		path = {"/{superapp}/{id}"},
		consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateObject (
			@PathVariable("id") String id,
			@PathVariable("superapp") String superapp,
			@RequestBody BoundaryObject update) {
		
		this.servicesObject
			.updateObj(id,superapp,update);
	}
}





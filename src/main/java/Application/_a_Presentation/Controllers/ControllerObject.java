package Application._a_Presentation.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Application._a_Presentation.Exceptions.BoundaryIsNotFoundException;
import Application.business_logic.Boundaies.ObjectBoundary;
import Application.business_logic.DataService.ServicesObject;

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
	public ObjectBoundary CreateAnObject(@RequestBody ObjectBoundary message) {
		return this.servicesObject.createObject(message);			
		
	}

	@GetMapping(
		path = { "/{superapp}/{id}" }, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public ObjectBoundary RetrieveAnObject ( 
			@PathVariable("superapp") String superapp , 
			@PathVariable("id") String id,
			@RequestParam("userSuperapp") String userSuperapp , 
			@RequestParam("userEmail") String email) {
		
		Optional<ObjectBoundary> demoOp = this.servicesObject
			.getSpecificObj(id ,superapp ,userSuperapp,email);
		
		if (demoOp.isPresent()) {
			return demoOp.get();
		}else {
			throw new BoundaryIsNotFoundException("could not find object by id: " + id);
		}
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ObjectBoundary[] GetAllObjects(
			@RequestParam("userSuperapp") String userSuperapp , 
			@RequestParam("email") String email,	
			@RequestParam("size") int size , 
			@RequestParam("page") int page
			) 
	{
		
		String id_user = email+" "+userSuperapp;
		return this.servicesObject
			.getAllObjects(id_user, size , page)
			.toArray(new ObjectBoundary[0]);
	}
	
	@GetMapping(
			path = {"/search/byType/{type}"},
			produces = MediaType.APPLICATION_JSON_VALUE)
		public ObjectBoundary[] searchObjectsByType (
			@PathVariable("type") String type , 
			@RequestParam("userSuperapp") String superapp ,
			@RequestParam("userEmail") String email, 
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
			String id = email+" "+superapp;	
			ObjectBoundary[] demoOp = this.servicesObject
				.searchByType(id,type, size, page).toArray(new ObjectBoundary[0]);
			return demoOp;
		}	

	@GetMapping(
			path = {"/search/byAlias/{alias}"}, 
			produces = MediaType.APPLICATION_JSON_VALUE)
		public ObjectBoundary[] searchObjectsByExactAlias ( 
				@PathVariable("alias") String alias , 
				@RequestParam("userSuperapp") String superapp , 
				@RequestParam("userEmail") String email, 
				@RequestParam(name = "size", required = false, defaultValue = "10") int size,
				@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
				String id = email+" "+superapp;	
				ObjectBoundary[] demoOp = this.servicesObject
					.searchObjectsByExactAlias(id, alias, size, page).toArray(new ObjectBoundary[0]);
				return demoOp;
		}	
	@GetMapping(
			path = { "/search/byAliasPattern/{pattern}"}, 
			produces = MediaType.APPLICATION_JSON_VALUE)
		public ObjectBoundary[] searchObjectsByAliasPattern ( 
				@PathVariable("pattern") String pattern , 
				@RequestParam("userSuperapp") String superapp , 
				@RequestParam("userEmail") String email, 
				@RequestParam(name = "size", required = false, defaultValue = "10") int size,
				@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
				String id = email+" "+superapp;
				ObjectBoundary[] demoOp = this.servicesObject
					.searchObjectsByAliasPattern(id, pattern, size, page).toArray(new ObjectBoundary[0]);
				return demoOp;
		}
	
	@GetMapping(
			path = { "/search/byLocation/{lat}/{lng}/{distance}"},
			produces = MediaType.APPLICATION_JSON_VALUE)
		public ObjectBoundary[] getObjectsInRadius (
			@PathVariable("lat") double lat , 
			@PathVariable("lng") double lng , 
			@PathVariable("distance") double distance ,
			@RequestParam(name = "distanceUnits", required = false, defaultValue = "km") String distanceUnits, 
			@RequestParam("userSuperapp") String superapp , 
			@RequestParam("userEmail") String email,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
			String id = email+" "+superapp;
			ObjectBoundary[] demoOp = this.servicesObject
				.searchByLocation(id, lat, lng, distance ,distanceUnits, size, page).toArray(new ObjectBoundary[0]);
			return demoOp;
		}	


	@PutMapping(
		path = {"/{superapp}/{id}"},
		consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateObject (
			@PathVariable("id") String id,
			@PathVariable("superapp") String superapp,
			@RequestBody ObjectBoundary update , 
			@RequestParam("userSuperapp") String userSuperapp , 
			@RequestParam("userEmail") String email) {
		
		String id_user = email+" "+ userSuperapp;
		this.servicesObject
			.updateObj(id,superapp,update ,id_user);
	}
	
}




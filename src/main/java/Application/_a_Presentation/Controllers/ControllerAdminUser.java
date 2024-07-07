package Application._a_Presentation.Controllers;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Application.business_logic.Boundaies.BoundaryCommand;
import Application.business_logic.Boundaies.BoundaryUser;
import Application.business_logic.DataService.ServicesCommand;
import Application.business_logic.DataService.ServicesObject;
import Application.business_logic.DataService.ServicesUser;

@RestController
@RequestMapping(path = { "/superapp/admin" })
public class ControllerAdminUser {
	private ServicesUser servicesUser;
	private ServicesObject servicesObject;
	private ServicesCommand ServicesCommand;
	public ControllerAdminUser(ServicesUser servicesUser, ServicesObject servicesObject,
			ServicesCommand servicesCommand) {
		this.servicesUser = servicesUser;
		this.servicesObject = servicesObject;
		ServicesCommand = servicesCommand;
	}
	@DeleteMapping
	(path  = {"/users"})
	public void deleteAllUsers( 
			@RequestParam("userSuperapp") String superapp,
			@RequestParam("userEmail") String email) {
		String id = email+" "+superapp;
		this.servicesUser.deleteAllUsers(id);
	}
	@DeleteMapping(path = {"/objects"})
	public void deleteAllObjects(
			@RequestParam("userSuperapp") String superapp,
			@RequestParam("userEmail") String email 
			) {
		String id = email+" "+superapp;
		this.servicesObject.deleteAllObjs(id);
	}
	@DeleteMapping(path = {"/miniapp"})
	public void deleteAllMiniAppsCommands(
			 @RequestParam("userSuperapp") String superapp
			,@RequestParam("userEmail") String email) {
		String id = email+" "+superapp;
		this.ServicesCommand.deleteAllminiAppCommandes(id);
	}
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
				path = {"/users"})
	public BoundaryUser[] getAllUsers (
			@RequestParam("userSuperapp") String superapp
			,@RequestParam("userEmail") String email,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		String id = email+" "+superapp;
		return this.servicesUser
			.getAllUsers(id, page, size)
			.toArray(new BoundaryUser[0]);
	}
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
				path = {"/miniapp"})
	public BoundaryCommand[] getAllMiniAppsCommands
			(@RequestParam("userSuperapp") String superapp
			,@RequestParam("userEmail") String email,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		String id = email+" "+superapp;
		return this.ServicesCommand
				.getAllMiniAppsCommands(id, page, size)
				.toArray(new BoundaryCommand[0]);
	}
	@GetMapping(
			path = { "/miniapp/{miniAppName}" }, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryCommand[] getSpecificCommandsFromMiniApp
	(@PathVariable("miniAppName") String idMiniAppName,
			@RequestParam("userSuperapp") String superapp
			,@RequestParam("userEmail") String email
			,@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		String idUser = email+" "+superapp;
		return this.ServicesCommand
				.getAllCommandsOfSpecificMiniApp(idMiniAppName,idUser, page, size)
				.toArray(new BoundaryCommand[0]);
	}
	
	
	
	
	
	
	
}

package Application._a_Presentation;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Application.business_logic.BoundaryCommand;
import Application.business_logic.BoundaryUser;
import Application.business_logic.ServicesCommand;
import Application.business_logic.ServicesObject;
import Application.business_logic.ServicesUser;

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
	(path  = {"/users?userSuperapp={userSuperapp}&userEmail={email}"})
	public void deleteAllUsers(@PathVariable("email") String email , @PathVariable("userSuperapp") String superapp) {
		String id = email+"_"+superapp;
		this.servicesUser.deleteAllUsers(id);
	}
	@DeleteMapping(path = {"/objects?userSuperapp={userSuperapp}&userEmail={email}"})
	public void deleteAllObjects(@PathVariable("email") String email , @PathVariable("userSuperapp") String superapp) {
		String id = email+"_"+superapp;
		this.servicesObject.deleteAllObjs(id);
	}
	@DeleteMapping(path = {"/miniapp?userSuperapp={userSuperapp}&userEmail={email}"})
	public void deleteAllMiniAppsCommands(@PathVariable("email") String email , @PathVariable("userSuperapp") String superapp) {
		String id = email+"_"+superapp;
		this.ServicesCommand.deleteAllminiAppCommandes(id);
	}
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
				path = {"/users?userSuperapp={userSuperapp}&userEmail={email}&size={size}&page={page}"})
	public BoundaryUser[] getAllUsers 
	(@PathVariable("email") String email , @PathVariable("userSuperapp") String superapp,@PathVariable("size") int size,@PathVariable("page")int page) {
		//add  		String id = email+"_"+superapp;
		String id = email+"_"+superapp;
		return this.servicesUser
			.getAllUsers(id)
			.toArray(new BoundaryUser[0]);
	}
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
				path = {"/miniapp?userSuperapp={userSuperapp}&userEmail={email}&size={size}&page={page}"})
	public BoundaryCommand[] getAllMiniAppsCommands
	(@PathVariable("email") String email , @PathVariable("userSuperapp") String superapp,@PathVariable("size") int size,@PathVariable("page")int page) {
		//add  		String id = email+"_"+superapp;
		String id = email+"_"+superapp;
		return this.ServicesCommand
				.getAllMiniAppCommands(id)
				.toArray(new BoundaryCommand[0]);
	}
	@GetMapping(
			path = { "/miniapp/{miniAppName}?userSuperapp={userSuperapp}&userEmail={email}&size={size}&page={page}" }, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryCommand[] getSpecificCommandsFromMiniApp
	(@PathVariable("miniAppName") String idMiniAppName, @PathVariable("email") String email , @PathVariable("userSuperapp") String superapp,@PathVariable("size") int size,@PathVariable("page")int page) {
		//add  		String id = email+"_"+superapp;
		String idUser = email+"_"+superapp;
		return this.ServicesCommand
				.get_All_Mini_App_Commands(idMiniAppName,idUser)
				.toArray(new BoundaryCommand[0]);
	}
	
}

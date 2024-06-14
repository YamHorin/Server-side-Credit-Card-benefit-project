package Application._a_Presentation;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Application.business_logic.BoundaryCommand;

import Application.business_logic.ServicesCommand;

@RestController
@RequestMapping(path = { "/superapp/miniapp/{MiniAppName}" })
public class ControllerCommand {
	private ServicesCommand ServicesCommand;
	
	public ControllerCommand(ServicesCommand ServicesCommand) {
		this.ServicesCommand = ServicesCommand;
	}

	@PostMapping(
		consumes = MediaType.APPLICATION_JSON_VALUE, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryCommand store_command(@RequestBody BoundaryCommand message) {
		try {
			return this.ServicesCommand.createMiniAppCommand(message);
		}
		catch (BoundaryIsNotFilledCorrectException e) {
		}
		return null;
		
	}


	
	
	@GetMapping(
		path = { "/Credit_Card_Benefit_app/admin/miniapp/{miniAppName}" }, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryCommand[] getSpecificCommandsFromMiniApp(@PathVariable("miniAppName") String id) {
		
		return this.ServicesCommand.get_All_Mini_App_Commands(id)
				.toArray(new BoundaryCommand[0]);
	}

	
	
	@GetMapping(
		path = { "/Credit_Card_Benefit_app/admin/miniapp/{miniAppName}{id}" }, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryCommand getSpecificCommand(@PathVariable("id") String id) {
		Optional<BoundaryCommand> demoOp = this.ServicesCommand
			.getSpecificMiniAppCommand(id);
		
		if (demoOp.isPresent()) {
			return demoOp.get();
		}else {
			throw new BoundaryIsNotFoundException("could not find Command by id: \n"+ id);
		}
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryCommand[] getAllMessages() {
		return this.ServicesCommand
			.getAllMiniAppCommands()
			.toArray(new BoundaryCommand[0]);
	}
	
	@DeleteMapping
	public void deleteAll() {
		this.ServicesCommand.deleteAllminiAppCommandes();
	}
	

}





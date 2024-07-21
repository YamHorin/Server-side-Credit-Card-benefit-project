package Application._a_Presentation.Controllers;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import Application.business_logic.Boundaies.ObjectBoundary;
import Application.business_logic.Boundaies.MiniAppCommandBoundary;
import Application.business_logic.DataService.ServicesCommand;

@RestController
@RequestMapping(path = { "/superapp/miniapp" })
public class ControllerCommand {
	private ServicesCommand ServicesCommand;
	
	public ControllerCommand(ServicesCommand ServicesCommand) {
		this.ServicesCommand = ServicesCommand;
	}

	@PostMapping (path = { "/{MiniAppName}" },
		consumes = MediaType.APPLICATION_JSON_VALUE, 
		produces = MediaType.APPLICATION_JSON_VALUE)

	public ObjectBoundary[] store_command(@PathVariable("MiniAppName") String idMiniAppName ,@RequestBody MiniAppCommandBoundary message) {

			return this.ServicesCommand.createMiniAppCommand(message , idMiniAppName);		
	}
}
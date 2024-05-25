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
import Services.ServicesCommand;
import Boundary.BoundaryCommand;
@RestController
@RequestMapping(path = { "/demo" })
public class ControllerCommand {
	private ServicesCommand servicesCommand;
	
	public ControllerCommand(ServicesCommand servicesCommand) {
		this.servicesCommand = servicesCommand;
	}

	@PostMapping(
		consumes = MediaType.APPLICATION_JSON_VALUE, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryCommand store(@RequestBody BoundaryCommand message) {
		return this.servicesCommand.createDemo(message);
	}

	@GetMapping(
		path = { "/{id}" }, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryCommand getSpecificMessage(@PathVariable("id") String id) {
		Optional<BoundaryCommand> demoOp = this.servicesCommand
			.getSpecificDemo(id);
		
		if (demoOp.isPresent()) {
			return demoOp.get();
		}else {
			throw new RuntimeException("could not find message by id: " + id);
		}
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public BoundaryCommand[] getAllMessages() {
		return this.servicesCommand
			.getAllDemoes()
			.toArray(new BoundaryCommand[0]);
	}
	
	@DeleteMapping
	public void deleteAll() {
		this.servicesCommand.deleteAllDemoes();
	}
	
	@PutMapping(
		path = {"/{id}"},
		consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void update (
			@PathVariable("id") String id, 
			@RequestBody BoundaryCommand update) {
		this.servicesCommand
			.updateDemo(id, update);
	}
}





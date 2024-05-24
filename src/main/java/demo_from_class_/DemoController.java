package demo_from_class_;

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

@RestController
@RequestMapping(path = { "/demo" })
public class DemoController {
	private DemoService demoService;
	
	public DemoController(DemoService demoService) {
		this.demoService = demoService;
	}

	@PostMapping(
		consumes = MediaType.APPLICATION_JSON_VALUE, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public DemoBoundary store(@RequestBody DemoBoundary message) {
		return this.demoService
			.createDemo(message);
	}

	@GetMapping(
		path = { "/{id}" }, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public DemoBoundary getSpecificMessage(@PathVariable("id") String id) {
		Optional<DemoBoundary> demoOp = this.demoService
			.getSpecificDemo(id);
		
		if (demoOp.isPresent()) {
			return demoOp.get();
		}else {
			throw new RuntimeException("could not find message by id: " + id);
		}
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public DemoBoundary[] getAllMessages() {
		return this.demoService
			.getAllDemoes()
			.toArray(new DemoBoundary[0]);
	}
	
	@DeleteMapping
	public void deleteAll() {
		this.demoService.deleteAllDemoes();
	}
	
	@PutMapping(
		path = {"/{id}"},
		consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void update (
			@PathVariable("id") String id, 
			@RequestBody DemoBoundary update) {
		this.demoService
			.updateDemo(id, update);
	}
}





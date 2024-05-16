package demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
}





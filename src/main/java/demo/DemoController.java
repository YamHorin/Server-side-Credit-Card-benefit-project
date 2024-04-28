package demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = { "/demo" })
public class DemoController {

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DemoBoundary echo(@RequestBody DemoBoundary message) {
		System.err.println(message);
		message.setMessageTimestamp(new Date());
		return message;
	}

	@GetMapping(path = { "/{id}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public DemoBoundary getRandomMessage(@PathVariable("id") String id) {
		DemoBoundary demoBoundary = new DemoBoundary();
		demoBoundary.setId(id);
		demoBoundary.setMessage("random value: " + UUID.randomUUID().toString());
		demoBoundary.setMessageTimestamp(new Date());
		System.err.println(demoBoundary); // can be sysout
		return demoBoundary;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public DemoBoundary[] getManyMessages() {
		List<DemoBoundary> messages = new ArrayList<>();
		DemoBoundary demoBoundary ;
		
		for (int i = 1; i <= 5; i++) {
			demoBoundary = new DemoBoundary();
			demoBoundary.setId("" + i);
			demoBoundary.setMessage("random value: " + UUID.randomUUID().toString());
			demoBoundary.setMessageTimestamp(new Date());
			messages.add(demoBoundary);
			System.err.println(demoBoundary); 
		}
		return messages
			.toArray(new DemoBoundary[0]);
	}
}

package Application;

import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("manualTests")
public class Initializer implements CommandLineRunner{

	@Override
	public void run(String... args) throws Exception {
		

	}
	
}

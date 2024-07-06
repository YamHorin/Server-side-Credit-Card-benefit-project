package Application.logic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

import Application.business_logic.javaObjects.UserId;

public class restClientMain {

	@Value("${server.port:8085}")
	public static RestClient setPort(int port) {
		return  RestClient.create("http://localhost:" + port + "/superapp");
	}
	
	public static void main(String[] args)
	 {
		 RestClient restClient = null;
		 
		 
		 int port = 8085;
		 restClient = setPort(port);
		adminFunctions adminFunctions = new adminFunctions();
			UserId useridADM = new UserId("2024B.gal.angel", "YahavLer@gmail.com");
			
			adminFunctions.deleteClub(restClient, useridADM, 2);
	 }

}

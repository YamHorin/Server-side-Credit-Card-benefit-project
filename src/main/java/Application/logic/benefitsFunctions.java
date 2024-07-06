package Application.logic;

import java.util.Collections;
import java.util.Scanner;

import org.springframework.web.client.RestClient;

import Application.business_logic.Boundaies.BoundaryObject;
import Application.business_logic.Boundaies.BoundaryUser;
import Application.business_logic.javaObjects.CreatedBy;
import Application.business_logic.javaObjects.UserId;

public class benefitsFunctions {
	public void addBenefit (RestClient restClient ,UserId userId)
	{	
		Scanner scn =new Scanner(System.in);
		System.out.println("we are making a new benefit:");
		System.out.println("benefit name?");
		String name = scn.nextLine();
		System.out.println("benefit description?");
		String description = scn.nextLine();
		BoundaryObject benefit = new BoundaryObject();
		
		benefit.setType("benefit");
		benefit.setAlias(name);
		benefit.setActive(true);
		benefit.setObjectDetails(Collections.singletonMap("description", description));
		benefit.setCreatedBy(new CreatedBy(userId));
		
		benefit  = restClient.post()
				.uri("/object")
				.body(benefit)
				.retrieve()
				.body(BoundaryObject.class);
		
		scn.close();
		System.out.println("we have a ne benefit yeah babyn\n\n"+benefit.toString());
		
	}
}

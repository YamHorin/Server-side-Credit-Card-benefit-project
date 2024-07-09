package Application.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.springframework.web.client.RestClient;

import Application.business_logic.Boundaies.ObjectBoundary;
import Application.business_logic.Boundaies.UserBoundary;
import Application.business_logic.javaObjects.CreatedBy;
import Application.business_logic.javaObjects.Location;
import Application.business_logic.javaObjects.UserId;

public class benefitsFunctions {
	public int addBenefit (RestClient restClient ,UserId userId)
	{	
		Scanner scn =new Scanner(System.in);
		System.out.println("we are making a new benefit:");
		System.out.println("benefit name?");
		String name = scn.nextLine();
		System.out.println("benefit description?");
		String description = scn.nextLine();
		ObjectBoundary benefit = new ObjectBoundary();
		
		benefit.setType("benefit");
		benefit.setAlias(name);
		benefit.setActive(true);
		benefit.setObjectDetails(Collections.singletonMap("description", description));
		benefit.setCreatedBy(new CreatedBy(userId));
		
		benefit.setLocation(new Location(999,999));
		benefit  = restClient.post()
				.uri("/objects")
				.body(benefit)
				.retrieve()
				.body(ObjectBoundary.class);
		
		scn.close();
		System.out.println("we have a ne benefit yeah babyn\n\n"+benefit.toString());
		return getNumberFromId(benefit.getObjectID().getId());
		
	}
	
	public int getNumberFromId(String str)
	{
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i)))
				//option for a bug?
				return Integer.parseInt(String.valueOf(str.charAt(i)));
		}
		return 99;
	}
}

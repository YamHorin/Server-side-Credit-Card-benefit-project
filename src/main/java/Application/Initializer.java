package Application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import Application.business_logic.Boundaies.BoundaryCommand;
import Application.business_logic.Boundaies.BoundaryObject;
import Application.business_logic.Boundaies.BoundaryUser;
import Application.business_logic.Boundaies.RoleEnumBoundary;
import Application.business_logic.DataService.ServicesCommand;
import Application.business_logic.DataService.ServicesObject;
import Application.business_logic.DataService.ServicesUser;
import Application.business_logic.javaObjects.CommandId;
import Application.business_logic.javaObjects.CreatedBy;
import Application.business_logic.javaObjects.Location;
import Application.business_logic.javaObjects.ObjectId;
import Application.business_logic.javaObjects.TargetObject;
import Application.business_logic.javaObjects.UserId;

@Component
@Profile("manualTests")
public class Initializer implements CommandLineRunner{
    @Value("${spring.application.name}")
	private String superAppName;
	private ServicesUser ServicesUser;
	private ServicesObject ServicesObject;
	private ServicesCommand ServicesCommand;
	
	public Initializer(ServicesUser servicesUser,
			ServicesObject servicesObject,
			ServicesCommand servicesCommand) {
		ServicesUser = servicesUser;
		ServicesObject = servicesObject;
		ServicesCommand = servicesCommand;
	}



	@Override
	public void run(String... args) throws Exception {
		//make 12 users
        List<String> names = new ArrayList<>();
        // Add 10 full names to the list
        String manUrl = "https://i.imgur.com/2KI92JQ.jpeg";
        String womanUrl ="https://i.imgur.com/iqmrZWm.jpeg";
        names.add("JohnDoe");
        names.add("JaneSmith");
        names.add("MichaelJohnson");
        names.add("EmilyDavis");
        names.add("ChrisBrown");
        names.add("JessicaWilson");
        names.add("DavidMartinez");
        names.add("SarahTaylor");
        names.add("DanielAnderson");
        names.add("LauraThomas");
        names.add("YahavLer");
		IntStream.range(0, 11).mapToObj(
		i ->{
			BoundaryUser user =   new BoundaryUser();
			String new_name = addSpace(names.get(i));
			user.setUserName(new_name);
			if (i%2==0 || user.getUserName().equalsIgnoreCase("Sarah Taylor")) {
				user.setRole(RoleEnumBoundary.SUPERAPP_USER);
				user.setAvatar(manUrl);
			}
			
			else {
				user.setAvatar(womanUrl);
				user.setRole(RoleEnumBoundary.MINIAPP_USER);
			}
			if (i==10)
				user.setRole(RoleEnumBoundary.ADM_USER);
			UserId UserId = new UserId();
			UserId.setEmail(names.get(i)+"@gmail.com");
			user.setUserId(UserId);
			return this.ServicesUser.createUser(user);
		}).forEach(user->System.err.println(user.toString()));

		boolean isATest = false;
		
		if (isATest)
			testRunSprint3(names);
		else
			makeObjectsSprint4();
		

	}
	public String addSpace(String name)
	{
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (i > 0 && Character.isUpperCase(c)) {
                result.append(' ');
            }
            result.append(c);
        }

        return result.toString();
	}
	
	public void makeObjectsSprint4()
	{
		String type = "club";
		UserId UserId = new UserId();
		UserId.setEmail("SarahTaylor@gmail.com");
		UserId.setSuperAPP(this.superAppName);
        List<String> creditCardClubs = new ArrayList<>();

        creditCardClubs.add("Hever");
        creditCardClubs.add("MasterCardPlus");
        creditCardClubs.add("American Express");
        creditCardClubs.add("Discover");
        creditCardClubs.add("Diners Club");
        creditCardClubs.add("asmoret");
        creditCardClubs.add("RamiLevi");
        
        
        //making 6 clubs 
		for (int j = 0; j < 6; j++) {
			BoundaryObject obj = new BoundaryObject();
			obj.setType(type);
			obj.setLocation(new Location(999,999));
			obj.setAlias(creditCardClubs.get(j));
			Map<String, Object> obj_items = new HashMap<>();
			obj_items.put("idClub",j);
			int benefitsId []  = {j , j+1};
			obj_items.put("listOfBenefitOfClub", benefitsId );
			obj.setActive(true);
			obj.setCreatedBy(new CreatedBy(UserId));
			obj.setObjectDetails(obj_items);
			obj= this.ServicesObject.createObject(obj);
			System.err.println(obj.toString());
		}
        //making 5 stores
		String[] stores = {"Walmart", "Target", "Best Buy", "Macy's", "Home Depot" ,"Castro" ,"Golda"};
        type  = "store";
		for (int j = 0; j < 7; j++) {
			BoundaryObject obj = new BoundaryObject();
			
			obj.setType(type);
			obj.setLocation(new Location(30.25+j,60.5555-j));
			obj.setAlias(stores[j]);
			Map<String, Object> obj_items = new HashMap<>();
			obj_items.put("idStore", j);
			int benefitsId []  = {j , j+1 , j+2};
			if (stores[j].equalsIgnoreCase("Golda"))
				 benefitsId   = null;
			obj_items.put("listOfBenefitOfClub", benefitsId );
			int listOfClubOfStore []  ={j , j+1};
			obj_items.put("listOfClubOfStore", listOfClubOfStore);
			obj.setActive(true);
			obj.setCreatedBy(new CreatedBy(UserId));
			obj.setObjectDetails(obj_items);
			obj= this.ServicesObject.createObject(obj);
			System.err.println(obj.toString());
		}
		HashMap<String, String> salesMap = new HashMap<>();

	    // Add 10 benefits
	    salesMap.put("Summer Clearance", "Up to 70% off on clothing and accessories.");
	    salesMap.put("Back to School Savings", "Discounts on laptops, backpacks, and school supplies.");
	    salesMap.put("Labor Day Weekend Sale", "Deals on appliances, furniture, and mattresses.");
	    salesMap.put("Black Friday Specials", "Reduced prices on electronics, toys, and home goods.");
	    salesMap.put("Holiday Gift Guide", "Curated selection of gifts with special offers.");
	    salesMap.put("Flash Sale", "Limited-time discounts on select items.");
	    salesMap.put("Buy One Get One (BOGO) Deals", "Purchase one item and get another of equal or lesser value for free.");
	    salesMap.put("Seasonal Promotions", "Special offers on products relevant to the current season.");
	    salesMap.put("Clearance Rack", "Deep discounts on leftover or discontinued items.");
	    salesMap.put("Employee Appreciation Sale", "Discounts offered exclusively to store employees.");
        type  = "benefit";
        int j=0;
	    for (String benefit : salesMap.keySet()) {
		   BoundaryObject obj = new BoundaryObject();
			obj.setType(type);
			obj.setLocation(new Location(999,999));
			obj.setAlias(benefit);
			Map<String, Object> obj_items = new HashMap<>();
			obj_items.put("idBenefit", j);
			obj_items.put("description", salesMap.get(benefit));
			obj.setActive(true);
			obj.setCreatedBy(new CreatedBy(UserId));
			obj.setObjectDetails(obj_items);
			obj= this.ServicesObject.createObject(obj);
			System.err.println(obj.toString());
			j++;
	}
		

	}
	public void testRunSprint3(List<String> names)
	{
		//make 20 objects
		String type = "type";
		String alias = "alias";
		ArrayList<String > ids_of_objects = new ArrayList<>();
		CreatedBy CreatedBy  = new CreatedBy();
		UserId UserId = new UserId();
		BoundaryObject BoundaryObject = null;
		for (int j =0 ;j<=20 ;j++)
		{
			System.err.println(j);
			System.err.println("here");
			BoundaryObject obj = new BoundaryObject();
			obj.setActive(j%2==0);
			obj.setLocation(new Location(0.2+j , 0.2+j));
			obj.setType(type+" "+j);
			obj.setAlias(alias+" "+j);
			UserId.setEmail(names.get(2)+"@aa.com");
			CreatedBy.setUserId(UserId);
			obj.setCreatedBy(CreatedBy);
			obj.setObjectDetails(Collections.singletonMap("person", "Jane #" + j));
			BoundaryObject = this.ServicesObject.createObject(obj);
			ids_of_objects.add( BoundaryObject.getObjectID().getId());	
			
		}
		//make 10 mini app commands
		UserId.setEmail(names.get(2)+"@aa.com");
		UserId.setSuperAPP(BoundaryObject.getObjectID().getSuperApp());
		CreatedBy.setUserId(UserId);
		String id = "command";
		String command = "make visa card";
		for (int i =0; i<10 ; i++) {
			BoundaryCommand Command = new BoundaryCommand();
			Command.setCommand(command+" number "+i);
			Command.setCommandAttributes(Collections.singletonMap("person", "Jane #" + i));
			CommandId CommandId =new CommandId();
			CommandId.setId(id+i);
			Command.setCommandId(CommandId);
			Command.setInvokedBy(CreatedBy);
			Command.setTargetObject(new TargetObject(new ObjectId(ids_of_objects.get(i))));
			this.ServicesCommand.createMiniAppCommand(Command);
		}
	}


	
	
}

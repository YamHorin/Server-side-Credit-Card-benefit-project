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
        names.add("John_Doe");
        names.add("Jane_Smith");
        names.add("Michael_Johnson");
        names.add("Emily_Davis");
        names.add("Chris_Brown");
        names.add("Jessica_Wilson");
        names.add("David_Martinez");
        names.add("Sarah_Taylor");
        names.add("Daniel_Anderson");
        names.add("Laura_Thomas");
        names.add("Yahav_Lev");
		IntStream.range(0, 11).mapToObj(
		i ->{
			BoundaryUser user =   new BoundaryUser();
			user.setUserName(names.get(i));
			user.setAvatar("avatar "+i);
			if (i%2==0)
				user.setRole(RoleEnumBoundary.SUPERAPP_USER);
			else
				user.setRole(RoleEnumBoundary.MINIAPP_USER);
			if (i==10)
				user.setRole(RoleEnumBoundary.ADM_USER);
			UserId UserId = new UserId();
			UserId.setEmail(names.get(i)+"@aa.com");
			user.setUserId(UserId);
			return this.ServicesUser.createUser(user);
		}).forEach(user->System.err.println(user.toString()));
		
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

package Application;

import java.util.Collections;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import Application.DataAccess.Location;
import Application.business_logic.BoundaryCommand;
import Application.business_logic.BoundaryObject;
import Application.business_logic.BoundaryUser;
import Application.business_logic.CommandId;
import Application.business_logic.CreatedBy;
import Application.business_logic.RoleEnumBoundary;
import Application.business_logic.ServicesCommand;
import Application.business_logic.ServicesObject;
import Application.business_logic.ServicesUser;
import Application.business_logic.UserId;

@Component
@Profile("manualTests")
public class Initializer implements CommandLineRunner{
	private ServicesUser ServicesUser;
	private ServicesObject ServicesObject;
	private ServicesCommand ServicesCommand;
	private String name_super_app;
	
	public Initializer(ServicesUser servicesUser,
			ServicesObject servicesObject,
			ServicesCommand servicesCommand) {
		ServicesUser = servicesUser;
		ServicesObject = servicesObject;
		ServicesCommand = servicesCommand;
	}



	@Override
	public void run(String... args) throws Exception {
		//make 11 users
		String username = "gal";
		IntStream.range(0, 11).mapToObj(
		i ->{
			BoundaryUser user =   new BoundaryUser();
			user.setUserName(username+i);
			user.setAvatar("avatar "+i);
			if (i%2==0)
				user.setRole(RoleEnumBoundary.SUPERAPP_USER);
			else
				user.setRole(RoleEnumBoundary.MINIAPP_USER);
			if (i==10)
				user.setRole(RoleEnumBoundary.ADM_USER);
			UserId UserId = new UserId();
			UserId.setEmail(username+i+"@aa.com");
			user.setUserId(UserId);
			return this.ServicesUser.createUser(user);
		}).forEach(user->System.err.println(user.toString()));
		
		//make 20 objects
		String type = "type";
		String alias = "alias";
		String createdBy = "yam@yam.com";
		for (int j =0 ;j<=20 ;j++)
		{
			System.err.println(j);
			System.err.println("here");
			BoundaryObject obj = new BoundaryObject();
			obj.setActive(j%2==0);
			obj.setLocation(new Location(0.2+j , 0.2+j));
			obj.setType(type+" "+j);
			obj.setAlias(alias+" "+j);
			CreatedBy CreatedBy  = new CreatedBy();
			UserId UserId = new UserId();
			UserId.setEmail(createdBy+j);
			UserId.setSuperAPP(this.name_super_app);
			CreatedBy.setUserId(UserId);
			obj.setCreatedBy(CreatedBy);
			obj.setObjectDetails(Collections.singletonMap("person", "Jane #" + j));
			this.ServicesObject.createObject(obj);
				
			
		}
		//make 10 mini app commands
		
		String id = "command";
		String command = "command number";
		//TODO change the String in CommandEntity of invokedBy and targetObject to string 
		//so i will get the id of the user and object 
		//and after come back to change here 
		String targetObject  = "123";
		String invokedBy = username;
		for (int i =0; i<10 ; i++) {
			BoundaryCommand Command = new BoundaryCommand();
			Command.setCommand(command+" number "+i);
			Command.setCommandAttributes(Collections.singletonMap("person", "Jane #" + i));
			CommandId CommandId =new CommandId();
			CommandId.setId(id+i);
			Command.setCommandId(CommandId);
			this.ServicesCommand.createMiniAppCommand(Command);
		}
		
		

	}
	@Value("${spring.application.name:SuperApppp}")
	public void setname_super_app(String name_super_app) {
		System.err.println("**** reading from configuration default super app name: " + name_super_app);
		this.name_super_app = name_super_app;
	}

	
	
}

package Application.logic.FindAllBenefits;


import java.util.List;

import org.springframework.stereotype.Component;

import Application.business_logic.Boundaies.BoundaryCommand;
import Application.business_logic.Boundaies.BoundaryObject;
import Application.business_logic.DataService.ServicesObject;
import Application.logic.MiniappInterface;

@Component("printAllClubs")
public class printAllClubs implements MiniappInterface{
	private ServicesObject ServicesObject;

	
	


	public printAllClubs(Application.business_logic.DataService.ServicesObject servicesObject) {
		ServicesObject = servicesObject;
	}





	//i assume that it's all clubs for the user in the mini app commands
	//i assume that the user is saying how much benefits he wnats to see  in the mini app commands
	@Override
	public List<BoundaryObject> activateCommand(BoundaryCommand miniappCommandBoundary) {

		String id = miniappCommandBoundary.getInvokedBy().getUserId().getEmail()+" "+
				miniappCommandBoundary.getInvokedBy().getUserId().getSuperAPP();
		String command = miniappCommandBoundary.getCommand();
		System.err.println("the client wants: "+command);
		int numberBenefits = 10;
		//TODO how much clubs to shoe the user????? set defult to 10?
		List<BoundaryObject> list_clubs = this.ServicesObject.searchByType(id, 
				"club", 
				numberBenefits, 
				0);
		return list_clubs;
		
	}





}

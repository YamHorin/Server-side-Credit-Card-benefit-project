package Application.logic;

import java.util.List;

import Application.business_logic.Boundaies.BoundaryCommand;
import Application.business_logic.Boundaies.BoundaryObject;

public interface MiniappInterface {

	public List<BoundaryObject> activateCommand(BoundaryCommand miniappCommandBoundary);
}
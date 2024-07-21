package Application.logic;

import java.util.List;

import Application.business_logic.Boundaies.MiniAppCommandBoundary;
import Application.business_logic.Boundaies.ObjectBoundary;

public interface MiniappInterface {
	public List<ObjectBoundary> activateCommand(MiniAppCommandBoundary miniappCommandBoundary);
}

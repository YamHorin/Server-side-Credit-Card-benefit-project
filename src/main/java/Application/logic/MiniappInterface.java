package Application.logic;

import Application.business_logic.Boundaies.MiniAppCommandBoundary;

public interface MiniappInterface {

	public Object activateCommand(MiniAppCommandBoundary miniappCommandBoundary);
}
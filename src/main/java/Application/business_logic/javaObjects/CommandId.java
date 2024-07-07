package Application.business_logic.javaObjects;

public class CommandId {
	private String superapp;
	private String miniapp;
	private String id;
	
	public CommandId () {}
	
	

	public String getSuperApp() {
		return superapp;
	}

	public void setSuperApp(String superApp) {
		this.superapp = superApp;
	}

	public String getMiniApp() {
		return miniapp;
	}

	public void setMiniApp(String miniApp) {
		this.miniapp = miniApp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "CommandId:/n "
				+ "{superApp=" + superapp + "/n"
						+ ", miniApp=" + miniapp 
						+ "/n, id=" + id + "}";
	}

}

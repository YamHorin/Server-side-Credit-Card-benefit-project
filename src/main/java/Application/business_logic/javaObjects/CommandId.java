package Application.business_logic.javaObjects;

public class CommandId {
	private String superApp;
	private String miniApp;
	private String id;
	
	public CommandId () {}
	
	

	public String getSuperApp() {
		return superApp;
	}

	public void setSuperApp(String superApp) {
		this.superApp = superApp;
	}

	public String getMiniApp() {
		return miniApp;
	}

	public void setMiniApp(String miniApp) {
		this.miniApp = miniApp;
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
				+ "{superApp=" + superApp + "/n"
						+ ", miniApp=" + miniApp 
						+ "/n, id=" + id + "}";
	}

}

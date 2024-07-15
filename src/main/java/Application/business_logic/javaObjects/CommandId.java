package Application.business_logic.javaObjects;

public class CommandId {
	private String superapp;
	private String miniapp;
	private String id;
	
	public CommandId () {}
	
	

	public String getSuperapp() {
		return superapp;
	}

	public void setSuperapp(String superApp) {
		this.superapp = superApp;
	}

	public String getMiniapp() {
		return miniapp;
	}

	public void setMiniapp(String miniApp) {
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
				+ "{superapp=" + superapp + "/n"
						+ ", miniapp=" + miniapp 
						+ "/n, id=" + id + "}";
	}

}

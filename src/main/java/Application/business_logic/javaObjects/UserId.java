package Application.business_logic.javaObjects;

public class UserId {
	private String superapp;
	private String email;
	
	public UserId() {
		
	}
	public UserId(String SuperAPP , String email) {
		this.superapp= SuperAPP;
		this.email = email;
	}
	
	public String getSuperAPP() {
		return superapp;
	}
	public void setSuperAPP(String superAPP) {
		this.superapp = superAPP;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "UserId [SuperAPP=" + superapp + ", email=" + email + "]";
	}
	

}

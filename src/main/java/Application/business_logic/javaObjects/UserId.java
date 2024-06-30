package Application.business_logic.javaObjects;

public class UserId {
	private String SuperAPP;
	private String email;
	
	public UserId() {
		
	}
	public UserId(String SuperAPP , String email) {
		this.email = email;
		this.SuperAPP= SuperAPP;
	}
	
	public String getSuperAPP() {
		return SuperAPP;
	}
	public void setSuperAPP(String superAPP) {
		this.SuperAPP = superAPP;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "UserId [SuperAPP=" + SuperAPP + ", email=" + email + "]";
	}
	

}

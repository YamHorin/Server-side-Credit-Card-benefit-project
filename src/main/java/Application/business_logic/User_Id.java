package Application.business_logic;

public class User_Id {
	private String SuperAPP;
	private String email;
	
	public User_Id() {
		
	}
	public User_Id(String SuperAPP , String email) {
		this.email = email;
		this.SuperAPP= SuperAPP;
	}
	
	public String getSuperAPP() {
		return SuperAPP;
	}
	public void setSuperAPP(String superAPP) {
		SuperAPP = superAPP;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

}

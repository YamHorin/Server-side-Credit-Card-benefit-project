package Application.business_logic;

public class CreatedBy {
	private UserId userId;
	
	public CreatedBy() {}

	public CreatedBy(String email , String superApp) {
		this.userId = new UserId(email ,superApp);
	}

	public UserId getUserId() {
		return userId;
	}

	public void setUserId(UserId userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "CreatedBy{" +
				"userId=" + userId +
				'}';
	}

}

package Application.business_logic.javaObjects;

public class CreatedBy {
	private UserId userId;
	
	public CreatedBy() {
	}

	public CreatedBy(UserId userId2) {
		this.userId = userId2;
	}

	public CreatedBy(String email , String superApp) {
		this.userId = new UserId(superApp,email);
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
				"userId=" + userId.toString() +
				'}';
	}

}

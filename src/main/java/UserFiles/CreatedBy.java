package UserFiles;

public class CreatedBy {
	private User_Id userId;
	
	public CreatedBy() {}

	public User_Id getUserId() {
		return userId;
	}

	public void setUserId(User_Id userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "CreatedBy{" +
				"userId=" + userId +
				'}';
	}

}

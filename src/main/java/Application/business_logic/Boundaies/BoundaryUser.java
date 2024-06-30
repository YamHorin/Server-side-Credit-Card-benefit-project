package Application.business_logic.Boundaies;

import Application.DataAccess.Entities.EntityUser;
import Application.DataAccess.Entities.RoleEnumEntity;
import Application.business_logic.javaObjects.UserId;

public class BoundaryUser {

	private UserId userId;
	private RoleEnumBoundary role;
	private String userName;
	private String avatar;

	public BoundaryUser() {
	}



	public UserId getUserId() {
		return userId;
	}

	public void setUserId(UserId userId) {
		this.userId = userId;
	}

	public RoleEnumBoundary getRole() {
		return role;
	}

	public void setRole(RoleEnumBoundary role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "UserBoundary{" + "userId=" + userId.toString()+"\n\n" + ", role=" + role + ", userName='" + userName + '\'' + ", avatar='"
				+ avatar + '\'' + '}';
	}


}

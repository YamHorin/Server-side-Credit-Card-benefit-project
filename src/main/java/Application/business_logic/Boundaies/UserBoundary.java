package Application.business_logic.Boundaies;

import Application.DataAccess.Entities.EntityUser;
import Application.DataAccess.Entities.RoleEnumEntity;
import Application.business_logic.javaObjects.UserId;

public class UserBoundary {

	private UserId userId;
	private RoleEnumBoundary role;
	private String username;
	private String avatar;

	public UserBoundary() {
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
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "UserBoundary{" + "userId=" + userId.toString()+"\n\n" + ", role=" + role + ", userName='" + username + '\'' + ", avatar='"
				+ avatar + '\'' + '}';
	}


}

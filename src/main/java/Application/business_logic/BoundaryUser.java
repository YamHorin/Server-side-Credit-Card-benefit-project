package Application.business_logic;

import Application.DataAccess.EntityUser;
import Application.DataAccess.RoleEnumEntity;

public class BoundaryUser {

	private UserId userId;
	private RoleEnumBoundary role;
	private String userName;
	private String avatar;

	public BoundaryUser() {
	}

	public BoundaryUser(EntityUser userEntity) {
		this.userId = new UserId();
		String[] splitId = userEntity.getId().split("_");
		this.getUserId().setEmail(splitId[0]);
		this.getUserId().setSuperAPP(splitId[1]);
		this.setUserName(userEntity.getUserName());
		RoleEnumBoundary role =RoleEnumBoundary.valueOf(userEntity.getRole().name().toUpperCase());
		this.setRole(role);
		this.setAvatar(userEntity.getAvatar());

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
		return "UserBoundary{" + "userId=" + userId + ", role=" + role + ", userName='" + userName + '\'' + ", avatar='"
				+ avatar + '\'' + '}';
	}


}

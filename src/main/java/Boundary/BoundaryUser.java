package Boundary;

import Entities.*;
import UserFiles.*;

/*
{
  "id":"abc",
  "message":"hello",
  "messageTimestamp":"2024-05-23T14:34:00+0200",
  "details":{"key":5, "otherKey":"hello"},
  "status":"OK|NOT_OK|UNDETERMINED",
  "version":0.1,
  "name":{
    "firstName":"Jane",
    "lastName":"Smith"
  }
}
 */
public class BoundaryUser {

	private User_Id userId;
	private RoleEnumBoundary role;
	private String userName;
	private String avatar;

	public BoundaryUser() {
	}

	public BoundaryUser(EntityUser userEntity) {
		this.userId = new User_Id();
		String[] splitId = userEntity.getId().split("_");
		this.getUserId().setEmail(splitId[0]);
		this.getUserId().setSuperAPP(splitId[1]);
		this.setUserName(userEntity.getUserName());
		this.setRole(userEntity.getRole());
		this.setAvatar(userEntity.getAvatar());

	}

	public User_Id getUserId() {
		return userId;
	}

	public void setUserId(User_Id userId) {
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

	public EntityUser toEntity() {
		EntityUser userEntity = new EntityUser();

		userEntity.setId(this.getUserId().getEmail() + "_" + this.getUserId().getSuperAPP());
		userEntity.setRole(this.getRole());
		userEntity.setUserName(this.getUserName());
		userEntity.setAvatar(this.getAvatar());
		return userEntity;

	}
}

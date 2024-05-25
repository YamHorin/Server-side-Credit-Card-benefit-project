package UserFiles;

import java.net.URL;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_TBL")
public class User_Entity {
	
	@Id
	private String UserName;
	private RoleEnumEntity role;
	private User_Id user_id;
	private URL avatar ;
	

	public RoleEnumEntity getRole() {
		return role;
	}
	public void setRole(RoleEnumEntity role) {
		this.role = role;
	}
	public User_Id getUser_id() {
		return user_id;
	}
	public void setUser_id(User_Id user_id) {
		this.user_id = user_id;
	}
	public URL getAvatar() {
		return avatar;
	}
	public void setAvatar(URL avatar) {
		this.avatar = avatar;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	
	

}

package UserFiles;
/*
 NewUserBoundary sample JSON:
{
"email":"jane@demo.org",
"role":"STUDENT",
"username":"Jane Smith",
"avatar":"J"
}
 */
import java.net.MalformedURLException; 
import java.net.URL; 
import java.util.Map;

public class User_Boundary {

	private String Name;
	private RoleEnumBoundary role;
	private User_Id user_id;
	private URL avatar ;
	
	
	public RoleEnumBoundary getRole() {
		return role;
	}

	public void setRole(RoleEnumBoundary role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User_Boundary [Name=" + Name + ", role=" + role + ", user_id=" + user_id + ", avatar=" + avatar + "]";
	}

	public User_Boundary() {
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

	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	

	
}

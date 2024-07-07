package Application.business_logic.Boundaies;

import Application.DataAccess.Entities.EntityUser;
import Application.business_logic.javaObjects.UserId;

public class NewUserBoundary {

    private String email;
    private RoleEnumBoundary role;
    private String username;
    private String avatar;

    public NewUserBoundary() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(RoleEnumBoundary role) {
        this.role = role;
    }

    public RoleEnumBoundary getRole() {
        return role;
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


    public UserBoundary newUserToUserBoundary() {
    	UserBoundary  Boundary = new UserBoundary ();    	
    	RoleEnumBoundary Role;
		Role = RoleEnumBoundary.valueOf(this.getRole().name().toUpperCase());
    	Boundary.setRole(Role);
        Boundary.setUserName(this.getUserName() == null ? "Anonymous" : this.getUserName());
        Boundary.setAvatar(this.getAvatar() == null ? "F" : this.getAvatar());
        Boundary.setUserId(new UserId("",this.getEmail()));
        
        return Boundary;

    }
    @Override
    public String toString() {
        return "NewUserBoundary{" +
                "email='" + email + '\'' +
                ", role=" + role +
                ", userName='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}


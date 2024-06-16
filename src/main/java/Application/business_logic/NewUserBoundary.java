package Application.business_logic;

import Application.DataAccess.EntityUser;

public class NewUserBoundary {

    private String email;
    private String role;
    private String userName;
    private String avatar;

    public NewUserBoundary() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
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


    public BoundaryUser newUserToUserBoundary() {
    	BoundaryUser  Boundary = new BoundaryUser ();    	
    	RoleEnumBoundary Role;
    	System.err.println("this.getRole() = "+this.getRole());
    	try {
		Role = RoleEnumBoundary.valueOf(this.getRole().toUpperCase());
    	}
    	catch (IllegalArgumentException e) {
    	    Role = RoleEnumBoundary.UNDETERMINED;
    	}
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
                ", userName='" + userName + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}


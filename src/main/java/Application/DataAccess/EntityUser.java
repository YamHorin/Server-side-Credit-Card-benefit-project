package Application.DataAccess;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_TBL")
public class EntityUser {

    @Id
    private String id;
    private String userName;
    private RoleEnumEntity role;
    private String avatar;


	public EntityUser() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public RoleEnumEntity getRole() {
        return role;
    }

    public void setRole(RoleEnumEntity role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", role=" + role +
                ", avatar='" + avatar + '\'' +
                 +
                '}';
    }

    

}

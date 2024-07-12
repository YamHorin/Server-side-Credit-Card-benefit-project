package Application.DataAccess.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_TBL")
public class EntityUser {

    @Id
    private String id;
    private String username;

    @Enumerated(EnumType.STRING)
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
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
                ", userName='" + username + '\'' +
                ", role=" + role +
                ", avatar='" + avatar + '\'' +
                 +
                '}';
    }

    

}

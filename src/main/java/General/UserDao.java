package General;

import org.springframework.data.jpa.repository.JpaRepository;

import Entities.EntityUser;

public interface UserDao extends JpaRepository<EntityUser, String>{

}

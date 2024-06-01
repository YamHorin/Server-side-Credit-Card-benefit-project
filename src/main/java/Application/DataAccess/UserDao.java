package Application.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<EntityUser, String>{

}

package Application.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ObjDao extends JpaRepository<EntityObject, String> {
}

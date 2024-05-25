package General;

import org.springframework.data.jpa.repository.JpaRepository;

import Entities.EntityObject;

public interface ObjDao extends JpaRepository<EntityObject, String> {
}

package General;

import org.springframework.data.jpa.repository.JpaRepository;

import Entities.EntityCommand;

public interface MiniAppCommadDao extends JpaRepository<EntityCommand, String> {

}

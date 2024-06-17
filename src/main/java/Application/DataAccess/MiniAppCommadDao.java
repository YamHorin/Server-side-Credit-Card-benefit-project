package Application.DataAccess;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface MiniAppCommadDao extends JpaRepository<EntityCommand, String> {

	List<EntityCommand> findAllByminiAppName(@Param("miniAppName") Pageable pageRequest);
}

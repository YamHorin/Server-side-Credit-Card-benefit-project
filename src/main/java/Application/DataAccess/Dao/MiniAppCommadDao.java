package Application.DataAccess.Dao;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import Application.DataAccess.Entities.EntityCommand;


public interface MiniAppCommadDao extends JpaRepository<EntityCommand, String> {

	List<EntityCommand> findAllByMiniAppName(@Param("miniAppName")String miniAppName, Pageable pageRequest);
}

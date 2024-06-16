package Application.DataAccess;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;



public interface ObjDao extends JpaRepository<EntityObject, String> {

	//find all only active SQL query: 
	//using pagination
	public List<EntityObject> findAllByActive(
		@Param("active") boolean active, 	
		Pageable pageable);

	public List<EntityObject> findAllByType(@Param("type") String type, Pageable pageable);

	public Page<EntityObject> findAll(Pageable pageable);
}

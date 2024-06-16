package Application.DataAccess;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;



public interface ObjDao extends JpaRepository<EntityObject, String> {

	//find all only active SQL query: 
	//using pagination
	
	//try to use more sql then java logic

	public List<EntityObject> findAllByActive(
		@Param("active") boolean active, 	
		Pageable pageable);

	public List<EntityObject> findAllByType(@Param("type") String type, Pageable pageable);
	
	public List<EntityObject> findAllByTypeAndActiveIsTrue(@Param("type") String type, Pageable pageable);

	public Page<EntityObject> findAll(Pageable pageable);
	
	public Optional<EntityObject> findByObjectIDAndActiveIsTrue(@Param("objectID") String objectID);
	
	
}

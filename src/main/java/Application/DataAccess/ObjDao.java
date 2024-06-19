package Application.DataAccess;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;



public interface ObjDao extends JpaRepository<EntityObject, String> {

	public List<EntityObject> findAllByActive(
		@Param("active") boolean active, 	
		Pageable pageable);

	public List<EntityObject> findAllBytype(@Param("type") String type, Pageable pageable);
	
	public List<EntityObject> findAllBytypeAndActiveIsTrue(@Param("type") String type, Pageable pageable);

	public Page<EntityObject> findAll(Pageable pageable);
	
	public Optional<EntityObject> findByobjectIDAndActiveIsTrue(@Param("objectID") String objectID);

	//yam: guy pattern in SQL is like not by pattern...
	public List<EntityObject> findAllBypattern(@Param("pattern") String pattern, Pageable pageable);

	public List<EntityObject> findAllBylat(@Param("lat") int lat, PageRequest of);

	public List<EntityObject> findAllByalias(@Param("alias") String alias, PageRequest of);
	
	@Query("SELECT e FROM EntityObject e WHERE ST_DWithin(e.geom, ST_MakePoint(:x, :y), :radius) = true")
    List<EntityObject> findAllWithinRadius(@Param("x") double x, @Param("y") double y, @Param("radius") double radius , Pageable pageable);
}

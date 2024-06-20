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

	
	public List<EntityObject> findAllByaliasLike(@Param("pattern") String pattern, Pageable pageable);


	public List<EntityObject> findAllByalias(@Param("alias") String alias, Pageable pageable);
	
	
//	@Query(value = "SELECT e FROM EntityObject e WHERE " +
//            "(6371 * acos(cos(radians(:latitude)) * cos(radians(e.location_lat)) * cos(radians(e.location_lng) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(e.location_lat)))) < :radius")
//	
//	public List<EntityObject> findAllWithinRadius(@Param("centerLat") double centerLat, 
//                                                  @Param("centerLng") double centerLng, 
//                                                  @Param("radius") double radius  , Pageable pageable);
	@Query(value = "SELECT *, " +
            "SQRT(POW((:centerLat - location_lat), 2) + POW((:centerLng - location_lng), 2)) AS distance " +
            "FROM OBJ_TBL " +
            "WHERE SQRT(POW((:centerLat - location_lat), 2) + POW((:centerLng - location_lng), 2)) <= :radius " +
            "ORDER BY distance", nativeQuery = true)
public List<EntityObject> findAllWithinRadius(@Param("centerLat") double centerLat, 
                                                     @Param("centerLng") double centerLng, 
                                                     @Param("radius") double radius,
                                                     Pageable pageable);
}
	



package Application.DataAccess.Dao;
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

import Application.DataAccess.Entities.EntityObject;



public interface ObjDao extends JpaRepository<EntityObject, String> {
	//RadiusEarth = 6371; 

	public List<EntityObject> findAllByActive(
		@Param("active") boolean active, 	
		Pageable pageable);

	public List<EntityObject> findAllBytype(@Param("type") String type, Pageable pageable);
	
	public Page<EntityObject> findAll(Pageable pageable);
	
	public Optional<EntityObject> findByobjectIDAndActiveIsTrue(@Param("objectID") String objectID);

   
	// @Query("SELECT * FROM Employee e WHERE e.firstName LIKE %:substring%")
	public List<EntityObject> findAllByaliasLike(@Param("pattern") String pattern, Pageable pageable);


	public List<EntityObject> findAllByalias(@Param("alias") String alias, Pageable pageable);
	
//	RadiusKM
@Query(value = "SELECT *, " +
            "(6371 * ACOS(COS(RADIANS(:centerLat)) * COS(RADIANS(location_lat)) * COS(RADIANS(location_lng) - RADIANS(:centerLng)) + SIN(RADIANS(:centerLat)) * SIN(RADIANS(location_lat)))) AS distance " +
            "FROM OBJ_TBL " +
            "WHERE (6371 * ACOS(COS(RADIANS(:centerLat)) * COS(RADIANS(location_lat)) * COS(RADIANS(location_lng) - RADIANS(:centerLng)) + SIN(RADIANS(:centerLat)) * SIN(RADIANS(location_lat)))) <= :radius " +
            "ORDER BY distance", nativeQuery = true)
public List<EntityObject> findAllByLocationWithinRadiusKM(@Param("centerLat") double centerLat, 
                                                     @Param("centerLng") double centerLng, 
                                                     @Param("radius") double radius
                                                     , Pageable pageable);
//Radius natural
	@Query(value = "SELECT *, " +
            "SQRT(POW((:centerLat - location_lat), 2) + POW((:centerLng - location_lng), 2)) AS distance " +
            "FROM OBJ_TBL " +
            "WHERE SQRT(POW((:centerLat - location_lat), 2) + POW((:centerLng - location_lng), 2)) <= :radius " +
            "ORDER BY distance", nativeQuery = true)
public List<EntityObject> findAllWithinRadiusN(@Param("centerLat") double centerLat, 
                                                     @Param("centerLng") double centerLng, 
                                                     @Param("radius") double radius,
                                                     Pageable pageable);
	
	
	
//	RadiusKM with active 
	
	
	@Query(value = "SELECT *, " +
            "SQRT(POW((:centerLat - location_lat), 2) + POW((:centerLng - location_lng), 2)) AS distance " +
            "FROM OBJ_TBL " +
            "WHERE SQRT(POW((:centerLat - location_lat), 2) + POW((:centerLng - location_lng), 2)) <= :radius " +
            "ORDER BY distance", nativeQuery = true)
	public List<EntityObject> findAllWithinRadiusAndActiveIsTrue(@Param("centerLat") double centerLat, 
            @Param("centerLng") double centerLng, 
            @Param("radius") double radius,
            Pageable pageable);

	
	
	public List<EntityObject> findAllByaliasLikeAndActiveIsTrue(@Param("pattern") String pattern, Pageable pageable);

	public List<EntityObject>  findAllByaliasAndActiveIsTrue(@Param("alias")String alias, Pageable of);

	public List<EntityObject> findAllBytypeAndActiveIsTrue(@Param("type")String type, Pageable of);
	
	 @Query("SELECT COUNT(e) FROM EntityObject  e WHERE e.type = :type")
    public long countByType(@Param("type") String type);
	 
	 
	//created by yam to delet club
	public List<EntityObject> findAllByobjectDetailsLike(@Param("pattern") String pattern);
}
	





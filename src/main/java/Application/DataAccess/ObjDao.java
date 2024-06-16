package Application.DataAccess;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Order;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;
import org.springframework.data.repository.query.Param;



public interface ObjDao extends JpaRepository<EntityObject, String> {

	//find all only active SQL query: 
	//using pagination
	public List<EntityObject> findAllByActive(
		@Param("active") boolean active, 	
		Pageable pageable);

	public List<EntityObject> findAllByMessage(String type, PageRequest of);
}

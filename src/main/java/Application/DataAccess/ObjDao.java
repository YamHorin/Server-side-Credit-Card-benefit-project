package Application.DataAccess;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;


public interface ObjDao extends JpaRepository<EntityObject, String> {

	Streamable<Order> findAllByMessage(String type, PageRequest of);

}

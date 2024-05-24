package demo_from_class_;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoDao extends JpaRepository<DemoEntity, String>{

}

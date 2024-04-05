package critisys.res.manager.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import critisys.res.manager.model.EntityState;
import critisys.res.manager.model.Menu;
import java.util.List;


@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {
    List<Menu> findByState(EntityState state);
}

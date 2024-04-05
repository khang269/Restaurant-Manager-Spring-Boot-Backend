package critisys.res.manager.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import critisys.res.manager.model.EntityState;
import critisys.res.manager.model.Menu;
import critisys.res.manager.model.MenuItem;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, String> {
    
    List<MenuItem> findByState(EntityState state);

    List<MenuItem> findByMenu(Menu menu);

    List<MenuItem> findByMenuAndState(Menu menu, EntityState state);

    List<MenuItem> findByMenuIdAndState(String menuId, EntityState state);
}

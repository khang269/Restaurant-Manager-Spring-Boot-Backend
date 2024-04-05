package critisys.res.manager.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import critisys.res.manager.model.ResRole;
import critisys.res.manager.model.ResUser;

@Repository
public interface ResRoleRepository extends JpaRepository<ResRole, String> {
    
    List<ResRole> findByResUser(ResUser resUser);

} 


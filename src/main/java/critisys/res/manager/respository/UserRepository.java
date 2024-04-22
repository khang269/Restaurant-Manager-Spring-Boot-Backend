package critisys.res.manager.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import critisys.res.manager.model.ResUser;

@Repository
public interface UserRepository extends JpaRepository<ResUser, String>{
    
    ResUser findFirstByEmail(String email);

    List<ResUser> findByName(String name);

    Optional<ResUser> findByEmail(String email);

}
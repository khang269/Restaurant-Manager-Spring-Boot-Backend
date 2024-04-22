package critisys.res.manager.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import critisys.res.manager.model.Menu;
import critisys.res.manager.model.ResUser;
import critisys.res.manager.respository.MenuRepository;
import critisys.res.manager.service.ResUserService;

@Configuration
public class DefaultConfiguration {
    
    private static final Logger log = LoggerFactory.getLogger(DefaultConfiguration.class);
    
    @Bean
    CommandLineRunner initDatabase(MenuRepository menuRepository, ResUserService service, BCryptPasswordEncoder bPasswordEncoder){
        return args -> {
            log.info("Preloading " + menuRepository.save(new Menu()));
            log.info("Preloading " + menuRepository.save(new Menu()));

            //Now add a default admin user
            //TODO: setup default admin user by other means.
            ResUser user = new ResUser();
            user.setEmail("youradminemail@gmail.com");
            user.setName("yourPassWord");
            user.setPassword(bPasswordEncoder.encode("khang"));
            service.upsertUser(user);
        };
    }
}

package critisys.res.manager.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import critisys.res.manager.model.Menu;
import critisys.res.manager.respository.MenuRepository;

@Configuration
public class DefaultConfiguration {
    
    private static final Logger log = LoggerFactory.getLogger(DefaultConfiguration.class);
    
    @Bean
    CommandLineRunner initDatabase(MenuRepository menuRepository){
        return args -> {
            log.info("Preloading " + menuRepository.save(new Menu()));
            log.info("Preloading " + menuRepository.save(new Menu()));
        };
    }
}

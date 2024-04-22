package critisys.res.manager.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import critisys.res.manager.model.ResUser;
import critisys.res.manager.respository.UserRepository;

@Service
public class ResUserService {
    
    @Autowired
    private UserRepository userRepository;

    public boolean authenticateUser(String email, String password, BCryptPasswordEncoder encoder){
        ResUser exist = userRepository.findFirstByEmail(email);
        
        if (exist == null) {
            return false;
        }

        return encoder.matches(password, exist.getPassword());
    }

    public ResUser upsertUser(ResUser user){
        Optional<ResUser> exist = userRepository.findByEmail(user.getEmail());
        if (exist.isPresent()){
            ResUser existUser = exist.get();
            existUser.setPassword(user.getPassword());
            existUser.setName(user.getName());
            existUser.setUpdatedDate(new Date());
            userRepository.save(existUser);
            return existUser;
        }
        else{
            ResUser newUser = new ResUser();
            newUser.setCreatedDate(new Date());
            newUser.setUpdatedDate(new Date());
            newUser.setEmail(user.getEmail());
            newUser.setName(user.getName());
            newUser.setPassword(user.getPassword());
            userRepository.save(newUser);
            return newUser;
        }
    }
}

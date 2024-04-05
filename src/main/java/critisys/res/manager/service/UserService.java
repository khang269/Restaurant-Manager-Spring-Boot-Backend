package critisys.res.manager.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import critisys.res.manager.model.ResRole;
import critisys.res.manager.model.ResUser;
import critisys.res.manager.respository.ResRoleRepository;
import critisys.res.manager.respository.UserRepository;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResRoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ResUser user = userRepository.findFirstByEmail(username);
        if (user == null){
            return new User("", "", Arrays.asList(new SimpleGrantedAuthority(ResRole.Role.VISITOR.toString())));
        }

        List<ResRole> roles = roleRepository.findByResUser(user);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (ResRole role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getRole().toString()));
        }
    
        return new User(username, user.getPassword(), authorities);
    }
    
}

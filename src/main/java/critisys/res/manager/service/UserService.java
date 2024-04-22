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

import critisys.res.manager.model.ResUser;
import critisys.res.manager.model.ResUser.Role;
import critisys.res.manager.respository.UserRepository;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ResUser user = userRepository.findFirstByEmail(username);
        if (user == null){
            throw new UsernameNotFoundException("User not exist");
        }

        List<Role> roles = user.getRoles();
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }
    
        return new User(username, user.getPassword(), authorities);
    }

    public UserDetails getDefaultUser(){
        return new User("visitor", "visitor", Arrays.asList(new SimpleGrantedAuthority(Role.VISITOR.toString())));
    }
    
}

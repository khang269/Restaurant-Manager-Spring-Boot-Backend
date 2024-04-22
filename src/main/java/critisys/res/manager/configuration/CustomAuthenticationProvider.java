package critisys.res.manager.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import critisys.res.manager.service.ResUserService;
import critisys.res.manager.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Bean
    public BCryptPasswordEncoder passwordEncoderBean(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private ResUserService userService;

    @Autowired
    private UserService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(userService.authenticateUser((String) authentication.getPrincipal(), (String) authentication.getCredentials(), passwordEncoderBean())){
            UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
            return new UsernamePasswordAuthenticationToken(userDetailsService.loadUserByUsername(authentication.getName()), userDetails.getPassword(), userDetails.getAuthorities());
        }
        else{
            UserDetails userDetails = userDetailsService.getDefaultUser();
            return new UsernamePasswordAuthenticationToken(userDetailsService.loadUserByUsername(authentication.getName()), userDetails.getPassword());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    

}

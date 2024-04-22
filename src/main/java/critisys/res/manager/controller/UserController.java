package critisys.res.manager.controller;

import org.springframework.web.bind.annotation.RestController;

import critisys.res.manager.configuration.JwtTokenUtil;
import critisys.res.manager.respository.UserRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @PostMapping("/signin")
    public ResponseEntity login(@RequestBody SignInEntity signInEntity) {
        
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(signInEntity.getEmail(), signInEntity.getPassword())
        );

        if(!authentication.isAuthenticated()){
            return new ResponseEntity<>("Failed to sign in. Please check your username and password.",HttpStatus.UNAUTHORIZED);
        }

        String jwt = tokenUtil.generateToken((UserDetails) authentication.getPrincipal());

        ResponseTokenEntity body = new ResponseTokenEntity();
        body.setAccessToken(jwt);

        return ResponseEntity.ok().body(body);
    }
    

    public static class SignInEntity {
        
        private String email;

        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }
    
    public static class ResponseTokenEntity {
        
        private String accessToken;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

    }
}

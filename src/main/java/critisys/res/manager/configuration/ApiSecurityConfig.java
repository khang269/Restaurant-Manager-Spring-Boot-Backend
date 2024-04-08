package critisys.res.manager.configuration;

import org.hibernate.internal.SessionCreationOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class ApiSecurityConfig {

    @Bean
    public JwtAuthenticationFilter authenticationFilterBean(){
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(
                (authz) -> 
                    authz
                        .requestMatchers("/token/*")
                        .permitAll()
                        .requestMatchers("/token/*")
                        .hasAnyRole("ADMIN")
                    
            )
            .exceptionHandling(
                (handling) -> 
                    handling.authenticationEntryPoint(null)
            )
            .sessionManagement(
                (session) ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)   
            )
            .addFilterBefore(authenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoderBean(){
        return new BCryptPasswordEncoder();
    }
    
}

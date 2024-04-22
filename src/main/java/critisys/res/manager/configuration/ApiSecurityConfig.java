package critisys.res.manager.configuration;

import org.hibernate.internal.SessionCreationOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class ApiSecurityConfig {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(customAuthenticationProvider);
        return builder.build();
    }

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
                        .requestMatchers("/api/user/signin")
                        .permitAll()
                        .requestMatchers("/api/menu/upsert", "/api/menu/delete", "/api/item/upsert", "/api/item/delete")
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

        http.csrf(
            (config) ->
                config.disable()
        );

        return http.build();
    }
    
}

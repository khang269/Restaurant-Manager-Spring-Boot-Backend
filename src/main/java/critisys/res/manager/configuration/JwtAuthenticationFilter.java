package critisys.res.manager.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import critisys.res.manager.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String header = request.getHeader("Authorization");
        String userName = null;

        if (header != null && header.startsWith("Bearer ")){
            String token = header.replace("Bearer ", "");
            try {
                userName = jwtTokenUtil.getAllClaimsFromToken(token).getSubject();
                UserDetails userDetails = userService.loadUserByUsername(userName);
                if(jwtTokenUtil.validateToken(token, userDetails)){
                    // if token is valid
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
                addDefaultSecurityContext();
            } catch (UnsupportedJwtException e) {
                logger.warn("Jwt Exception", e);
                addDefaultSecurityContext();
            } catch(JwtException e){
                logger.error("Jwt Exception", e);
                addDefaultSecurityContext();
            } catch(UsernameNotFoundException e){
                logger.error("User name Not Found in DB", e);
                addDefaultSecurityContext();
            } 
        }
        else{
            logger.warn("couldn't find bearer string, will ignore the header");
            addDefaultSecurityContext();
        }

        filterChain.doFilter(request, response);
        
    }

    private void addDefaultSecurityContext(){
        UserDetails defaultDetails = userService.getDefaultUser();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(defaultDetails, "", defaultDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    
}

package critisys.res.manager.configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

import java.lang.Exception;

@Component
public class JwtTokenUtil {
    
    public String generateToken(UserDetails userDetails){

        List<String> scopes = new ArrayList<>();
        for(GrantedAuthority authority : userDetails.getAuthorities()){
            scopes.add(authority.getAuthority());
        }

        Claims claims = Jwts
                            .claims()
                            .subject(userDetails.getUsername())
                            .issuer("critisys.res.manager")
                            .issuedAt(new Date(System.currentTimeMillis()))
                            .expiration(new Date(System.currentTimeMillis() + 3600*1000))
                            .add("scopes", scopes)
                            .build();

        return Jwts
                    .builder()
                    .claims(claims)
                    .signWith(Keys.hmacShaKeyFor("random".getBytes()), Jwts.SIG.HS256)
                    .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        try {
            Claims claims = getAllClaimsFromToken(token);

            String subject = claims.getSubject();
            Date expDate = claims.getExpiration();

            return userDetails.getUsername().equals(subject) && (new Date()).before(expDate); 
        }
        catch(Exception e){
            return false;
        } 
    }

    public Claims getAllClaimsFromToken(String token) throws UnsupportedJwtException, JwtException, IllegalArgumentException{
        return Jwts
                .parser()
                .decryptWith(Keys.hmacShaKeyFor("random".getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}

package bartlomiejzienkiewicz.forumapi.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
public class JWTAuthentication {




    public static boolean isTokenValid(String token){



        UsernamePasswordAuthenticationToken authenticated = getAuthenticationByToken(token);

        return authenticated != null;

    }

    private static UsernamePasswordAuthenticationToken getAuthenticationByToken(String token) {

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey("1273tabdsfttv62349111".getBytes())
                .parseClaimsJws(token.replace("Bearer ", ""));

        String username = claimsJws.getBody().get("name").toString();
        String role = claimsJws.getBody().get("role").toString();
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = Collections.singleton(new SimpleGrantedAuthority(role));

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);
        return usernamePasswordAuthenticationToken;

    }


}

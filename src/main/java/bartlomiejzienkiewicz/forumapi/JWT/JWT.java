package bartlomiejzienkiewicz.forumapi.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Component
public class JWT {
    private String username;

    private String role;

    private Long timeOfWorking =172800000L; //2 days

    private String token;





    public String getToken(){



        String jws = Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setIssuer("API")
                .setSubject(username)
                .claim("role",role)
                .claim("name", username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + timeOfWorking))
                .signWith(
                        SignatureAlgorithm.HS512,
                        "1273tabdsfttv62349111".getBytes()


                )
                .compact();
        return jws;
    }
}

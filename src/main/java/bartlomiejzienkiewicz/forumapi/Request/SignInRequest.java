package bartlomiejzienkiewicz.forumapi.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class SignInRequest {
    private String username;
    private String password;
}

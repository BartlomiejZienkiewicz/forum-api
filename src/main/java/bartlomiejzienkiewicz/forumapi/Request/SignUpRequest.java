package bartlomiejzienkiewicz.forumapi.Request;

import bartlomiejzienkiewicz.forumapi.Repository.UserRepository.UserServiceImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {
    private String username;
    private String password;
    private String email;
}

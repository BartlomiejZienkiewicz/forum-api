package bartlomiejzienkiewicz.forumapi.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SendMessageRequest {

    private String token;
    private String authorsUsername;
    private String secondUsersUsername;
    private String contents;


}

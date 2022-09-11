package bartlomiejzienkiewicz.forumapi.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DoesConversationExist {

    private String token;
    private String firstUsersUsername;
    private String secondUsersUsername;

}

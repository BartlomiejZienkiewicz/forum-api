package bartlomiejzienkiewicz.forumapi.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetMessagesByConversationRequest {

    String firstUserUsername;
    String secondUserUsername;
    String token;


}

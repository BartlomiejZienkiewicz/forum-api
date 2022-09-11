package bartlomiejzienkiewicz.forumapi.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateAnswerRequest {
    private String token;
    private String contents;
    private String username;
    private Long idOfPost;
    private Long idOfAnsweredCommend;
}

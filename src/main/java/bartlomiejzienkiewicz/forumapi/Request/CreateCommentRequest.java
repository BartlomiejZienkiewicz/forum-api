package bartlomiejzienkiewicz.forumapi.Request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateCommentRequest {

    private String token;
    private String username;
    private String contents;
    private String idOfPost;
}

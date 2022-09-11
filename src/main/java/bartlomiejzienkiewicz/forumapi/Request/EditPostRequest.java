package bartlomiejzienkiewicz.forumapi.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditPostRequest {
    private String jwt;
    private String editedText;
    private Long postId;
}

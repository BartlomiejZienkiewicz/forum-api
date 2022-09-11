package bartlomiejzienkiewicz.forumapi.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreatePostRequest {
    private String token;
    private String contents;
    private String username;

}

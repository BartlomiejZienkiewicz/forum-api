package bartlomiejzienkiewicz.forumapi.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteNotificationRequest {

    private String token;
    private Long notificationId;

}

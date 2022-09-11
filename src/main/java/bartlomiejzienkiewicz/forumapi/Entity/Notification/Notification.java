package bartlomiejzienkiewicz.forumapi.Entity.Notification;

import bartlomiejzienkiewicz.forumapi.Entity.User.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "Notifications")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "contents")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "idOfUser")
    @NotNull
    private User user;

    @Column(name ="timeOfCreation")
    private Date timeOfCreation;

    @Column(name = "idOfPost")
    private Long idOfPost;

    @Column(name = "type")
    private NotificationType notificationType;

    @Column(name = "from_who")
    private String fromWho;

}

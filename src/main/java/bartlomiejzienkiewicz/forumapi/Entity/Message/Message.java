package bartlomiejzienkiewicz.forumapi.Entity.Message;

import bartlomiejzienkiewicz.forumapi.Entity.User.User;
import bartlomiejzienkiewicz.forumapi.Entity.Conversation.Conversation;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "contents")
    private String contents;

    @Column(name = "dateOfCreation")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date utilDate;

    //author
    @ManyToOne()
    @JoinColumn(name = "idOfUser")
    private User user;


    @ManyToOne
    @JoinColumn(name = "idOfConversation")
    private Conversation conversation;


}

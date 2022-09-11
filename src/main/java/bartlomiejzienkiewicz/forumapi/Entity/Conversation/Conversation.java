package bartlomiejzienkiewicz.forumapi.Entity.Conversation;



import bartlomiejzienkiewicz.forumapi.Entity.Message.Message;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Conversations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "idOfFirstUser")
    private Long idOfFirstUser;


    @Column(name = "idOfSecondUser")
    private Long idOfSecondUser;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE,
            mappedBy = "conversation")
    private List<Message> messageList = new ArrayList<Message>();






    public void addMessage(Message message){
        if(messageList == null){
            messageList = new ArrayList<>();
        }
        messageList.add(message);
        message.setConversation(this);
    }

}

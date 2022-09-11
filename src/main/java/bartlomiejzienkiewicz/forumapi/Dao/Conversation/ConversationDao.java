package bartlomiejzienkiewicz.forumapi.Dao.Conversation;


import bartlomiejzienkiewicz.forumapi.Dao.Dao;
import bartlomiejzienkiewicz.forumapi.Entity.Conversation.Conversation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConversationDao implements Dao<Conversation>, Comparable<ConversationDao> {

    private String secondUsersUsername;

    @Override
    public void convertToDao(Conversation entity) {

    }

    @Override
    public int compareTo(ConversationDao o) {
        return this.getSecondUsersUsername().compareTo(o.getSecondUsersUsername());

    }
}

package bartlomiejzienkiewicz.forumapi.Repository.ConversationRepository;

import bartlomiejzienkiewicz.forumapi.Entity.Conversation.Conversation;
import bartlomiejzienkiewicz.forumapi.Repository.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationService implements EntityService<Conversation> {

    private ConversationRepository conversationRepository;

    @Autowired
    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Override
    public void save(Conversation entity) {
        conversationRepository.save(entity);
    }

    @Override
    public void deleteAll() {
        conversationRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        conversationRepository.deleteById(id);
    }

    @Override
    public Optional<Conversation> findById(Long id) {
        return conversationRepository.findById(id);
    }

    public List<Conversation> findAll(){
        return conversationRepository.findAll();
    }

    public Conversation findConversationByIdsOfTwoUsers(Long authorsId, Long secondUsersId) {


        for(Conversation conversation: conversationRepository.findAll()){
            if(conversation.getIdOfFirstUser() == authorsId && conversation.getIdOfSecondUser() == secondUsersId){
                return conversation;
            }
            else if(conversation.getIdOfFirstUser() == secondUsersId && conversation.getIdOfSecondUser() == authorsId){
                return conversation;
            }
        }
        return null;

    }
    public List<Conversation> findByIdOfUser(Long id){
        List<Conversation> conversations = new ArrayList<>();
        for(Conversation conversation: findAll()){
            if(conversation.getIdOfFirstUser().equals(id) || conversation.getIdOfSecondUser().equals(id)){
                conversations.add(conversation);
            }
        }
        return conversations;
    }


}

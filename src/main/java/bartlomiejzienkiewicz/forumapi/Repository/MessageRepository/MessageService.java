package bartlomiejzienkiewicz.forumapi.Repository.MessageRepository;

import bartlomiejzienkiewicz.forumapi.Entity.Conversation.Conversation;
import bartlomiejzienkiewicz.forumapi.Entity.Message.Message;
import bartlomiejzienkiewicz.forumapi.Repository.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService implements EntityService<Message> {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void save(Message entity) {
        messageRepository.save(entity);
    }

    @Override
    public void deleteAll() {
        messageRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    public List<Message> getMessagesByConversation(Conversation conversation){
        List<Message> messageList = new ArrayList<>();
        messageList = messageRepository.findAll().stream().filter(message -> message.getConversation().equals(conversation)).collect(Collectors.toList());
        return messageList;
    }
}

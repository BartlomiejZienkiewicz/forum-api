package bartlomiejzienkiewicz.forumapi;

import bartlomiejzienkiewicz.forumapi.Repository.CommentRepository.CommentServiceImpl;
import bartlomiejzienkiewicz.forumapi.Repository.ConversationRepository.ConversationService;
import bartlomiejzienkiewicz.forumapi.Repository.MessageRepository.MessageService;
import bartlomiejzienkiewicz.forumapi.Repository.PostRepository.PostServiceImpl;
import bartlomiejzienkiewicz.forumapi.Repository.UserRepository.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForumApiApplication implements CommandLineRunner {



	@Autowired
	UserServiceImpl userService;

	@Autowired
	PostServiceImpl postService;

	@Autowired
	CommentServiceImpl commentService;

	@Autowired
	ConversationService conversationService;

	@Autowired
	MessageService messageService;

	@Value("${jwt.key}")
	private String key;





	public static void main(String[] args) {
		SpringApplication.run(ForumApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}






}

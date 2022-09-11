package bartlomiejzienkiewicz.forumapi.Repository.UserRepository;

import bartlomiejzienkiewicz.forumapi.Entity.User.User;
import bartlomiejzienkiewicz.forumapi.Repository.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements EntityService<User> {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void save(User entity) {
        userRepository.save(entity);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User findByUsername(String username){
        return userRepository.findAll().stream().filter(user -> user.getUsername().equals(username)).findFirst().get();
    }
    public boolean isEmailTaken(String email){
        List<User> userList = userRepository.findAll();
        for(User user: userList){
            if(user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    public boolean isUsernameTaken(String username) {
        List<User> userList = userRepository.findAll();
        for(User user: userList){
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }
}

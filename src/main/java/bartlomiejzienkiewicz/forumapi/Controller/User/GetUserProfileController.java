package bartlomiejzienkiewicz.forumapi.Controller.User;

import bartlomiejzienkiewicz.forumapi.Repository.UserRepository.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class GetUserProfileController {


    private UserServiceImpl userService;

    @Autowired
    public GetUserProfileController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/get_user_profile/{username}")
    public ResponseEntity<String> getUserProfileData(@PathVariable("username") String username){
        if(userService.findByUsername(username) == null){
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}

package bartlomiejzienkiewicz.forumapi.Controller;

import bartlomiejzienkiewicz.forumapi.Entity.User.Role;
import bartlomiejzienkiewicz.forumapi.Entity.User.User;

import bartlomiejzienkiewicz.forumapi.Repository.UserRepository.UserServiceImpl;
import bartlomiejzienkiewicz.forumapi.Request.SignUpRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class SignUpController {

    private UserServiceImpl userService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpController(UserServiceImpl userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/signUp", consumes = "application/json", method = RequestMethod.PUT)
    public ResponseEntity signUp(@RequestBody SignUpRequest signUpRequest){

        //get data from request
        String username = signUpRequest.getUsername();
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();


        if(userService.isEmailTaken(email)){
            Gson gson = new Gson();
            String json = gson.toJson("email is taken");

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }


        else if(userService.isUsernameTaken(username)){
            Gson gson = new Gson();
            String json = gson.toJson("username is taken");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }

        else{
            //Sign up user
            User user = User.builder().username(username).
                    password(passwordEncoder.encode(password)).email(email).role(Role.USER).build();

            userService.save(user);

            Gson gson = new Gson();
            String json = gson.toJson("ok");
            return ResponseEntity.status(HttpStatus.OK).body(json);

        }
    }


}

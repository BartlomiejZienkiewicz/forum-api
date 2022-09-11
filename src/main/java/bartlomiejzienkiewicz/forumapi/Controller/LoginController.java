package bartlomiejzienkiewicz.forumapi.Controller;

import bartlomiejzienkiewicz.forumapi.Entity.User.Role;
import bartlomiejzienkiewicz.forumapi.Entity.User.User;
import bartlomiejzienkiewicz.forumapi.JWT.JWT;
import bartlomiejzienkiewicz.forumapi.Repository.UserRepository.UserServiceImpl;
import bartlomiejzienkiewicz.forumapi.Request.SignInRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class LoginController {

    private UserServiceImpl userService;

    private PasswordEncoder passwordEncoder;


    @Autowired
    public LoginController(UserServiceImpl userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;

    }

    @RequestMapping(value = "/signIn", consumes = "application/json", method = RequestMethod.PUT)
    public ResponseEntity signIn(@RequestBody SignInRequest signInRequest){

        //get data from request
        String username = signInRequest.getUsername();
        String password = signInRequest.getPassword();



        User user = userService.findByUsername(username);
        boolean passwordMatch = passwordEncoder.matches(password, user.getPassword());

        //if credentials are true
        if(passwordMatch){

            String role = "";

            if(user.getRole() == Role.USER){
                role = "ROLE_USER";
            }
            else if(user.getRole() == Role.ADMIN){
                role = "ROLE_ADMIN";
            }
            //create token
            JWT jwt = JWT.builder().username(user.getUsername()).timeOfWorking(System.currentTimeMillis())
                        .role(role)
                        .build();


            Gson gson = new Gson();
            String json = gson.toJson(jwt.getToken());

            return ResponseEntity.status(HttpStatus.OK).body(json);



        }

        else {
            Gson gson = new Gson();
            String json = gson.toJson("error");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }
    }
}

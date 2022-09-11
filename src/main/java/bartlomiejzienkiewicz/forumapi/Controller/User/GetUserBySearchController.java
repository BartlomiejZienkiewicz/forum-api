package bartlomiejzienkiewicz.forumapi.Controller.User;

import bartlomiejzienkiewicz.forumapi.Dao.User.UserProfileDao;
import bartlomiejzienkiewicz.forumapi.Entity.User.User;
import bartlomiejzienkiewicz.forumapi.Repository.UserRepository.UserServiceImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class GetUserBySearchController{

    private UserServiceImpl userService;

    @Autowired
    public GetUserBySearchController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(path="/get_users_by_search/{input}/{usernameOfSendingUser}")
    public ResponseEntity<String> getUsersBySearch(@PathVariable("input") String input,
                                                   @PathVariable("usernameOfSendingUser") String usernameOfSendingUser){


        List<UserProfileDao> userProfileDaoList = new ArrayList<>();



        try{
            //trying to find user
            User user = userService.findByUsername(input);
            //if user is found and user is not user that sends request
            if(user != null && !user.getUsername().equals(usernameOfSendingUser)){
                UserProfileDao userProfileDao = new UserProfileDao();
                userProfileDao.convertToDao(user);
                userProfileDaoList.add(userProfileDao);
            }
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }


        for(User user1: userService.findAll()){
            //if input is in username
            boolean isFound = user1.getUsername().indexOf(input) !=-1? true: false;


            if(isFound == true && !user1.getUsername().equals(input) && !user1.getUsername().equals(usernameOfSendingUser)){
                UserProfileDao userProfileDao = new UserProfileDao();
                userProfileDao.convertToDao(user1);
                userProfileDaoList.add(userProfileDao);
            }
        }

        Gson gson = new Gson();


        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(userProfileDaoList));
    }


}

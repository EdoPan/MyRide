package it.dsmt.myRide.view;
import it.dsmt.myRide.model.User;
import it.dsmt.myRide.controller.UserController;
import it.dsmt.myRide.dto.LoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserView {
    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<String> signup(@RequestBody User user){
        try{
            UserController.registerUser(user.getUsername(), user.getPassword());
        } catch (Exception e){
            System.out.println("[USER VIEW] Unable to sign-up user");
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("User registered", HttpStatus.OK);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<LoginDTO> login(@RequestBody User user){
        try{
            LoginDTO login = UserController.loginUser(user.getUsername(), user.getPassword());
            return ResponseEntity.ok(login);
        } catch (Exception e){
            System.out.println("[USER CONTROLLER] Impossible to login");
            return new ResponseEntity("ERROR", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Boolean> checkIfMaintainer(@RequestParam String username){
        boolean response;
        try{
            response = UserController.checkIfMaintainer(username);
        } catch (Exception e){
            System.out.println("[USER VIEW] Impossible to fetch the user");
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/chats")
    public ResponseEntity<String> getChatRequests(){
        String result = "";
        try{
            result = UserController.getChatRequests();
        } catch (Exception e){
            System.out.println("[USER VIEW] Impossible to get active chats");
            return new ResponseEntity<>("NOT_FOUND" , HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
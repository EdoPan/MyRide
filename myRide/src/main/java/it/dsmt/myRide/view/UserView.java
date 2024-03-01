package it.dsmt.myRide.view;
import it.dsmt.myRide.model.User;
import it.dsmt.myRide.controller.UserController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public ResponseEntity<String> login(@RequestBody User user){
        try{
            String username;
            username = UserController.loginUser(user.getUsername(), user.getPassword());
            if (username != ""){
                return ResponseEntity.ok(username);
            }else{
                return new ResponseEntity(null , HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e){
            System.out.println("[USER VIEW] Impossible to login");
            return new ResponseEntity(null , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

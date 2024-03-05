package it.dsmt.myRide.view;
import it.dsmt.myRide.model.User;
import it.dsmt.myRide.controller.UserController;
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
            UserController.registerUser(user.getUsername(), user.getPassword(), user.getIsMaintainer());
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
            username = UserController.loginUser(user.getUsername(), user.getPassword(), user.getIsMaintainer());
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

    @GetMapping("/user/{username}")
    public ResponseEntity<Boolean> checkIfMaintainer(@RequestParam String username){
        boolean response;
        try{
            response = UserController.checkIfMaintainer(username);
        } catch (Exception e){
            System.out.println("[RIDE VIEW] Impossible to fetch the ride");
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(response);
    }

}

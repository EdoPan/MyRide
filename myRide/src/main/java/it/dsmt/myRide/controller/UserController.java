package it.dsmt.myRide.controller;
import it.dsmt.myRide.model.User;

public class UserController {

    public static void registerUser(String username, String password) throws Exception{
        User user = new User(username, password);
        try{
            user.register();
        } catch (Exception e){
            throw e;
        }
        
    }
    
    public static String loginUser(String username, String password) throws Exception{
        User user = new User(username, password);
        try {
            user.login();
            return user.getUsername();
        } catch (Exception e){
            throw e;
        }

    }
}

package it.dsmt.myRide.controller;
import it.dsmt.myRide.model.User;

public class UserController {

    public static void registerUser(String username, String password, boolean isMainteiner) throws Exception{
        User user = new User(username, password, isMainteiner);
        try{
            user.register();
        } catch (Exception e){
            throw e;
        }
        
    }
    
    public static String loginUser(String username, String password, boolean isMaintainer) throws Exception{
        User user = new User(username, password, isMaintainer);
        try {
            user.login();
            return user.getUsername();
        } catch (Exception e){
            throw e;
        }

    }

    public static boolean checkIfMaintainer(String username) throws Exception{
        User user = User.getUserByUsername(username);
        try {
            return user.checkIfMaintainer();
        } catch (Exception e){
            throw e;
        }
    }
}

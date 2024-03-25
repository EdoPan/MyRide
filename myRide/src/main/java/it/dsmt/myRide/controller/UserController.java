package it.dsmt.myRide.controller;
import it.dsmt.myRide.dto.LoginDTO;
import it.dsmt.myRide.model.MasterNode;
import it.dsmt.myRide.model.User;

public class UserController {

    public static void registerUser(String username, String password) throws Exception{
        User user = new User(username, password, false);
        try{
            user.register();
        } catch (Exception e){
            throw e;
        }  
    }
    
    public static LoginDTO loginUser(String username, String password) throws Exception{
        User user = new User(username, password, false);
        try {
            if(user.login()){
                LoginDTO account = new LoginDTO(user.getUsername(), user.getIsMaintainer());
                return account;
            }
            else{
                throw new Exception();
            }
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

    public static String getChatRequests() throws RuntimeException{
        try{
            String res = MasterNode.getChatRequests();
            return res;
        }catch(RuntimeException e){
            throw e;
        }
    }
}

package it.dsmt.myRide.controller;
import it.dsmt.myRide.dto.LoginDTO;
import it.dsmt.myRide.model.MasterNode;
import it.dsmt.myRide.model.User;

import java.sql.SQLException;

import com.rqlite.NodeUnavailableException;

public class UserController {

    public static void registerUser(String username, String password) throws NodeUnavailableException,SQLException{
        User user = new User(username, password, false);
        try{
            user.register();
        } catch (NodeUnavailableException e){
            throw e;
        }  
    }
    
    public static LoginDTO loginUser(String username, String password) throws Exception, NodeUnavailableException{
        User user = new User(username, password, false);
        try {
            if(user.login()){
                LoginDTO account = new LoginDTO(user.getUsername(), user.getIsMaintainer());
                return account;
            }
            else{
                throw new Exception();
            }
        } catch (NodeUnavailableException e){
            throw e;
        }
    }

    public static boolean checkIfMaintainer(String username) throws NodeUnavailableException{
        User user = User.getUserByUsername(username);
        try {
            return user.checkIfMaintainer();
        } catch (NodeUnavailableException e){
            throw e;
        }
    }

    public static String getChatRequests() throws RuntimeException{
        try{
            String res = MasterNode.getChatRequests();
            String charsToRemove = "{";
            for (char c : charsToRemove.toCharArray()) {
                res = res.replace(String.valueOf(c), "");
            }
            charsToRemove = "}";
            for (char c : charsToRemove.toCharArray()) {
                res = res.replace(String.valueOf(c), "");
            }
            return res;
        } catch (RuntimeException e){
            throw e;
        }
    }
}
package it.dsmt.myRide.model;
import it.dsmt.myRide.controller.DBController;
import java.sql.SQLException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rqlite.NodeUnavailableException;
import com.rqlite.Rqlite;
import com.rqlite.dto.QueryResults;

public class User {
    private String username;
    private String password;
    private boolean isMaintainer;

    public User(){
    }

    public User(String username, String password, boolean isMaintainer){
        this.username = username;
        this.password = password;
        this.isMaintainer = isMaintainer;
    }

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public boolean getIsMaintainer(){
        return this.isMaintainer;
    }

    public void setIsMainteiner(boolean isMaintainer){
        this.isMaintainer = isMaintainer;
    }

    public void register() throws NodeUnavailableException,SQLException{
        // Default registration is for user not maintainers so isMaintainer is set to false
        try{
            if(getUserByUsername(this.username) == null){
                String query = "INSERT INTO users(username, password, isMaintainer) VALUES('" + 
                this.username + "','" + this.password + "',FALSE)";
                DBController.getInstance().getConnection().Execute(query);
            }
            else{
                throw new SQLException();
            }    
        }
        catch(Exception e){
            throw e;
        }
    }
    
    public boolean login() throws NodeUnavailableException{
        String query = "SELECT * FROM users where username = '" + this.username + "' and password = '" + this.password + "'";
        QueryResults res = DBController.getInstance().getConnection().Query(query, Rqlite.ReadConsistencyLevel.STRONG);
        Gson gson = new Gson();
        String json = gson.toJson(res);
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        boolean check = jsonObject.getAsJsonArray("results").get(0).getAsJsonObject().has("values");
        if (res != null && res.results != null && res.results.length > 0 && !jsonObject.has("error") && check) {
            this.isMaintainer = jsonObject.getAsJsonArray("results")
            .get(0).getAsJsonObject()
            .getAsJsonArray("values").get(0).getAsJsonArray().get(2).getAsBoolean();
            return true;
        }
        return false;
    }

    public static User getUserByUsername(String username) throws NodeUnavailableException{
        String query = "SELECT * FROM users WHERE username = '" + username + "'";
        QueryResults res = DBController.getInstance().getConnection().Query(query, Rqlite.ReadConsistencyLevel.STRONG);
        Gson gson = new Gson();
        String json = gson.toJson(res);
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        boolean check = jsonObject.getAsJsonArray("results").get(0).getAsJsonObject().has("values");
        if(!jsonObject.has("error") && check){
            String password = jsonObject.getAsJsonArray("results")
                .get(0).getAsJsonObject()
                .getAsJsonArray("values").get(0).getAsJsonArray().get(1).getAsString();
            Boolean isMaintainer = jsonObject.getAsJsonArray("results")
                .get(0).getAsJsonObject()
                .getAsJsonArray("values").get(0).getAsJsonArray().get(2).getAsBoolean();
            User user = new User(username, password, isMaintainer);
            return user;
        }
        else{
            return null;
        }
    }

    public boolean checkIfMaintainer() throws NodeUnavailableException{
        boolean checkIfMaintainer = false;
        String query = "SELECT isMaintainer FROM users WHERE username = " + this.username;
        QueryResults res = DBController.getInstance().getConnection().Query(query, Rqlite.ReadConsistencyLevel.STRONG);
        Gson gson = new Gson();
        String json = gson.toJson(res);
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        boolean check = jsonObject.getAsJsonArray("results").get(0).getAsJsonObject().has("values");
        if(!jsonObject.has("error") && check){
            checkIfMaintainer = jsonObject.getAsJsonArray("results")
                .get(0).getAsJsonObject()
                .getAsJsonArray("values").get(0).getAsJsonArray().get(2).getAsBoolean();
        }        
        return checkIfMaintainer;
    }
}
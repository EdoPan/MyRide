package it.dsmt.myRide.model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.dsmt.myRide.controller.DBController;

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

    public void register() throws Exception{
        // Default registration is for user not maintainers so isMaintainer is set to false
        String query = "INSERT INTO users(username, password, isMaintainer) VALUES('" + 
        this.username + "','" + this.password + "',FALSE)";
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            stmt.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw e;
            }
    }
    
    public boolean login(){
        String query = "SELECT * FROM users where username = '" + this.username + "' and password = '" + this.password + "'";

        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            if (res != null && res.next()) {
                this.isMaintainer = res.getBoolean("isMaintainer");
                res.close();
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
        return false;
    }

    public static User getUserByUsername(String username){
        User user = new User();
        String query = "SELECT * FROM users WHERE username = '" + username + "'";
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            user = new User(res.getString("username"), res.getString("password"), 
            res.getBoolean("isMaintainer"));
            res.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public boolean checkIfMaintainer(){
        String query = "SELECT isMaintainer FROM users WHERE username = " + this.username;
        boolean check = false;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            check = res.getBoolean("isMaintainer");
            res.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return check;
    }
}
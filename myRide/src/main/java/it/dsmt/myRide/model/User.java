package it.dsmt.myRide.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public void register(){
        // Default registration is for user not maintainers so isMaintainer is set to false
        String query = "INSERT INTO users(username, password, isMaintainer) VALUES(" + 
        this.username + ",'" + this.password + ",'" + "false"+ "')";
        try (Connection conn = DBController.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }
    
    public boolean login(){
        // handle the login
        return true;
    }

    public static User getUserByUsername(String username){
        User user = new User();
        String query = "SELECT * FROM users WHERE username > ?";
        
        try (Connection conn = DBController.connect();
             PreparedStatement pstmt  = conn.prepareStatement(query)){
            
            pstmt.setString(1,username);
            ResultSet rs  = pstmt.executeQuery();

            user = new User(rs.getString("username"), rs.getString("password"), 
            rs.getBoolean("isMaintainer"));
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public boolean checkIfMaintainer(){
        String query = "SELECT isMaintainer FROM users WHERE username = " + this.username;
        boolean check = false;
        try (Connection conn = DBController.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)){
            check = rs.getBoolean("isMaintainer");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return check;
    }
}


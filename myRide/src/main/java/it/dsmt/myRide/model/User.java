package it.dsmt.myRide.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.dsmt.myRide.controller.DBController;

public class User {
    private String username;
    private String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
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

    public void register(){
        String query = "INSERT INTO users(username, password) VALUES(" + 
        this.username + ",'" + this.password + "')";
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
}
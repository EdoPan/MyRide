package it.dsmt.myRide.model;
import java.util.ArrayList;
import java.util.List;
import it.dsmt.myRide.controller.DBController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Bike {
    private int id;
    private String type;
    private double price;
    private String condition;

    public Bike(){
    }

    public Bike(int id, String type, double price, String condition){
        this.id = id;
        this.type = type;
        this.price = price;
        this.condition = condition;
    }

    public int getID(){
        return this.id;
    }
    public void setID(int id){
        this.id = id;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    public double getPrice(){
        return this.price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public String getCondition(){
        return this.condition;
    }

    public void setCondition(String condition){
        this.condition = condition;
    }

    public static Bike getBikeByID(int id){
        Bike bike = new Bike();
        String query = "SELECT * FROM bikes WHERE id > ?";
        
        try (Connection conn = DBController.connect();
             PreparedStatement pstmt  = conn.prepareStatement(query)){
            
            pstmt.setInt(1,id);
            ResultSet rs  = pstmt.executeQuery();

            bike = new Bike(rs.getInt("id"), rs.getString("type"), 
            rs.getDouble("price"), rs.getString("condition"));
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bike;
    }

    public static List<Bike> getBikes(){
        List<Bike> bikes = new ArrayList<Bike>();
        String query = "SELECT * FROM bikes";
        try (Connection conn = DBController.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){
                while(rs.next()){
                    Bike bike = new Bike(rs.getInt("id"), rs.getString("type"), 
                    rs.getDouble("price"), rs.getString("condition"));
                    bikes.add(bike);
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        return bikes;
    }

    public static void repairBike(int id){
        String query = "UPDATE bikes SET condition = 'new'" + "WHERE id > ?";
        try (Connection conn = DBController.connect();
            PreparedStatement pstmt  = conn.prepareStatement(query)){
                pstmt.setInt(1,id);
                pstmt.executeQuery();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }        }

    public void addBike(){
        String query = "INSERT INTO bikes(id, type, price, condition) VALUES(" + 
        this.id + ",'" + this.type + "', '" + this.price + "'," + this.condition + "')";
        try (Connection conn = DBController.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }
    
    public void removeBike(){
        String query = "DELETE FROM bikes WHERE id = " + this.id;
        try (Connection conn = DBController.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }    
    }

    public void markCondition(){
        String query = "UPDATE bikes SET condition = " + this.condition + "WHERE id = " + this.id;
        try (Connection conn = DBController.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }    
    }
}

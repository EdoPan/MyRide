package it.dsmt.myRide.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.dsmt.myRide.controller.DBController;

import java.util.ArrayList;
import java.util.List;

public class Station {
    private int id;
    private String address;
    private int numberOfBikes;

    public Station(){
    }

    public Station(int id, String address, int numberOfBikes){
        this.id = id;
        this.address = address;
        this.numberOfBikes = numberOfBikes;
    }

    public int getID(){
        return this.id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getAddress(){
        return this.address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public int getNumberOfBikes(){
        return this.numberOfBikes;
    }

    public void setNumberOfBikes(int numberOfBikes){
        this.numberOfBikes = numberOfBikes;
    }

    public static Station getStationByID(int id){
        Station station = new Station();
        String query = "SELECT * FROM stations WHERE id = ?";
        
        try (Connection conn = DBController.connect();
             PreparedStatement pstmt  = conn.prepareStatement(query)){
            
            pstmt.setInt(1,id);
            ResultSet rs  = pstmt.executeQuery();

            station = new Station(rs.getInt("id"), rs.getString("address"), 
            rs.getInt("numberOfBikes"));
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return station;
    }

    public void addStation(){
        String query = "INSERT INTO stations(id, type, price, condition) VALUES(" + 
        this.id + ",'" + this.address + "', '" + this.numberOfBikes + "'," + "')";
        try (Connection conn = DBController.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    public void removeStation(){
        String query = "DELETE FROM stations WHERE id = " + this.id;
        try (Connection conn = DBController.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }     
        }

    public List<Bike> getBikes(){
        List<Bike> bikes = new ArrayList<Bike>();
        String query = "SELECT * FROM bikes WHERE stationID = " + this.id;
        try (Connection conn = DBController.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){
                while(rs.next()){
                    Bike bike = new Bike(rs.getInt("id"), rs.getString("type"), 
                    rs.getDouble("price"), rs.getString("condition"), rs.getInt("stationID"));
                    bikes.add(bike);
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        return bikes;
    }

}

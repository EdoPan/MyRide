package it.dsmt.myRide.model;
import java.util.ArrayList;
import java.util.List;
import it.dsmt.myRide.controller.DBController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Bike {
    private int id;
    private String type;
    private double price;
    private String condition;
    private int stationID;

    public Bike(){
    }

    public Bike(int id, String type, double price, String condition, int stationID){
        this.id = id;
        this.type = type;
        this.price = price;
        this.condition = condition;
        this.stationID = stationID;
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

    public int getStationID(){
        return this.stationID;
    }

    public void setStationID(int stationID){
        this.stationID = stationID;
    }

    public static Bike getBikeByID(int id){
        Bike bike = new Bike();
        String query = "SELECT * FROM bikes WHERE id = " + id;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            bike = new Bike(res.getInt("id"), res.getString("type"), 
            res.getDouble("price_per_hour"), res.getString("condition"), res.getInt("stationID"));
            res.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bike;
    }

    public static List<Bike> getAllBikes(){
        List<Bike> bikes = new ArrayList<Bike>();
        String query = "SELECT * FROM bikes";
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
                while(res.next()){
                    Bike bike = new Bike(res.getInt("id"), res.getString("type"), 
                    res.getDouble("price_per_hour"), res.getString("condition"), res.getInt("stationID"));
                    bikes.add(bike);
                }
                res.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        return bikes;
    }

    public void repairBike(){
        String query = "UPDATE bikes SET condition = 'new' " + "WHERE id = " + this.id;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            res.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }        
    }

    public void addBike(){
        String query = "INSERT INTO bikes(id, type, price_per_hour, condition, stationID) VALUES(" + 
        this.id + ",'" + this.type + "', '" + this.price + "'," + this.condition + "'," + this.stationID + "')";
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            res.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }
    
    public void removeBike(){
        String query = "DELETE FROM bikes WHERE id = " + this.id;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            res.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }    
    }

    public void markCondition(){
        String query = "UPDATE bikes SET condition = '" + this.condition + "' WHERE id = " + this.id;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            res.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }    
    }

    public void assignBikeToStation(){
        String query = "UPDATE bikes SET stationID = " + this.stationID + " WHERE id = " + this.id;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            res.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } 
    }
}

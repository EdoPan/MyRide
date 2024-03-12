package it.dsmt.myRide.model;
import java.util.ArrayList;
import java.util.List;
import it.dsmt.myRide.controller.DBController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import it.dsmt.myRide.dto.BikeToBeRepairedDTO;


public class Bike {
    private int id;
    private String type;
    private double price;
    private String condition;
    private int stationID;

    public Bike(){
    }

    public Bike(String type, double price, int stationID){
        this.type = type;
        this.price = price;
        this.stationID = stationID;
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
            res.getDouble("price"), res.getString("condition"), res.getInt("stationID"));
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
                    res.getDouble("price"), res.getString("condition"), res.getInt("stationID"));
                    bikes.add(bike);
                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return bikes;
    }

    public static List<BikeToBeRepairedDTO> getBikesToBeRepaired(){
        List<BikeToBeRepairedDTO> bikes = new ArrayList<BikeToBeRepairedDTO>();
        String query = "SELECT a.id, a.type, b.address FROM bikes a INNER JOIN stations b ON a.stationID = b.id WHERE a.condition = 'new'";
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
                while(res.next()){
                    BikeToBeRepairedDTO bike = new BikeToBeRepairedDTO(res.getInt("id"), res.getString("type"), 
                    res.getString("address"));
                    bikes.add(bike);
                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
        }
        return bikes;
    }

    public static List<Bike> getAllBikesByStation(int stationID){
        List<Bike> bikes = new ArrayList<Bike>();
        String query = "SELECT * FROM bikes WHERE stationID = " + stationID;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
                while(res.next()){
                    Bike bike = new Bike(res.getInt("id"), res.getString("type"), 
                    res.getDouble("price"), res.getString("condition"), res.getInt("stationID"));
                    bikes.add(bike);
                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();    
            }
        return bikes;
    }

    public void repairBike(){
        String query = "UPDATE bikes SET condition = 'repaired' " + "WHERE id = " + this.id;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            int rowsAffected = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }        
    }

    public void addBike(){
        String query = "INSERT INTO bikes(type, price, condition, stationID) VALUES('" + this.type + "'," + this.price + 
        ",'" + "new" + "'," + this.stationID + ")";
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            int rowsAffected = stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    
    public void removeBike(){
        String query = "DELETE FROM bikes WHERE id = " + this.id;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            int rowsAffected = stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
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

    public void assignBikeToStation() throws SQLException{
        String query = "UPDATE bikes SET stationID = " + this.stationID + " WHERE id = " + this.id;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            int rowsAffected = stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } 
    }

    public void deallocateBikeByStation(){
        System.out.println(this.id);
        String query = "UPDATE bikes SET stationID = NULL WHERE id = " + this.id;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            int rowsAffected = stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            } 
    }
}

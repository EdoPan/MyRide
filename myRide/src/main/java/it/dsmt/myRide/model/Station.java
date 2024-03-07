package it.dsmt.myRide.model;
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

    public static List<Station> getStations(){
        List<Station> stations = new ArrayList<>();
        String query = "SELECT * FROM stations";
        
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(query)){

            while(rs.next()){
                Station station;
                station = new Station(rs.getInt("id"), rs.getString("address"), 
                rs.getInt("numberOfBikes"));
                stations.add(station);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return stations;
    }

    public static Station getStationByID(int id){
        Station station = new Station();
        String query = "SELECT * FROM stations WHERE id = " + id;
        
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            station = new Station(res.getInt("id"), res.getString("address"), 
            res.getInt("numberOfBikes"));
            res.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return station;
    }

    public void addStation(){
        String query = "INSERT INTO stations(id, address, numberOfBikes) VALUES(" + 
        this.id + ",'" + this.address + "'," + this.numberOfBikes + ")";
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            res.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    public void removeStation(){
        String query = "DELETE FROM stations WHERE id = " + this.id;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            res.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }     
        }

    public List<Bike> getBikes(){
        List<Bike> bikes = new ArrayList<Bike>();
        String query = "SELECT * FROM bikes WHERE stationID = " + this.id;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                Bike bike = new Bike(res.getInt("id"), res.getString("type"), 
                res.getDouble("price"), res.getString("condition"), res.getInt("stationID"));
                bikes.add(bike);
            }
            res.close();
        } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        return bikes;
    }

}

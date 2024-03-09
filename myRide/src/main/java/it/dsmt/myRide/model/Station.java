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

    public Station(){
    }

    public Station(String address){
        this.address = address;
    }

    public Station(int id, String address){
        this.id = id;
        this.address = address;
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

    public static List<Station> getStations(String type){
        List<Station> stations = new ArrayList<>();
        String query;
        
        if(type.equals("all")){
            query = "SELECT * FROM stations";
        }
        else{
            query = "SELECT * FROM stations a INNER JOIN bikes b ON a.id = b.stationID " +
            "WHERE b.type = '" + type + "' " +
            "GROUP BY a.id " +
            "HAVING COUNT(*) > 0";
        }    
        System.out.println(query);
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(query)){

            while(rs.next()){
                Station stationIstance;
                stationIstance = new Station(rs.getInt("id"), rs.getString("address"));
                stations.add(stationIstance);
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
            station = new Station(res.getInt("id"), res.getString("address"));
            res.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return station;
    }

    public void addStation(){
        String query = "INSERT INTO stations(address) VALUES('" + 
        this.address + "')";
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

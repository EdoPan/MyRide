package it.dsmt.myRide.model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import it.dsmt.myRide.controller.DBController;
import it.dsmt.myRide.dto.ActiveRideDTO;

public class Ride {
    private int id;
    private String startTime;
    private String endTime;
    private int bikeID;
    private String username;

    public Ride(){
        
    }

    public Ride(String startTime, int bikeID, String username){
        this.startTime = startTime;
        this.bikeID = bikeID;
        this.username = username;
    }

    public Ride(int id, String startTime, String endTime, int bikeID, String username){
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bikeID = bikeID;
        this.username = username;
    }

    public int getID(){
        return this.id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getStartTime(){
        return this.startTime;
    }
    public String getEndTime(){
        return this.endTime;
    }

    public int getBikeID(){
        return this.bikeID;
    }
    public void setBikeID(int bikeID){
        this.bikeID = bikeID;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public static Ride getRideByID(int id){
        Ride ride = new Ride();
        String query = "SELECT * FROM rides WHERE id = " + id;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            ride = new Ride(res.getInt("id"), res.getString("startTime"), res.getString("endTime"), 
            res.getInt("bikeID"), res.getString("username"));
            res.close();
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
         return ride;
    }

    public static ActiveRideDTO getActiveRide(String username){
        String query = "SELECT a.id, a.startTime, a.bikeID, b.type FROM rides a INNER JOIN bikes b ON a.bikeID = b.id  WHERE a.username = '" + username + "' AND a.endTime = 'null'";
        ActiveRideDTO activeRide = null;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            if(res.next()){
                activeRide = new ActiveRideDTO(res.getInt("id"), res.getInt("bikeID"),
                res.getString("type"),res.getString("startTime"));
                res.close();
            }
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return activeRide;
     }

    public void bookRide() throws Exception{
        String queryCheck = "SELECT * FROM rides WHERE endTime = 'null' AND username = '" + this.username + "'" ;
        String query = "INSERT INTO rides(startTime, endTime, bikeID, username) VALUES('" + 
        this.startTime + "', 'null'," + this.bikeID + ",'" + this.username + "')";
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet resCheck = stmt.executeQuery(queryCheck);
            if(resCheck.next()){
                throw new Exception("\"Ride already exists\"");
            }
            else{
                int rowsAffected = stmt.executeUpdate(query);
                return;
            }    
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
    }

    public void deleteRide(){
        String query = "DELETE FROM rides WHERE id = " + this.id;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            res.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }    
    }  

    public void endRide() throws SQLException{
        String query = "UPDATE rides " + 
                       "SET endTime = '" + this.endTime + "'" +
                       "WHERE id = " + this.id;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            int rowsAffected = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }    
    }    
}
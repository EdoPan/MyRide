package it.dsmt.myRide.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import it.dsmt.myRide.controller.DBController;

public class Ride {
    private int id;
    private LocalDate startTime;
    private LocalDate endTime;
    private int bikeID;
    private String username;

    public Ride(){
        
    }

    public Ride(int id, LocalDate startTime, LocalDate endTime, int bikeID, String username){
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

    public LocalDate getStartTime(){
        return this.startTime;
    }
    public LocalDate getEndTime(){
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

    public static Ride getRideByID(int id){
        Ride ride = new Ride();
         String query = "SELECT * FROM rides WHERE id = ?";
         
         try (Connection conn = DBController.connect();
              PreparedStatement pstmt  = conn.prepareStatement(query)){
             
             pstmt.setInt(1,id);
             ResultSet rs  = pstmt.executeQuery();
             // Understand how to handle LocalDate type in SQLite
             ride = new Ride(rs.getInt("id"), null, null, 
             rs.getInt("bikeID"), rs.getString("username"));
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
         return ride;
     }

    public void bookRide(){
        String query = "INSERT INTO rides(id, startTime, endTime) VALUES(" + 
        this.id + ",'" + this.startTime + "','" + this.endTime + "','" + this.bikeID + "','" + this.username +  "')";
        try (Connection conn = DBController.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }

    public void deleteRide(){
        String query = "DELETE FROM rides WHERE id = " + this.id;
        try (Connection conn = DBController.connect();
             Statement stmt = conn.createStatement();
            ){
                ResultSet rs = stmt.executeQuery(query);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }    
    }  

    public void extendRide(LocalDate newEndTime){
        String query = "UPDATE rides " + 
                       "SET endTime = '" + newEndTime + "'" +
                       "WHERE id = '" + this.id + "'";
        try (Connection conn = DBController.connect();
             Statement stmt = conn.createStatement();){
                ResultSet rs = stmt.executeQuery(query);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }    
    }

    public boolean isTimeExpired(){
        LocalDate currentTime = java.time.LocalDate.now();
        endTime = getEndTime();
        if (currentTime == endTime){
            return true;
        } else {
            return false;
        }
    }
    
}

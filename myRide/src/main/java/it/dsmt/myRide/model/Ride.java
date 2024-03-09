package it.dsmt.myRide.model;
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
    private String userID;

    public Ride(){
        
    }

    public Ride(int id, LocalDate startTime, LocalDate endTime, int bikeID, String userID){
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bikeID = bikeID;
        this.userID = userID;
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
        return this.userID;
    }
    public void setUserID(String userID){
        this.userID = userID;
    }

    public static Ride getRideByID(int id){
        Ride ride = new Ride();
        String query = "SELECT * FROM rides WHERE id = ?";
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            // Understand how to handle LocalDate type in SQLite
            ride = new Ride(res.getInt("id"), null, null, 
            res.getInt("bikeID"), res.getString("userID"));
            res.close();
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
         return ride;
     }

    public void bookRide(){
        String query = "INSERT INTO rides(id, bikeID, userID, startTime, endTime) VALUES(" + 
        this.id + "," + this.bikeID + "," + this.userID + ",'" + this.startTime + "','" + this.endTime + "')";
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            res.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
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

    public void extendRide(LocalDate newEndTime){
        String query = "UPDATE rides " + 
                       "SET endTime = '" + newEndTime + "'" +
                       "WHERE id = " + this.id;
        try (Statement stmt = DBController.getInstance().getConnection().createStatement();){
            ResultSet res = stmt.executeQuery(query);
            res.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }    
    }

    public boolean isTimeExpired(){
        LocalDate currentTime = java.time.LocalDate.now();
        endTime = getEndTime();
        if (currentTime.isAfter(endTime) || currentTime.isEqual(endTime)){
            return true;
        } else {
            return false;
        }
    }
    
}
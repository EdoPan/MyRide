package it.dsmt.myRide.model;
import it.dsmt.myRide.controller.DBController;
import it.dsmt.myRide.dto.ActiveRideDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rqlite.NodeUnavailableException;
import com.rqlite.Rqlite;
import com.rqlite.dto.QueryResults;

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

    public static Ride getRideByID(int id) throws NodeUnavailableException{
        String query = "SELECT * FROM rides WHERE id = " + id;
        QueryResults res = DBController.getInstance().getConnection().Query(query, Rqlite.ReadConsistencyLevel.STRONG);
        Gson gson = new Gson();
        String json = gson.toJson(res);
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        boolean check = jsonObject.getAsJsonArray("results").get(0).getAsJsonObject().has("values");
        if(check == true && !jsonObject.has("error" )){
            String startTime = jsonObject.getAsJsonArray("results")
                .get(0).getAsJsonObject()
                .getAsJsonArray("values").get(0).getAsJsonArray().get(1).getAsString();
            String endTime = jsonObject.getAsJsonArray("results")
                .get(0).getAsJsonObject()
                .getAsJsonArray("values").get(0).getAsJsonArray().get(2).getAsString();
            int bikeID = jsonObject.getAsJsonArray("results")
                .get(0).getAsJsonObject()
                .getAsJsonArray("values").get(0).getAsJsonArray().get(3).getAsInt();
            String username = jsonObject.getAsJsonArray("results")
                .get(0).getAsJsonObject()
                .getAsJsonArray("values").get(0).getAsJsonArray().get(4).getAsString();
            Ride ride = new Ride(id, startTime, endTime, bikeID, username);
            return ride;
        }
        else {
            return null;   
        }     
    }

    public static ActiveRideDTO getActiveRide(String username) throws NodeUnavailableException{
        String query = "SELECT a.id, a.startTime, a.bikeID, b.type, b.price " + 
        "FROM rides a INNER JOIN bikes b ON a.bikeID = b.id  WHERE a.username = '" + username + "' AND a.endTime = 'null'";
        QueryResults res = DBController.getInstance().getConnection().Query(query, Rqlite.ReadConsistencyLevel.STRONG);
        Gson gson = new Gson();
        String json = gson.toJson(res);
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        boolean check = jsonObject.getAsJsonArray("results").get(0).getAsJsonObject().has("values");
        if(check == true && !jsonObject.has("error" )){
            int rideID = jsonObject.getAsJsonArray("results")
                .get(0).getAsJsonObject()
                .getAsJsonArray("values").get(0).getAsJsonArray().get(0).getAsInt();          
            int bikeID = jsonObject.getAsJsonArray("results")
                .get(0).getAsJsonObject()
                .getAsJsonArray("values").get(0).getAsJsonArray().get(2).getAsInt();
            String bikeType = jsonObject.getAsJsonArray("results")
                .get(0).getAsJsonObject()
                .getAsJsonArray("values").get(0).getAsJsonArray().get(3).getAsString();
            String startTime = jsonObject.getAsJsonArray("results")
                .get(0).getAsJsonObject()
                .getAsJsonArray("values").get(0).getAsJsonArray().get(1).getAsString();
            double price = jsonObject.getAsJsonArray("results")
                .get(0).getAsJsonObject()
                .getAsJsonArray("values").get(0).getAsJsonArray().get(4).getAsDouble();
            ActiveRideDTO activeRide = new ActiveRideDTO(rideID, bikeID, bikeType, startTime, price);
            return activeRide;
        }
        return null;
     }

    public void bookRide() throws NodeUnavailableException{
        String queryCheck = "SELECT * FROM rides WHERE endTime = 'null' AND username = '" + this.username + "'" ;
        String query = "INSERT INTO rides(startTime, endTime, bikeID, username) VALUES('" + 
        this.startTime + "', 'null'," + this.bikeID + ",'" + this.username + "')";
        QueryResults res = DBController.getInstance().getConnection().Query(queryCheck, Rqlite.ReadConsistencyLevel.STRONG);
        Gson gson = new Gson();
        String json = gson.toJson(res);
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        if(!jsonObject.has("error")){
            DBController.getInstance().getConnection().Execute(query);  
        }  
    }

    public void deleteRide() throws NodeUnavailableException{
        String query = "DELETE FROM rides WHERE id = " + this.id;
        DBController.getInstance().getConnection().Execute(query);  
    }  

    public void endRide() throws NodeUnavailableException{
        String query = "UPDATE rides " + 
                       "SET endTime = '" + this.endTime + "'" +
                       "WHERE id = " + this.id;
        DBController.getInstance().getConnection().Execute(query);
  
    }    
}
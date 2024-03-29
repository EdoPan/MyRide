package it.dsmt.myRide.model;
import it.dsmt.myRide.controller.DBController;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rqlite.NodeUnavailableException;
import com.rqlite.Rqlite;
import com.rqlite.dto.QueryResults;

public class Bike {
    private int id;
    private String type;
    private double price;
    private int stationID;

    public Bike(){
    }

    public Bike(String type, double price, int stationID){
        this.type = type;
        this.price = price;
        this.stationID = stationID;
    }

    public Bike(int id, String type, double price, int stationID){
        this.id = id;
        this.type = type;
        this.price = price;
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

    public int getStationID(){
        return this.stationID;
    }

    public void setStationID(int stationID){
        this.stationID = stationID;
    }

    private static Bike parseQueryResult(JsonElement element){
        int id = element.getAsJsonArray().get(0).getAsInt();
        String type = element.getAsJsonArray().get(1).getAsString();
        double price = element.getAsJsonArray().get(2).getAsDouble();
        int stationID = element.getAsJsonArray().get(3).getAsInt();
        return new Bike(id, type, price, stationID);
    }

    public static Bike getBikeByID(int id) throws NodeUnavailableException{
        String query = "SELECT * FROM bikes WHERE id = " + id;
        QueryResults res = DBController.getInstance().getConnection().Query(query, Rqlite.ReadConsistencyLevel.STRONG);
        Gson gson = new Gson();
        String json = gson.toJson(res);
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        Bike bike = null;
        boolean check = jsonObject.getAsJsonArray("results").get(0).getAsJsonObject().has("values");
       
        if(check == true && !jsonObject.has("error")){ 
            String type = jsonObject.getAsJsonArray("results")
                .get(0).getAsJsonObject()
                .getAsJsonArray("values").get(0).getAsJsonArray().get(1).getAsString();
            double price = jsonObject.getAsJsonArray("results")
                .get(0).getAsJsonObject()
                .getAsJsonArray("values").get(0).getAsJsonArray().get(2).getAsDouble();
            boolean checkStationID = jsonObject.getAsJsonArray("results")
                .get(0).getAsJsonObject()
                .getAsJsonArray("values").get(0).getAsJsonArray().get(3).isJsonPrimitive();
            int stationID = -1;
            if(checkStationID){
                stationID = jsonObject.getAsJsonArray("results")
                    .get(0).getAsJsonObject()
                    .getAsJsonArray("values").get(0).getAsJsonArray().get(3).getAsInt();
            }       
            bike = new Bike(id, type, price, stationID);
        }
        return bike;
    }

    public static List<Bike> getAllBikes() throws NodeUnavailableException{
        String query = "SELECT * FROM bikes";
        QueryResults res = DBController.getInstance().getConnection().Query(query, Rqlite.ReadConsistencyLevel.STRONG);
        Gson gson = new Gson();
        String json = gson.toJson(res);
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        List<Bike> bikes = new ArrayList<Bike>();
        try{
            boolean check = jsonObject.getAsJsonArray("results").get(0).getAsJsonObject().has("values");
            if (!jsonObject.has("error" ) && check) {
                JsonArray values = jsonObject.getAsJsonArray("results")
                        .get(0).getAsJsonObject()
                        .getAsJsonArray("values");
                for (JsonElement row : values) {
                    bikes.add(Bike.parseQueryResult(row));
                }
            }
        } catch(Exception e){
            throw e;
        }
        return bikes;
    }

    public static List<Bike> getAllBikesByStation(int stationID) throws NodeUnavailableException{
        String query = "SELECT * FROM bikes WHERE stationID = " + stationID;
        QueryResults res = DBController.getInstance().getConnection().Query(query, Rqlite.ReadConsistencyLevel.STRONG);
        Gson gson = new Gson();
        String json = gson.toJson(res);
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        List<Bike> bikes = new ArrayList<Bike>();
        boolean check = jsonObject.getAsJsonArray("results").get(0).getAsJsonObject().has("values");
        try{
            if (!jsonObject.has("error") && check) {
                JsonArray values = jsonObject.getAsJsonArray("results")
                        .get(0).getAsJsonObject()
                        .getAsJsonArray("values");
                for (JsonElement row : values) {
                    bikes.add(Bike.parseQueryResult(row));
                }
                return bikes;
            }
        } catch(Exception e){
            throw e;
        }
        return bikes;
    }

    public void addBike() throws NodeUnavailableException{
        String query = "INSERT INTO bikes(type, price, stationID) VALUES('" + this.type + "'," + this.price + 
        "," + this.stationID + ")";
        DBController.getInstance().getConnection().Execute(query);
    }
    
    public void removeBike() throws NodeUnavailableException{
        String query = "DELETE FROM bikes WHERE id = " + this.id;
        DBController.getInstance().getConnection().Execute(query);
    }

    public void assignBikeToStation() throws NodeUnavailableException{
        String query = "UPDATE bikes SET stationID = " + this.stationID + " WHERE id = " + this.id;
        DBController.getInstance().getConnection().Execute(query);
    }

    public void deallocateBikeByStation() throws NodeUnavailableException{
        String query = "UPDATE bikes SET stationID = NULL WHERE id = " + this.id;
        DBController.getInstance().getConnection().Execute(query);
    }
}
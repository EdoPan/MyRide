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

    private static Station parseStationQueryResult(JsonElement element){
        int id = element.getAsJsonArray().get(0).getAsInt();
        String address = element.getAsJsonArray().get(1).getAsString();
        return new Station(id, address);
    }

    private static Bike parseBikeQueryResult(JsonElement element){
        int id = element.getAsJsonArray().get(0).getAsInt();
        String type = element.getAsJsonArray().get(1).getAsString();
        double price = element.getAsJsonArray().get(2).getAsDouble();
        int stationID = element.getAsJsonArray().get(3).getAsInt();
        return new Bike(id, type, price, stationID);
    }

    public static Station getStationByID(int id) throws NodeUnavailableException{
        String query = "SELECT * FROM stations WHERE id = " + id;
        QueryResults res = DBController.getInstance().getConnection().Query(query, Rqlite.ReadConsistencyLevel.STRONG);
        Gson gson = new Gson();
        String json = gson.toJson(res);
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String address = jsonObject.getAsJsonArray("results")
            .get(0).getAsJsonObject()
            .getAsJsonArray("values").get(0).getAsJsonArray().get(1).getAsString();
        Station station = new Station(id, address);
        return station;
    }

    public void addStation() throws NodeUnavailableException{
        String query = "INSERT INTO stations(address) VALUES('" +
        this.address + "')";
        DBController.getInstance().getConnection().Execute(query);
    }

    public void removeStation() throws NodeUnavailableException{
        String query = "DELETE FROM stations WHERE id = " + this.id;
        DBController.getInstance().getConnection().Execute(query);
    }

    public static List<Station> getStations(String type) throws NodeUnavailableException{
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
        QueryResults res = DBController.getInstance().getConnection().Query(query, Rqlite.ReadConsistencyLevel.STRONG);
        Gson gson = new Gson();
        String json = gson.toJson(res);
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        List<Station> stations = new ArrayList<Station>();
        try{
            if (!jsonObject.has("error")) {
                JsonArray values = jsonObject.getAsJsonArray("results")
                        .get(0).getAsJsonObject()
                        .getAsJsonArray("values");
                for (JsonElement row : values) {
                    stations.add(Station.parseStationQueryResult(row));
                }
                return stations;
            }
        } catch(Exception e){
            throw e;
        }
        return stations;
    }

    public List<Bike> getBikes() throws NodeUnavailableException{
        String query = "SELECT * FROM bikes WHERE stationID = " + this.id;
        QueryResults res = DBController.getInstance().getConnection().Query(query, Rqlite.ReadConsistencyLevel.STRONG);
        Gson gson = new Gson();
        String json = gson.toJson(res);
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        List<Bike> bikes = new ArrayList<Bike>();
        try{
            if (!jsonObject.has("error")) {
                JsonArray values = jsonObject.getAsJsonArray("results")
                        .get(0).getAsJsonObject()
                        .getAsJsonArray("values");
                for (JsonElement row : values) {
                    bikes.add(Station.parseBikeQueryResult(row));
                }
                return bikes;
            }
        } catch(Exception e){
            throw e;
        }
        return bikes;
    }
}
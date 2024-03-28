package it.dsmt.myRide.controller;
import it.dsmt.myRide.model.Bike;
import java.util.List;
import com.rqlite.NodeUnavailableException;

public class BikeController {

    public static Bike getBikeByID(int id) throws NodeUnavailableException{

        try{
            return Bike.getBikeByID(id);
        } catch (NodeUnavailableException e) {
            throw e;
        } 
    }

    public static List<Bike> getAllBikes() throws NodeUnavailableException{
        try{
            return Bike.getAllBikes();
        } catch (NodeUnavailableException e){
            throw e;
        }
    }

    public static void addBike(String type, int quantity, int stationID) throws NodeUnavailableException{
        double price; 
        switch (type) {
            case "city": price=0.25;
                break;
            case "mountain": price=0.5;
                break;
            case "road": price=0.75;
                break;
            case "tandem": price=1;
                break;    
            default: price = 1;
                break;
        }
        for(int i = 0; i < quantity; i++){
            Bike bike = new Bike(type, price, stationID);
            try{
                bike.addBike();
            } catch (NodeUnavailableException e){
                throw e;
            }
        }    
    }

    public static void removeBike(int id) throws NodeUnavailableException{
        Bike bike = Bike.getBikeByID(id);
        try{
            bike.removeBike();
        } catch (NodeUnavailableException e){
            throw e;
        }
    }

    public static void removeBikesByStation(int stationID) throws NodeUnavailableException{
        try{
            List<Bike> bikes = Bike.getAllBikesByStation(stationID);
            for (Bike bike : bikes) {
                bike.removeBike();
            }
        } catch (NodeUnavailableException e){
            throw e;
        }
    }

    public static void assignBikeToStation(int id, int stationID) throws NodeUnavailableException{
        Bike bike = Bike.getBikeByID(id);
        bike.setStationID(stationID);
        try{
            bike.assignBikeToStation();
        } catch (NodeUnavailableException e) {
            throw e;
        }
    }

    public static void deallocateBikeByStation(int bikeID) throws NodeUnavailableException{
        Bike bike = Bike.getBikeByID(bikeID);
        try{
            bike.deallocateBikeByStation();
        } catch (NodeUnavailableException e) {
            throw e;
        }
    }
}
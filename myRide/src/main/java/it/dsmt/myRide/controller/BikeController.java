package it.dsmt.myRide.controller;
import it.dsmt.myRide.dto.BikeToBeRepairedDTO;
import it.dsmt.myRide.model.Bike;
import java.util.List;

public class BikeController {

    public static Bike getBikeByID(int id) throws Exception{

        try{
            return Bike.getBikeByID(id);
        } catch (Exception e) {
            throw e;
        } 
    }

    public static List<Bike> getAllBikes() throws Exception{
        try{
            return Bike.getAllBikes();
        } catch (Exception e){
            throw e;
        }
    }

    public static List<BikeToBeRepairedDTO> getBikesToBeRepaired() throws Exception{
        try{
            return Bike.getBikesToBeRepaired();
        } catch (Exception e){
            throw e;
        }
    }

    public static void repairBike(int id) throws Exception{
        try{
            Bike.getBikeByID(id).repairBike();
        } catch (Exception e){
            throw e;
        }
    }

    public static void addBike(String type, int quantity, int stationID) throws Exception{
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
            } catch (Exception e){
                throw e;
            }
        }    
    }


    public static void removeBike(int id) throws Exception{
        Bike bike = Bike.getBikeByID(id);
        try{
            bike.removeBike();
        } catch (Exception e){
            throw e;
        }
    }

    public static void removeBikesByStation(int stationID) throws Exception{
        try{
            List<Bike> bikes = Bike.getAllBikesByStation(stationID);
            for (Bike bike : bikes) {
                bike.removeBike();
            }
        } catch (Exception e){
            throw e;
        }
    }

    public static void markCondition(int id, String condition) throws Exception{
        Bike bike = Bike.getBikeByID(id);
        try{
            bike.markCondition();
        } catch (Exception e){
            throw e;
        }
    }

    public static void assignBikeToStation(int id, int stationID){
        Bike bike = Bike.getBikeByID(id);
        try{
            bike.assignBikeToStation();
        } catch (Exception e) {
            throw e;
        }
    }
}

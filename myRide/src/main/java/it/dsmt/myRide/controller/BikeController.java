package it.dsmt.myRide.controller;
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

    public static List<Bike> getBikes() throws Exception{
        try{
            return Bike.getBikes();
        } catch (Exception e){
            throw e;
        }
    }

    public static void repairBike(int id) throws Exception{
        try{
            Bike.repairBike(id);
        } catch (Exception e){
            throw e;
        }
    }

    public static void addBike(int id, String type, double price, String condition) throws Exception{
        Bike bike = new Bike(id, type, price, condition);
        try{
            bike.addBike();
        } catch (Exception e){
            throw e;
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

    public static void markCondition(int id, String condition) throws Exception{
        Bike bike = Bike.getBikeByID(id);
        try{
            bike.markCondition();
        } catch (Exception e){
            throw e;
        }
    }
}

package it.dsmt.myRide.controller;
import java.time.LocalDate;

import it.dsmt.myRide.model.Ride;

public class RideController {

    public static Ride getRideByID(int id) throws Exception{
        try{
            return Ride.getRideByID(id);
        } catch (Exception e){
            throw e;
        }
    }

    public static void bookRide() throws Exception{
        Ride ride = new Ride();
        try{
            ride.bookRide();
        } catch (Exception e){
            throw e;
        }
    }

    public static void deleteRide(int id) throws Exception{
        Ride ride = Ride.getRideByID(id);
        try{
            ride.deleteRide();
        } catch (Exception e){
            throw e;
        }
    }
    
    public static void extendRide(int id, LocalDate newEndTime) throws Exception{
        Ride ride = Ride.getRideByID(id);
        try{
            ride.extendRide(newEndTime);
        } catch (Exception e){
            throw e;
        }
    }    
    
}

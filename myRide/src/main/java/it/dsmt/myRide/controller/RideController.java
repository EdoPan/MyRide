package it.dsmt.myRide.controller;
import it.dsmt.myRide.dto.ActiveRideDTO;
import it.dsmt.myRide.model.Bike;
import it.dsmt.myRide.model.Ride;

public class RideController {

    public static Ride getRideByID(int id) throws Exception{
        try{
            return Ride.getRideByID(id);
        } catch (Exception e){
            throw e;
        }
    }

    public static ActiveRideDTO getActiveRide(String username) throws Exception{
        try{
            return Ride.getActiveRide(username);
        } catch (Exception e){
            throw e;
        }
    }

    public static void bookRide(int bikeID, String username) throws Exception{
        Ride ride = new Ride(java.time.LocalDateTime.now().toString(), bikeID, username);
        Bike bike = Bike.getBikeByID(bikeID);
        try{
            ride.bookRide();
            bike.deallocateBikeByStation();
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
    
    public static void endRide(int rideID, int stationID) throws Exception{
        Ride ride = Ride.getRideByID(rideID);
        System.out.println("station:" + stationID);
        ride.setEndTime(java.time.LocalDateTime.now().toString());
        Bike bike = Bike.getBikeByID(ride.getBikeID());
        bike.setStationID(stationID);
        try{
            ride.endRide();
            System.out.println("DEBUGGIN");
            bike.assignBikeToStation();
            System.out.println("DEBUGGIN2");
        } catch (Exception e){
            throw e;
        }
    }    
    
}

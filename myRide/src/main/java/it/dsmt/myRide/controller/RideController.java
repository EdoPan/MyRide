package it.dsmt.myRide.controller;
import it.dsmt.myRide.dto.ActiveRideDTO;
import it.dsmt.myRide.model.Bike;
import it.dsmt.myRide.model.Ride;
import com.rqlite.NodeUnavailableException;

public class RideController {

    public static Ride getRideByID(int id) throws NodeUnavailableException{
        try{
            return Ride.getRideByID(id);
        } catch (NodeUnavailableException e){
            throw e;
        }
    }

    public static ActiveRideDTO getActiveRide(String username) throws NodeUnavailableException{
        try{
            return Ride.getActiveRide(username);
        } catch (NodeUnavailableException e){
            throw e;
        }
    }

    public static void bookRide(int bikeID, String username) throws NodeUnavailableException{
        Ride ride = new Ride(java.time.LocalDateTime.now().toString(), bikeID, username);
        Bike bike = Bike.getBikeByID(bikeID);
        try{
            ride.bookRide();
            bike.deallocateBikeByStation();
        } catch (NodeUnavailableException e){
            throw e;
        }
    }

    public static void deleteRide(int id) throws NodeUnavailableException{
        Ride ride = Ride.getRideByID(id);
        try{
            ride.deleteRide();
        } catch (NodeUnavailableException e){
            throw e;
        }
    }
    
    public static void endRide(int rideID, int stationID) throws NodeUnavailableException{
        Ride ride = Ride.getRideByID(rideID);
        ride.setEndTime(java.time.LocalDateTime.now().toString());
        Bike bike = Bike.getBikeByID(ride.getBikeID());
        bike.setStationID(stationID);
        try{
            ride.endRide();
            bike.assignBikeToStation();
        } catch (NodeUnavailableException e){
            throw e;
        }
    }    
}
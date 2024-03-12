package it.dsmt.myRide.dto;

public class EndRideDTO {
    private int rideID;
    private int stationID;
    
    public EndRideDTO(int rideID, int stationID){
        this.rideID = rideID;
        this.stationID = stationID;
    }

    public int getRideID(){
        return this.rideID;
    }

    public int getStationID(){
        return this.stationID;
    }

    public void setRideID(int rideID){
        this.rideID = rideID;
    }

    public void setStationID(int stationID){
        this.stationID = stationID;
    }
}

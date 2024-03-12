package it.dsmt.myRide.dto;

public class ActiveRideDTO {
    private int rideID;
    private int bikeID;
    private String bikeType;
    private String startTime;
    private double price;
    
    public ActiveRideDTO(int rideID, int bikeID, String bikeType, String startTime, double price){
        this.rideID = rideID;
        this.bikeID = bikeID;
        this.bikeType = bikeType;
        this.startTime = startTime;
        this.price = price;
    }

    public int getRideID(){
        return this.rideID;
    }

    public int getBikeID(){
        return this.bikeID;
    }

    public String getBikeType(){
        return this.bikeType;
    }
    
    public String getStartTime(){
        return this.startTime;
    }

    public double getPrice(){
        return this.price;
    }

    public void setRideID(int rideID){
        this.rideID = rideID;
    }

    public void setBikeID(int bikeID){
        this.bikeID = bikeID;
    }

    public void setBikeType(String bikeType){
        this.bikeType = bikeType;
    }  
    
    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public void setPrice(double price){
        this.price = price;
    }  
}

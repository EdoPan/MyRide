package it.dsmt.myRide.dto;

public class RideDTO {
    private int bikeID;
    private String username;
    
    public RideDTO(int bikeID, String username){
        this.bikeID = bikeID;
        this.username = username;
    }

    public int getBikeID(){
        return this.bikeID;
    }

    public String getUsername(){
        return this.username;
    }
    
    public void setBikeID(int bikeID){
        this.bikeID = bikeID;
    }

    public void setUsername(String username){
        this.username = username;
    }    
}
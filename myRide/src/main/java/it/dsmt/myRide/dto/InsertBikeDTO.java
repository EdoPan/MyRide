package it.dsmt.myRide.dto;

public class InsertBikeDTO {
    private String type;
    private int quantity;
    private int stationID;
 

    public InsertBikeDTO(String type, int quantity, int stationID){
        this.type = type;
        this.quantity = quantity;
        this.stationID = stationID;
    }

    public String getType(){
        return this.type;
    }

    public int getQuantity(){
        return this.quantity;
    }
    
    public int getStationID(){
        return this.stationID;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setStationID(int stationID){
        this.stationID = stationID;
    }
}

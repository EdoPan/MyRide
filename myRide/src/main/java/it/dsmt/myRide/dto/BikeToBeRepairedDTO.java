package it.dsmt.myRide.dto;

public class BikeToBeRepairedDTO {
    private int id;
    private String type;
    private String address;

    public BikeToBeRepairedDTO(int id, String type, String address){
        this.id = id;
        this.type = type;
        this.address = address;
    }

    public int getId(){
        return this.id;
    }

    public String getType(){
        return this.type;
    }

    public String getAddress(){
        return this.address;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setAddress(String address){
        this.address = address;
    }
}

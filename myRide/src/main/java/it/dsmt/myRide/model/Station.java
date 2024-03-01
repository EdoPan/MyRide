package it.dsmt.myRide.model;
import java.util.List;

public class Station {
    private int id;
    private String address;
    private int numberOfBikes;
    private List<Bike> availableBikes;

    public Station(int id, String address, int numberOfBikes, List<Bike> availableBikes){
        this.address = address;
        this.numberOfBikes = numberOfBikes;
        this.availableBikes = availableBikes;
    }

    public int getID(){
        return this.id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getAddress(){
        return this.address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public int getNumberOfBikes(){
        return this.numberOfBikes;
    }

    public void setNumberOfBikes(int numberOfBikes){
        this.numberOfBikes = numberOfBikes;
    }

    public List<Bike> getAvailableBikes(){
        return this.availableBikes;
    }

    public void setAvailableBikes(List<Bike> bikes){
        this.availableBikes = bikes;
    }

    public static Station getStationByID(int id){
    }

    public void addStation(){
        // Handle the DB
    }

    public void removeStation(){
        // Handle the DB
    }

    public void addBikeToStation(Bike bike){
        // Handle the DB
    }

}

package it.dsmt.myRide.model;
import java.util.List;

public class Bike {
    private int id;
    private String type;
    private double price;
    private String condition;

    public Bike(){
    }

    public Bike(int id, String type, double price, String condition){
        this.id = id;
        this.type = type;
        this.price = price;
        this.condition = condition;
    }

    public int getID(){
        return this.id;
    }
    public void setID(int id){
        this.id = id;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    public double getPrice(){
        return this.price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public String getCondition(){
        return this.condition;
    }

    public void setCondition(String condition){
        this.condition = condition;
    }

    public static Bike getBikeByID(int id){
        //Handle DB
    }

    public static List<Bike> getBikes(){
        // Handle the DB
    }

    public static void repairBike(int id){
        // Handle the DB
    }

    public void addBike(){
        // Handle the DB
    }
    
    public void removeBike(){
        // Handle the DB
    }

    public void markCondition(){

    }
}

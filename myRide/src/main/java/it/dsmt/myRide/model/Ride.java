package it.dsmt.myRide.model;
import java.time.LocalDate;

public class Ride {
    private int id;
    private LocalDate startTime;
    private LocalDate endTime;

    public Ride(){
        
    }

    public Ride(int id, LocalDate startTime, LocalDate endTime){
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getID(){
        return this.id;
    }

    public void setID(int id){
        this.id = id;
    }

    public LocalDate getStartTime(){
        return this.startTime;
    }
    public LocalDate getEndTime(){
        return this.endTime;
    }

    public void bookRide(){
    }

    public void deleteRide(){
    }  

    public void extendRide(){
    }

    public boolean isTimeExpired(){
        LocalDate currentTime = java.time.LocalDate.now();
        endTime = getEndTime();
        if (currentTime == endTime){
            return true;
        } else {
            return false;
        }
    }

    public static Ride getRideByID(int id){

    }
    
}

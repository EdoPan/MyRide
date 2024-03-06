package it.dsmt.myRide;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import it.dsmt.myRide.controller.DBController;

@SpringBootApplication
public class MyRideApplication {
    public static void main(String[] args) {
      DBController.getInstance();
      SpringApplication.run(MyRideApplication.class, args);
    }
}
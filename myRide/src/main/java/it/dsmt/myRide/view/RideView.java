package it.dsmt.myRide.view;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import it.dsmt.myRide.controller.RideController;
import it.dsmt.myRide.model.Ride;

public class RideView {
    
    @GetMapping("/ride/{id_ride}")
    public ResponseEntity<Ride> getRideByID(@RequestParam int id){
        Ride response;
        try{
            response = RideController.getRideByID(id);
        } catch (Exception e){
            System.out.println("[RIDE VIEW] Impossible to fetch the ride");
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/ride/book")
    public ResponseEntity<String> bookRide(){
        try{
            RideController.bookRide();;
        } catch (Exception e){
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Ride booked!", HttpStatus.OK);
    }

    @PostMapping("/ride/{id_ride}/delete")
    public ResponseEntity<String> deleteRide(@RequestParam int id){
        try{
            RideController.deleteRide(id);;
        } catch (Exception e){
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Ride deleted!", HttpStatus.OK);
    }

    @PostMapping("/ride/{id_ride}/extend")
    public ResponseEntity<String> extendRide(@RequestParam int id){
        try{
            RideController.extendRide(id);;
        } catch (Exception e){
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Ride extended!", HttpStatus.OK);
    }
}

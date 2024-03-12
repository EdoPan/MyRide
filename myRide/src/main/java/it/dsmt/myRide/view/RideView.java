package it.dsmt.myRide.view;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import it.dsmt.myRide.controller.RideController;
import it.dsmt.myRide.model.Ride;
import it.dsmt.myRide.dto.RideDTO;
import it.dsmt.myRide.dto.ActiveRideDTO;
import it.dsmt.myRide.dto.EndRideDTO;

@Controller
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

    @GetMapping("/ride/active/{username}")
    public ResponseEntity<ActiveRideDTO> getActiveRide(@PathVariable("username") String username){
       ActiveRideDTO response;
        try{
            response = RideController.getActiveRide(username);
        } catch (Exception e){
            System.out.println("Impossible to fetch the active ride");
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/ride")
    public ResponseEntity<String> bookRide(@RequestBody RideDTO ride) throws Exception{
        try{
            RideController.bookRide(ride.getBikeID(), ride.getUsername());
        } catch (Exception e){
            return new ResponseEntity<>("ERROR: A ride already exists" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Ride booked!", HttpStatus.OK);
    }

    @DeleteMapping("/ride/{id_ride}")
    public ResponseEntity<String> deleteRide(@RequestParam int id){
        try{
            RideController.deleteRide(id);;
        } catch (Exception e){
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Ride deleted!", HttpStatus.OK);
    }

    @PostMapping("/ride/end")
    public ResponseEntity<String> endRide(@RequestBody EndRideDTO ride){
        try{
            RideController.endRide(ride.getRideID(),ride.getStationID());
        } catch (Exception e){
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Ride ended!", HttpStatus.OK);
    }
}

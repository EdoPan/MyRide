package it.dsmt.myRide.view;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import it.dsmt.myRide.controller.BikeController;
import it.dsmt.myRide.model.Bike;
import java.util.List;

@Controller
public class BikeView {

    @GetMapping("/bike/{id_bike}")
    public ResponseEntity<Bike> getBikeByID(@RequestParam int id){
        Bike response;
        try{
            response = BikeController.getBikeByID(id);
        } catch (Exception e){
            System.out.println("[BIKE VIEW] Impossible to fetch the bike");
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bikes")
    public ResponseEntity<List<Bike>> getBikes(){
        List<Bike> response;
        try{
            response = BikeController.getAllBikes();
        } catch (Exception e){
            System.out.println("[BIKE VIEW] Impossible to fetch all bikes");
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bike/{id_bike}")
    public ResponseEntity<String> repairBike(@RequestParam int id){
        try{
            BikeController.repairBike(id);
        } catch (Exception e){
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Bike repaired!", HttpStatus.OK);
    }

    @PostMapping("/bike")
    public ResponseEntity<String> addBike(@RequestParam Bike bike){
        try{
            BikeController.addBike(bike.getID(), bike.getType(), bike.getPrice(), bike.getCondition(), bike.getStationID());
        } catch (Exception e){
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Bike added!", HttpStatus.OK);
    }

    @PostMapping("/bike/{id_bike}/remove")
    public ResponseEntity<String> removeBike(@RequestParam int id){
        try{
            BikeController.removeBike(id);
        } catch (Exception e){
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Bike removed!", HttpStatus.OK);
    }

    @PostMapping("/bike/{id_bike}/mark/{condition}")
    public ResponseEntity<String> markCondition(@RequestParam int id, String condition){
        try{
            BikeController.markCondition(id, condition);
        } catch (Exception e){
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Bike marked!", HttpStatus.OK);
    }

    @PostMapping("/bike/{id_bike}/station/{station_id}")
    public ResponseEntity<String> assignBikeToStation(@RequestParam int id, int stationID){
        try{
            BikeController.assignBikeToStation(id, stationID);
        } catch (Exception e){
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Bike assigned!", HttpStatus.OK);
    }    
}

package it.dsmt.myRide.view;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import it.dsmt.myRide.controller.BikeController;
import it.dsmt.myRide.dto.InsertBikeDTO;
import it.dsmt.myRide.model.Bike;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/bike")
    public ResponseEntity<String> addBike(@RequestBody InsertBikeDTO bike){
        try{
            BikeController.addBike(bike.getType(), bike.getQuantity(), bike.getStationID());
        } catch (Exception e){
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Bike added!", HttpStatus.OK);
    }

    @DeleteMapping("/bike/{id_bike}")
    public ResponseEntity<String> removeBike(@PathVariable("id_bike") int id){
        try{
            BikeController.removeBike(id);
        } catch (Exception e){
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Bike removed!", HttpStatus.OK);
    }

    @DeleteMapping("/bikes/station/{id_station}")
    public ResponseEntity<String> removeBikesByStation(@PathVariable("id_station") int stationID){
        try{
            BikeController.removeBikesByStation(stationID);
        } catch (Exception e){
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("All bikes removed!", HttpStatus.OK);
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
    
    @PostMapping("/bike/{id_bike}/station")
    public ResponseEntity<String> deallocateBikeByStation(@PathVariable("id_bike") int bikeID){
        try{
            BikeController.deallocateBikeByStation(bikeID);
        } catch (Exception e){
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Bike deallocated!", HttpStatus.OK);
    }  
}

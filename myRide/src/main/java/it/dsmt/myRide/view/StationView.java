package it.dsmt.myRide.view;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import it.dsmt.myRide.controller.BikeController;
import it.dsmt.myRide.controller.StationController;
import it.dsmt.myRide.dto.InsertBikeDTO;
import it.dsmt.myRide.model.Station;

import java.util.List;
import java.util.Map;


@Controller
public class StationView {

    @GetMapping("/station/{id_station}")
    public ResponseEntity<Station> getStationByID(@PathVariable("id_station") int id){
        Station response;
        try{
            response = StationController.getStationByID(id);
        } catch (Exception e){
            System.out.println("[STATION VIEW] Impossible to fetch the station");
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stations/{type}")
    public ResponseEntity<List<Station>> getStations(@PathVariable("type") String type){
        List<Station> response;
        try{
            response = StationController.getStations(type);
        } catch (Exception e){
            System.out.println("[STATION VIEW] Impossible to fetch stations");
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/station")
    public ResponseEntity<String> addStation(@RequestBody Station station){
        try{
            StationController.addStation(station.getAddress());
        } catch (Exception e){
            return new ResponseEntity("ERROR", null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity("Station inserted",null, HttpStatus.OK);
    }

    @PostMapping("/station/{id_station}/remove")
    public ResponseEntity<String> removeStation(@RequestParam int id){
        try{
            StationController.removeStation(id);
        } catch (Exception e){
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<>("Station removed!", HttpStatus.OK);
    }

    @GetMapping("/station/{id_station}/bikes")
    public ResponseEntity<String> getBikes(@RequestParam int stationID){
        try{
            StationController.getBikes(stationID);
        } catch (Exception e){
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<>("Bikes listed!", HttpStatus.OK);
    }
    
}

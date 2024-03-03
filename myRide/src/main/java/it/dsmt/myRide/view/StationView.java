package it.dsmt.myRide.view;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import it.dsmt.myRide.controller.StationController;
import it.dsmt.myRide.model.Station;;;

public class StationView {

    @GetMapping("/station/{id_station}")
    public ResponseEntity<Station> getStationByID(@RequestParam int id){
        Station response;
        try{
            response = StationController.getStationByID(id);
        } catch (Exception e){
            System.out.println("[STATION VIEW] Impossible to fetch the station");
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/station")
    public ResponseEntity<String> addStation(@RequestParam Station station){
        try{
            StationController.addStation(station. getID(), station.getAddress(), station.getNumberOfBikes());
        } catch (Exception e){
            return new ResponseEntity<>("ERROR" , HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<>("Station added!", HttpStatus.OK);
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

package it.dsmt.myRide.controller;
import it.dsmt.myRide.model.Station;
import java.util.List;
import com.rqlite.NodeUnavailableException;

public class StationController {

    public static List<Station> getStations(String type) throws NodeUnavailableException{
        try{
            return Station.getStations(type);
        } catch (NodeUnavailableException e){
            throw e;
        }
    }

    public static Station getStationByID(int id) throws NodeUnavailableException{
        try{
            return Station.getStationByID(id);
        } catch (NodeUnavailableException e){
            throw e;
        }
    }

    public static void addStation(String address) throws NodeUnavailableException{
        Station station = new Station(address);
        try{
            station.addStation();
        } catch (NodeUnavailableException e){
            throw e;
        }
    }

    public static void removeStation(int id) throws NodeUnavailableException{
        Station station = Station.getStationByID(id);
        try{
            station.removeStation();
        } catch (NodeUnavailableException e){
            throw e;
        }
    }

    public static void getBikes(int id) throws NodeUnavailableException{
        Station station = Station.getStationByID(id);
        try{
            station.getBikes();
        } catch (NodeUnavailableException e){
            throw e;
        }
    }
}
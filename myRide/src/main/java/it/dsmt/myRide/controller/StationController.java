package it.dsmt.myRide.controller;
import it.dsmt.myRide.model.Station;
import java.util.List;

public class StationController {

    public static List<Station> getStations(String type) throws Exception{
        try{
            return Station.getStations(type);
        } catch (Exception e){
            throw e;
        }
    }

    public static Station getStationByID(int id) throws Exception{
        try{
            return Station.getStationByID(id);
        } catch (Exception e){
            throw e;
        }
    }

    public static void addStation(String address) throws Exception{
        Station station = new Station(address);
        try{
            station.addStation();
        } catch (Exception e){
            throw e;
        }
    }

    public static void removeStation(int id) throws Exception{
        Station station = Station.getStationByID(id);
        try{
            station.removeStation();
        } catch (Exception e){
            throw e;
        }
    }

    public static void getBikes(int id) throws Exception{
        Station station = Station.getStationByID(id);
        try{
            station.getBikes();
        } catch (Exception e){
            throw e;
        }
    }
}

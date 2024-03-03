package it.dsmt.myRide.controller;
import it.dsmt.myRide.model.Station;


public class StationController {

    public static Station getStationByID(int id) throws Exception{
        try{
            return Station.getStationByID(id);
        } catch (Exception e){
            throw e;
        }
    }

    public static void addStation(int id, String address, int numberOfBikes) throws Exception{
        Station station = new Station(id, address, numberOfBikes);
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

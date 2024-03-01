package it.dsmt.myRide.controller;
import it.dsmt.myRide.model.Bike;
import it.dsmt.myRide.model.Station;

import java.util.List;

public class StationController {

    public static Station getStationByID(int id) throws Exception{
        try{
            return Station.getStationByID(id);
        } catch (Exception e){
            throw e;
        }
    }

    public static void addStation(int id, String address, int numberOfBikes, List<Bike> availableBikes) throws Exception{
        Station station = new Station(id, address, numberOfBikes, availableBikes);
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

    public static void addBikeToStation(int stationID, int bikeID) throws Exception{
        Station station = Station.getStationByID(stationID);
        Bike bike = Bike.getBikeByID(bikeID);
        try{
            station.addBikeToStation(bike);
        } catch (Exception e){
            throw e;
        }
    }

    public static void getAvailableBikes(int id) throws Exception{
        Station station = Station.getStationByID(id);
        try{
            station.getAvailableBikes();
        } catch (Exception e){
            throw e;
        }
    }
}

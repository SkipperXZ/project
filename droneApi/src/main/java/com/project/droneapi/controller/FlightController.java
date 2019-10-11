package com.project.droneapi.controller;

import com.project.droneapi.model.*;
import com.project.droneapi.payload.FlightRespond;
import com.project.droneapi.payload.UploadFileResponse;
import com.project.droneapi.repository.DroneRouteRopository;
import com.project.droneapi.service.DroneRouteService;
import com.project.droneapi.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FlightController {

    @Autowired
    FlightService flightService;
    @Autowired
    DroneRouteService droneRouteService;

    @PostMapping("/flight")
    @ResponseBody
    public FlightRespond createNewFlight (
             @RequestParam String flightName
            ,@RequestParam String deviceID
            ,@RequestParam String userID
            ,@RequestParam List<Double> latitudeList
            ,@RequestParam List<Double> longitudeList
            ,@RequestParam String timeStamp
    ){

        Flight flight = new Flight();
        flight.setFlightName(flightName);
        flight.setDeviceID(deviceID);
        flight.setUserID(userID);
        flight.setTimeStamp(timeStamp);

        String flightID = flightService.createNewFlight(flight).getFlightID();

        for (int i = 0; i < latitudeList.size(); i++) {

            DroneRoute droneRoute = new DroneRoute();
            droneRoute.setFlightID(flightID);
            droneRoute.setRoutePointNumber(i);
            droneRoute.setRoutePointLatitude(latitudeList.get(i));
            droneRoute.setRoutePointLongitude(longitudeList.get(i));
            droneRouteService.createNewRoutePoint(droneRoute);

        }


        return new FlightRespond(flightID,flightName,deviceID,userID,latitudeList,longitudeList,timeStamp);

    }
    @GetMapping("/getFlight")
    @ResponseBody
    public  FlightRespond getAllImageUrlFromMarker(@RequestParam String flightName,@RequestParam String userID){
        Flight flight = flightService.getFlight(flightName,userID);

        if(flight != null) {
            String flightID = flight.getFlightID();
            String timeStamp = flight.getTimeStamp();
            List<DroneRoute> droneRoutes = droneRouteService.getDroneRoute(flightID);
            List<Double> latitudeList = new ArrayList<>();
            List<Double> longitudeList = new ArrayList<>();

            for (DroneRoute e : droneRoutes) {
                latitudeList.add(e.getRoutePointNumber(), e.getRoutePointLatitude());
                longitudeList.add(e.getRoutePointNumber(), e.getRoutePointLongitude());
            }
            FlightRespond flightRespond = new FlightRespond(flightID, flightName, flight.getDeviceID(), flight.getUserID(), latitudeList, longitudeList, flight.getTimeStamp());

            return flightRespond;
        }
        return null;


    }

}

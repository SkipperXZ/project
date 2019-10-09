package com.project.droneapi.service;

import com.project.droneapi.model.DroneRoute;
import com.project.droneapi.model.Flight;
import com.project.droneapi.repository.DroneRouteRopository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneRouteService {
    @Autowired
    private DroneRouteRopository droneRouteRopository;

    public DroneRoute createNewRoutePoint(DroneRoute droneRoute){
        return droneRouteRopository.save(droneRoute);
    }
    public List<DroneRoute> getDroneRoute(String flightID){
        return droneRouteRopository.findAllByFlightID(flightID);
    }
}

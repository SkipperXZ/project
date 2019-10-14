package com.project.droneapi.service;

import com.project.droneapi.model.Flight;
import com.project.droneapi.repository.DroneRouteRopository;
import com.project.droneapi.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {
    @Autowired
    FlightRepository flightRepository;

    public Flight createNewFlight(Flight flight){
        return flightRepository.save(flight);
    }

    public Flight getFlight(String flightName,String userID){
        return flightRepository.findFirstByFlightNameAndUserID(flightName,userID);
    }

    public List<Flight> getAllFlight(String userID){
        return flightRepository.findAllByUserID(userID);
    }

}

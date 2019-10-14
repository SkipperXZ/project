package com.project.droneapi.repository;

import com.project.droneapi.model.Flight;
import com.project.droneapi.model.ImageDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends CrudRepository<Flight,String> {
    Flight findFirstByFlightNameAndUserID(String flightName, String userID);
    List<Flight> findAllByUserID(String userID);

}

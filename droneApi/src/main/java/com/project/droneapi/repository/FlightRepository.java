package com.project.droneapi.repository;

import com.project.droneapi.model.Flight;
import com.project.droneapi.model.ImageDetail;
import org.springframework.data.repository.CrudRepository;

import java.nio.channels.FileLock;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends CrudRepository<Flight,String> {
    Flight findFirstByFlightNameAndUserID(String flightName, String userID);
    List<Flight> findAllByUserID(String userID);
    Flight findByDeviceIDAndStatusCode(String deviceID,int statusCode);
    Flight findByFlightID(String flightID);
    List<Flight> findAllByUserIDAndStatusCode(String userID,int statusCode);

}

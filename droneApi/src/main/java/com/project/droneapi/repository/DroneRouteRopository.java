package com.project.droneapi.repository;

import com.project.droneapi.model.DroneRoute;
import com.project.droneapi.model.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DroneRouteRopository extends CrudRepository<DroneRoute,String> {

    List<DroneRoute> findAllByFlightID(String flightID);
}

package com.project.droneapi.repository;

import com.project.droneapi.model.ImageDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.awt.*;
import java.util.List;

public interface ImageDetailRepository extends CrudRepository<ImageDetail, String> {
    List<ImageDetail> findAllByUserIDAndMarkerIDAndFlightID(String userID,String markerID,String flightID);

    ImageDetail findFirstByMarkerID(String markerID);

    ImageDetail findFirstByMarkerIDAndFlightID(String markerID,String flightID);

}

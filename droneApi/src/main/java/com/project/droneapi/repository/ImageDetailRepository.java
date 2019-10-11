package com.project.droneapi.repository;

import com.project.droneapi.model.ImageDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.awt.*;
import java.util.List;

public interface ImageDetailRepository extends CrudRepository<ImageDetail, String> {
    List<ImageDetail> findAllByUserIDAndMarkerID(String userID,String markerID);

    ImageDetail findFirstByMarkerID(String markerID);

}

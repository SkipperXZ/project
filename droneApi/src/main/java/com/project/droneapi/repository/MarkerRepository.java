package com.project.droneapi.repository;

import com.project.droneapi.model.ImageDetail;
import com.project.droneapi.model.Marker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MarkerRepository extends CrudRepository<Marker, String> {
    List<Marker> findByUserID(String userID);
    Marker findByUserIDAndMarkerLatAndMarkerLon(String userID,double markerLat,double markerLon);
}

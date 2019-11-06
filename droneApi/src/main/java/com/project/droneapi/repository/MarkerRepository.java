package com.project.droneapi.repository;

import com.project.droneapi.model.ImageDetail;
import com.project.droneapi.model.Marker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MarkerRepository extends CrudRepository<Marker, String> {
    List<Marker> findByUserID(String userID);
    Marker findByUserIDAndMarkerLatAndMarkerLon(String userID,double markerLat,double markerLon);

    @Query(value = "SELECT m.* FROM appdatabase.marker AS m INNER JOIN image_detail AS i ON m.id = i.marker_id WHERE i.flight_id = ?1 GROUP BY m.id",
            nativeQuery = true)
    List<Marker> findMarkerIDByFlightID(String flightID);
}

package com.project.droneapi.service;

import com.project.droneapi.model.Marker;
import com.project.droneapi.repository.MarkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkerService {

    private MarkerRepository repository;

    @Autowired
    public MarkerService(MarkerRepository repository) {
        this.repository = repository;
    }

    public List<Marker> findByUserID(String userID) {
        return repository.findByUserID(userID);
    }

    public Marker findMarkerExist(String userID,double markerLat , double  markerLon){
        return repository.findByUserIDAndMarkerLatAndMarkerLon(userID,markerLat,markerLon);
    }
    public Marker createNewMarker(Marker marker) {
        return repository.save(marker);

    }

}

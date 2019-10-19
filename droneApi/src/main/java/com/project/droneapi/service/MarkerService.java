package com.project.droneapi.service;

import com.project.droneapi.model.Marker;
import com.project.droneapi.payload.MarkerResponse;
import com.project.droneapi.repository.MarkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarkerService {

    private MarkerRepository repository;

    @Autowired
    private ImageDetailService imageDetailService;

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

    public List<MarkerResponse> getMarkerResponseByFlightID(String flightID){
        List<Marker> markers = repository.findMarkerIDByFlightID(flightID);
        List<MarkerResponse>  markerResponses = new ArrayList<>();
        for (Marker m:
                markers) {
            MarkerResponse markerResponse = new MarkerResponse();
            markerResponse.setId(m.getId());
            markerResponse.setMarkerLat(m.getMarkerLat());
            markerResponse.setMarkerLon(m.getMarkerLon());
            markerResponse.setUserID(m.getUserID());
            markerResponse.setIcon(imageDetailService.getFirstImageNameByMarker(m.getId()));
            markerResponses.add(markerResponse);
        }
        return markerResponses;
    };

}

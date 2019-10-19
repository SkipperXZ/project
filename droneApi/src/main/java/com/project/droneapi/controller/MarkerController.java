package com.project.droneapi.controller;

import com.project.droneapi.model.DBFile;
import com.project.droneapi.model.ImageDetail;
import com.project.droneapi.model.Marker;
import com.project.droneapi.payload.MarkerResponse;
import com.project.droneapi.service.ImageDetailService;
import com.project.droneapi.service.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/marker")
public class MarkerController {
    @Autowired
    private MarkerService markerService;

    @Autowired
    private ImageDetailService imageDetailService;

    @GetMapping("/getMarkerFromUser")
    @ResponseBody
    public List<MarkerResponse> get(@RequestParam String userID) {
        List<Marker> markers = markerService.findByUserID(userID);
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
    }

    @GetMapping("/getMarkerByFlightID")
    @ResponseBody
    public List<MarkerResponse>getAllMarkerByFlightID(@RequestParam String flightID){
        System.out.println(flightID);

        return markerService.getMarkerResponseByFlightID(flightID);
    }

}

package com.project.droneapi.controller;

import com.project.droneapi.model.DBFile;
import com.project.droneapi.model.Marker;
import com.project.droneapi.service.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/getMarkerFromUser")
public class MarkerController {
    @Autowired
    private MarkerService markerService;

    @GetMapping()
    @ResponseBody
    public List<Marker> get(@RequestParam String userID) {
        List<Marker> markers = markerService.findByUserID(userID);
        return markers;
    }
}

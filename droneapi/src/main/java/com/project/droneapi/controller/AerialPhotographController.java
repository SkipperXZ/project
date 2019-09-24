package com.project.droneapi.controller;

import com.project.droneapi.model.AerialPhotograph;
import com.project.droneapi.service.AerialPhotographService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/ap")
public class AerialPhotographController {

    @Autowired
    private AerialPhotographService aps;

    @GetMapping
    public ResponseEntity<?> getAllAP() {
        List<AerialPhotograph> aerialPhotographs = aps.retrieveCustomers();
        return ResponseEntity.ok(aerialPhotographs);
    }

    @PostMapping()
    public ResponseEntity<?> postAP(@RequestBody AerialPhotograph body) {
        AerialPhotograph ap = aps.createNewAP(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(ap);
    }

}

package com.project.droneapi.controller;

import com.project.droneapi.model.APDetail;
import com.project.droneapi.model.AerialPhotograph;
import com.project.droneapi.service.APDetailService;
import com.project.droneapi.service.AerialPhotographService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/postAPDetail")
public class APDetailController {
    @Autowired
    private APDetailService apDetailService;

    @GetMapping
    public ResponseEntity<?> getAllAP() {
        List<APDetail> apDetails = apDetailService.retrieveCustomers();
        return ResponseEntity.ok(apDetails);
    }

    @PostMapping()
    public ResponseEntity<?> postAP(@RequestBody APDetail body) {
        APDetail apDetail = apDetailService.createAPDetail(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(apDetail);
    }
}

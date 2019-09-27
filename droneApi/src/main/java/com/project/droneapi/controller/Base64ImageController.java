package com.project.droneapi.controller;

import com.project.droneapi.model.APDetail;
import com.project.droneapi.model.AerialPhotograph;
import com.project.droneapi.model.Base64Image;
import com.project.droneapi.service.AerialPhotographService;
import com.project.droneapi.service.Base64ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/upload")
public class Base64ImageController {
    @Autowired
    private Base64ImageService base64ImageService;

    @GetMapping
    public ResponseEntity<?> getAllAP() {
        List<Base64Image> base64Images = base64ImageService.retrieveCustomers();
        return ResponseEntity.ok(base64Images);
    }

    @PostMapping()
    public ResponseEntity<?> postAP(@RequestBody Base64Image body) {
        Base64Image base64Image = base64ImageService.createBase64Image(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(base64Image);
    }
}

package com.project.droneapi.controller;

import com.project.droneapi.model.DBFile;
import com.project.droneapi.model.ImageDetail;
import com.project.droneapi.model.Marker;
import com.project.droneapi.payload.UploadFileResponse;
import com.project.droneapi.service.DBFileStorageService;
import com.project.droneapi.service.ImageDetailService;
import com.project.droneapi.service.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.MultipartConfigElement;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ImageDetailController {

    @Autowired
    private ImageDetailService imageDetailService;
    @Autowired
    private DBFileStorageService dbFileStorageService;
    @Autowired
    private MarkerService markerService;

    @PostMapping("/upload")
    @ResponseBody
    public UploadFileResponse uploadNewImage (
              @RequestParam String userID
            , @RequestParam double latitude
            , @RequestParam double longitude
            , @RequestParam String flightID
            ,@RequestParam String timeStamp
            ,@RequestParam("file") MultipartFile imageFile
            ) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        DBFile dbFile = dbFileStorageService.storeFile(imageFile);
        ImageDetail imageDetail = new ImageDetail();
        Marker marker = new Marker();



        marker.setMarkerLat(new BigDecimal(latitude).setScale(4, RoundingMode.HALF_UP).doubleValue());
        marker.setMarkerLon(new BigDecimal(longitude).setScale(4, RoundingMode.HALF_UP).doubleValue());
        marker.setUserID(userID);


        Marker existMarker =  markerService.findMarkerExist(marker.getUserID(),marker.getMarkerLat(),marker.getMarkerLon());



        imageDetail.setImageID(dbFile.getId());
        imageDetail.setUserID(userID);
        imageDetail.setLatitude(latitude);
        imageDetail.setLongitude(longitude);
        imageDetail.setTimeStamp(LocalDateTime.parse(timeStamp));
        imageDetail.setFlightID(flightID);

        if(existMarker != null){
            imageDetail.setMarkerID(existMarker.getId());
        }else{
            String markerID = markerService.createNewMarker(marker).getId();
            imageDetail.setMarkerID(marker.getId());
        }

        imageDetailService.createNewAP(imageDetail);




        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                imageFile.getContentType(), imageFile.getSize());
    }



    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        DBFile dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG) //.contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getId()+".png" +"\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    @GetMapping("/getAllImageUrlByMarkerIDAndFlightID")
    @ResponseBody
    public  List<String> getAllImageUrlByMarkerIDAndFlightIDr(@RequestParam String userID,@RequestParam String markerID,@RequestParam String flightID){
        return  imageDetailService.getFirstImageNameByMarkerAndFlightID(userID,markerID,flightID);
    }





}

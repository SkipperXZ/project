package com.project.droneapi.service;

import com.project.droneapi.model.ImageDetail;
import com.project.droneapi.repository.ImageDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageDetailService {

    private ImageDetailRepository repository;

    @Autowired
    public ImageDetailService(ImageDetailRepository repository) {
        this.repository = repository;
    }

    public Iterable<ImageDetail> retrieveCustomers() {
        return repository.findAll();
    }

    public ImageDetail createNewAP(ImageDetail imageDetail) {
        return repository.save(imageDetail);

    }
    public List<String> getAllImageUrlFromMarker(String userID,String markerID){
        List<ImageDetail> imageDetails = repository.findAllByUserIDAndMarkerID(userID,markerID);
        List<String>imageUrls = new ArrayList<>();

        for (ImageDetail e:
             imageDetails) {
            imageUrls.add(e.getImageID());
        }
        return imageUrls;
    }

    public String getFirstImageNameByMarker(String markerID){
        return repository.findFirstByMarkerID(markerID).getImageID();
    }


}

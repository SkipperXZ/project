package com.project.droneapi.service;

import com.project.droneapi.model.ImageDetail;
import com.project.droneapi.repository.ImageDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}

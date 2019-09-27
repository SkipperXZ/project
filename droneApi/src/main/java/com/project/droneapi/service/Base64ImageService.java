package com.project.droneapi.service;

import com.project.droneapi.model.Base64Image;
import com.project.droneapi.repository.Base64ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Base64ImageService {
    private Base64ImageRepository repository;

    @Autowired
    public Base64ImageService(Base64ImageRepository repository) {
        this.repository = repository;
    }

    public List<Base64Image> retrieveCustomers() {
        return repository.findAll();
    }

    public Base64Image createBase64Image(Base64Image base64Image) {
        return repository.save(base64Image);
    }
}

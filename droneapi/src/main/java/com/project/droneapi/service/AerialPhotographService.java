package com.project.droneapi.service;

import com.project.droneapi.model.AerialPhotograph;
import com.project.droneapi.repository.AerialPhotographRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AerialPhotographService {

    private AerialPhotographRepository repository;

    @Autowired
    public AerialPhotographService(AerialPhotographRepository repository) {
        this.repository = repository;
    }

    public List<AerialPhotograph> retrieveCustomers() {
        return repository.findAll();
    }

    public AerialPhotograph createNewAP(AerialPhotograph ap) {
        return repository.save(ap);
    }

}

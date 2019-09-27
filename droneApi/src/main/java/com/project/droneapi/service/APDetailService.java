package com.project.droneapi.service;

import com.project.droneapi.model.APDetail;
import com.project.droneapi.model.AerialPhotograph;
import com.project.droneapi.repository.APDetailRepository;
import com.project.droneapi.repository.AerialPhotographRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class APDetailService {
    private APDetailRepository repository;

    @Autowired
    public APDetailService(APDetailRepository repository) {
        this.repository = repository;
    }

    public List<APDetail> retrieveCustomers() {
        return repository.findAll();
    }

    public APDetail createAPDetail(APDetail apDetail) {
        return repository.save(apDetail);
    }
}

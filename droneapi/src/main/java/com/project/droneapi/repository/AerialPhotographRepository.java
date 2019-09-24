package com.project.droneapi.repository;

import com.project.droneapi.model.AerialPhotograph;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface AerialPhotographRepository extends MongoRepository<AerialPhotograph, String> {
}

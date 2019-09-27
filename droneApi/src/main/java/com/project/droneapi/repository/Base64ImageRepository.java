package com.project.droneapi.repository;

import com.project.droneapi.model.AerialPhotograph;
import com.project.droneapi.model.Base64Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Base64ImageRepository extends MongoRepository<Base64Image, String> {
}

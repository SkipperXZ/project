package com.project.droneapi.repository;

import com.project.droneapi.model.ImageDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface ImageDetailRepository extends CrudRepository<ImageDetail, String> {
}

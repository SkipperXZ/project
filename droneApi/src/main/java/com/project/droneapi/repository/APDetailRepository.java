package com.project.droneapi.repository;

import com.project.droneapi.model.APDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface APDetailRepository extends MongoRepository<APDetail, String> {
}

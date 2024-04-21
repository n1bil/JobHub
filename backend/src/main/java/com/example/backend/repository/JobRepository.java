package com.example.backend.repository;

import com.example.backend.entity.Job;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface JobRepository extends MongoRepository<Job, String> {

    List<Job> findAllByCreatedBy(String createdBy);

    @Query("{$match: {createdBy: ?0}}, {$group: {_id: '$jobStatus', count: {$sum: 1}}}")
    List<Document> aggregateJobStatusCount(ObjectId userId);

}

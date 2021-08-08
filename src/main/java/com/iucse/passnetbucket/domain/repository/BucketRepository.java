package com.iucse.passnetbucket.domain.repository;

import com.iucse.passnetbucket.domain.aggregate.entity.SpaceBucket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketRepository extends MongoRepository<SpaceBucket, String> {
}

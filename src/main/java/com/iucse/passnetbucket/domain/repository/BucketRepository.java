package com.iucse.passnetbucket.domain.repository;

import com.iucse.passnetbucket.domain.aggregate.entity.SpaceBucket;
import com.iucse.passnetbucket.domain.aggregate.vo.OwnerId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BucketRepository extends MongoRepository<SpaceBucket, String> {
    @Query("{ owner: { ownerId: ?0 } }")
    Optional<SpaceBucket> findByOwnerId(OwnerId ownerId);
}

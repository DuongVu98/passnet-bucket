package com.iucse.passnetbucket.domain.repository;

import com.iucse.passnetbucket.domain.aggregate.entity.UploadedFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends MongoRepository<UploadedFile, String> {
}

package com.iucse.passnetbucket.usecase.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

    private final Storage storage;

    @Value("${config.bucket.appBucket}")
    private String bucket;

    @Autowired
    public StorageService(Storage storage) {
        this.storage = storage;
    }

    public Blob upload(String folder, String fileName, byte[] content) {
        return storage.create(
           BlobInfo.newBuilder(bucket, String.format("%s/%s", folder, fileName))
              .build(),
           content
        );
    }
}

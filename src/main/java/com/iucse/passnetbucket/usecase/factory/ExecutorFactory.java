package com.iucse.passnetbucket.usecase.factory;

import com.iucse.passnetbucket.domain.command.CreateBucketCommand;
import com.iucse.passnetbucket.domain.command.DeleteFileCommand;
import com.iucse.passnetbucket.domain.command.UploadFileCommand;
import com.iucse.passnetbucket.domain.repository.BucketRepository;
import com.iucse.passnetbucket.usecase.executor.CommandExecutor;
import com.iucse.passnetbucket.usecase.executor.CreateBucketExecutor;
import com.iucse.passnetbucket.usecase.executor.DeleteFileExecutor;
import com.iucse.passnetbucket.usecase.executor.UploadFileExecutor;
import com.iucse.passnetbucket.usecase.service.OwnerTypeMappingService;
import com.iucse.passnetbucket.usecase.service.RewriteNameService;
import com.iucse.passnetbucket.usecase.service.StorageService;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true)
public class ExecutorFactory {
    private BucketRepository bucketRepository;
    private OwnerTypeMappingService ownerTypeMappingService;
    private StorageService storageService;
    private RewriteNameService rewriteNameService;

    @Autowired
    public ExecutorFactory(BucketRepository bucketRepository, OwnerTypeMappingService ownerTypeMappingService, StorageService storageService, RewriteNameService rewriteNameService) {
        this.bucketRepository = bucketRepository;
        this.ownerTypeMappingService = ownerTypeMappingService;
        this.storageService = storageService;
        this.rewriteNameService = rewriteNameService;
    }

    public CommandExecutor produce(CreateBucketCommand command) {
        var executor = CreateBucketExecutor.builder()
           .bucketRepository(bucketRepository)
           .ownerTypeMappingService(ownerTypeMappingService)
           .build();
        executor.setCommand(command);
        return executor;
    }

    public CommandExecutor produce(UploadFileCommand command) {
        var executor = UploadFileExecutor.builder()
           .bucketRepository(bucketRepository)
           .storageService(storageService)
           .rewriteNameService(rewriteNameService)
           .build();
        executor.setCommand(command);
        return executor;
    }

    public CommandExecutor produce(DeleteFileCommand command) {
        var executor = DeleteFileExecutor.builder()
           .bucketRepository(bucketRepository)
           .build();
        executor.setCommand(command);
        return executor;
    }
}

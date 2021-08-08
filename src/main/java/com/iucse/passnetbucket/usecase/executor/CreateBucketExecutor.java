package com.iucse.passnetbucket.usecase.executor;

import com.iucse.passnetbucket.domain.aggregate.entity.Owner;
import com.iucse.passnetbucket.domain.aggregate.entity.SpaceBucket;
import com.iucse.passnetbucket.domain.aggregate.vo.GcpBucketId;
import com.iucse.passnetbucket.domain.aggregate.vo.OwnerId;
import com.iucse.passnetbucket.domain.command.BaseCommand;
import com.iucse.passnetbucket.domain.command.CreateBucketCommand;
import com.iucse.passnetbucket.domain.exception.CommandNotCompatibleException;
import com.iucse.passnetbucket.domain.repository.BucketRepository;
import com.iucse.passnetbucket.usecase.feature.CommandConverter;
import com.iucse.passnetbucket.usecase.service.OwnerTypeMappingService;
import com.iucse.passnetbucket.usecase.service.StorageService;
import lombok.Builder;

import java.util.ArrayList;

public class CreateBucketExecutor extends CommandExecutor implements CommandConverter<CreateBucketCommand> {

    private final StorageService storageService;
    private final BucketRepository bucketRepository;
    private final OwnerTypeMappingService ownerTypeMappingService;

    @Builder
    public CreateBucketExecutor(BaseCommand command, StorageService storageService, BucketRepository bucketRepository, OwnerTypeMappingService ownerTypeMappingService) {
        super(command);
        this.storageService = storageService;
        this.bucketRepository = bucketRepository;
        this.ownerTypeMappingService = ownerTypeMappingService;
    }

    @Override
    public SpaceBucket execute() {
        var typedCommand = convertCommand(command);
        return this.bucketRepository.save(
           SpaceBucket.builder()
              .owner(
                 Owner.builder()
                    .ownerId(new OwnerId(typedCommand.getOwnerId()))
                    .ownerType(ownerTypeMappingService.getOwnerType(typedCommand.getOwnerType()))
                    .build()
              )
              .uploadedFiles(new ArrayList<>())
              .gcpBucketId(new GcpBucketId(this.storageService.getStorageId()))
              .build()
        );
    }

    @Override
    public CreateBucketCommand convertCommand(BaseCommand command) {
        if (this.command instanceof CreateBucketCommand) {
            return (CreateBucketCommand) command;
        } else {
            throw new CommandNotCompatibleException("This command must be CreateBucketCommand");
        }
    }
}

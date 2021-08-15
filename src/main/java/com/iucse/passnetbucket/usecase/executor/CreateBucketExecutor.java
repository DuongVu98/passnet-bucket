package com.iucse.passnetbucket.usecase.executor;

import com.iucse.passnetbucket.domain.aggregate.entity.Owner;
import com.iucse.passnetbucket.domain.aggregate.entity.SpaceBucket;
import com.iucse.passnetbucket.domain.aggregate.vo.GcpFolder;
import com.iucse.passnetbucket.domain.aggregate.vo.OwnerId;
import com.iucse.passnetbucket.domain.command.BaseCommand;
import com.iucse.passnetbucket.domain.command.CreateBucketCommand;
import com.iucse.passnetbucket.domain.exception.CommandNotCompatibleException;
import com.iucse.passnetbucket.domain.repository.BucketRepository;
import com.iucse.passnetbucket.usecase.feature.CommandConverter;
import com.iucse.passnetbucket.usecase.service.OwnerTypeMappingService;
import lombok.Builder;

import java.util.ArrayList;

public class CreateBucketExecutor extends CommandExecutor implements CommandConverter<CreateBucketCommand> {

    private final BucketRepository bucketRepository;
    private final OwnerTypeMappingService ownerTypeMappingService;

    @Builder
    public CreateBucketExecutor(BucketRepository bucketRepository, OwnerTypeMappingService ownerTypeMappingService) {
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
              .gcpFolder(new GcpFolder(typedCommand.getOwnerId()))
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

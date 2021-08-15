package com.iucse.passnetbucket.usecase.executor;

import com.iucse.passnetbucket.domain.aggregate.entity.SpaceBucket;
import com.iucse.passnetbucket.domain.aggregate.entity.UploadedFile;
import com.iucse.passnetbucket.domain.command.BaseCommand;
import com.iucse.passnetbucket.domain.command.DeleteFileCommand;
import com.iucse.passnetbucket.domain.exception.BucketNotFoundException;
import com.iucse.passnetbucket.domain.exception.FileNotExistException;
import com.iucse.passnetbucket.domain.repository.BucketRepository;
import com.iucse.passnetbucket.usecase.feature.CommandConverter;
import lombok.Builder;

public class DeleteFileExecutor extends CommandExecutor implements CommandConverter<DeleteFileCommand> {

    private final BucketRepository bucketRepository;

    @Builder
    public DeleteFileExecutor(BucketRepository bucketRepository) {
        this.bucketRepository = bucketRepository;
    }

    @Override
    public SpaceBucket execute() {
        var typedCommand = convertCommand(command);

        var bucket = bucketRepository
           .findById(command.getAggregateId())
           .orElseThrow(() -> new BucketNotFoundException("Bucket not found"));
        var file = checkExistAndGetFile(typedCommand.getFileId(), bucket);

        bucket.deleteFile(file);

        return bucketRepository.save(bucket);
    }

    @Override
    public DeleteFileCommand convertCommand(BaseCommand command) {
        return null;
    }

    private UploadedFile checkExistAndGetFile(String fileId, SpaceBucket bucket) {
        return bucket.getUploadedFiles().stream()
           .filter(f -> f.getId().equals(fileId))
           .findFirst()
           .orElseThrow(() -> new FileNotExistException(""));
    }
}

package com.iucse.passnetbucket.usecase.executor;

import com.google.cloud.storage.Blob;
import com.iucse.passnetbucket.domain.aggregate.entity.SpaceBucket;
import com.iucse.passnetbucket.domain.aggregate.entity.UploadedFile;
import com.iucse.passnetbucket.domain.aggregate.vo.FileName;
import com.iucse.passnetbucket.domain.aggregate.vo.FileType;
import com.iucse.passnetbucket.domain.aggregate.vo.OwnerId;
import com.iucse.passnetbucket.domain.aggregate.vo.SignedUrl;
import com.iucse.passnetbucket.domain.command.BaseCommand;
import com.iucse.passnetbucket.domain.command.UploadFileCommand;
import com.iucse.passnetbucket.domain.exception.BucketNotFoundException;
import com.iucse.passnetbucket.domain.exception.CommandNotCompatibleException;
import com.iucse.passnetbucket.domain.exception.FileNameExistedException;
import com.iucse.passnetbucket.domain.exception.FileNotExistException;
import com.iucse.passnetbucket.domain.repository.BucketRepository;
import com.iucse.passnetbucket.usecase.feature.CommandConverter;
import com.iucse.passnetbucket.usecase.service.StorageService;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

public class UploadFileExecutor extends CommandExecutor implements CommandConverter<UploadFileCommand> {

    private final StorageService storageService;
    private final BucketRepository bucketRepository;

    @Builder
    public UploadFileExecutor(BaseCommand command, StorageService storageService, BucketRepository bucketRepository) {
        super(command);
        this.storageService = storageService;
        this.bucketRepository = bucketRepository;
    }

    @Override
    public SpaceBucket execute() {
        var typedCommand = convertCommand(command);
        var file = typedCommand.getFile();
        var bucket = bucketRepository.findByOwnerId(new OwnerId(typedCommand.getOwnerId())).orElseThrow(() -> new BucketNotFoundException(String.format("Bucket for owner %s not found", typedCommand.getOwnerId())));

        checkFileExists(file, bucket);

        try {
            Blob blob = storageService.upload(bucket.getGcpFolder().getValue(), file.getOriginalFilename(), file.getBytes());

            bucket.uploadFile(
               UploadedFile.builder()
                  .fileName(new FileName(Objects.requireNonNull(file.getOriginalFilename())))
                  .fileType(new FileType(getDocType(file)))
                  .signedUrl(new SignedUrl(blob.getSelfLink()))
                  .build()
            );
        } catch (IOException exception) {
            throw new FileNotExistException("Cannot open uploading file", exception);
        }

        return bucketRepository.save(bucket);
    }

    @Override
    public UploadFileCommand convertCommand(BaseCommand command) {
        if (this.command instanceof UploadFileCommand) {
            return (UploadFileCommand) command;
        } else {
            throw new CommandNotCompatibleException("This command must be UploadFileCommand");
        }
    }

    private String getDocType(MultipartFile file) {
        var separatedName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        return separatedName[separatedName.length - 1];
    }

    private void checkFileExists(MultipartFile file, SpaceBucket bucket) {
        var fileName = file.getOriginalFilename();
        bucket.getUploadedFiles().stream()
           .filter(f -> f.getFileName().getValue().equals(fileName))
           .findFirst()
           .ifPresent(
              uploadedFile -> {
                  throw new FileNameExistedException(String.format("File name [%s] existed in bucket [%s]", fileName, bucket.getId()));
              }
           );
    }
}

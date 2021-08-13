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
import com.iucse.passnetbucket.usecase.service.RewriteNameService;
import com.iucse.passnetbucket.usecase.service.StorageService;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@FieldDefaults(makeFinal = true)
public class UploadFileExecutor extends CommandExecutor implements CommandConverter<UploadFileCommand> {

    private StorageService storageService;
    private BucketRepository bucketRepository;
    private RewriteNameService rewriteNameService;

    @Builder
    public UploadFileExecutor(BaseCommand command, StorageService storageService, BucketRepository bucketRepository, RewriteNameService rewriteNameService) {
        super(command);
        this.storageService = storageService;
        this.bucketRepository = bucketRepository;
        this.rewriteNameService = rewriteNameService;
    }

    @Override
    public SpaceBucket execute() {
        var typedCommand = convertCommand(command);
        var file = typedCommand.getFile();
        var bucket = bucketRepository.findByOwnerId(new OwnerId(typedCommand.getOwnerId())).orElseThrow(() -> new BucketNotFoundException(String.format("Bucket for owner %s not found", typedCommand.getOwnerId())));

        var fileName = checkFileExistsOrElse(file, bucket, typedCommand.isRewriteName());

        try {
            Blob blob = storageService.upload(bucket.getGcpFolder().getValue(), fileName, file.getBytes());

            bucket.uploadFile(
               UploadedFile.builder()
                  .fileName(new FileName(fileName))
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

    private String checkFileExistsOrElse(MultipartFile file, SpaceBucket bucket, boolean rewrite) {
        AtomicReference<String> fileName = new AtomicReference<>(file.getOriginalFilename());
        bucket.getUploadedFiles().stream()
           .filter(f -> f.getFileName().getValue().equals(fileName.get()))
           .findFirst()
           .ifPresent(
              uploadedFile -> {
                  if (!rewrite) {
                      throw new FileNameExistedException(String.format("File name [%s] existed in bucket [%s]", fileName.get(), bucket.getId()));
                  } else {
                      fileName.set(rewriteNameService.rewriteName(bucket, fileName.get()));
                  }
              }
           );
        return fileName.get();
    }
}

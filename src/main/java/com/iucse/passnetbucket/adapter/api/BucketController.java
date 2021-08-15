package com.iucse.passnetbucket.adapter.api;

import com.iucse.passnetbucket.adapter.controller.CommandGateway;
import com.iucse.passnetbucket.domain.command.CreateBucketCommand;
import com.iucse.passnetbucket.domain.command.DeleteFileCommand;
import com.iucse.passnetbucket.domain.command.UploadFileCommand;
import com.iucse.passnetbucket.domain.request.CreateBucketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/buckets")
public class BucketController extends BaseController {

    private final CommandGateway commandGateway;

    @Autowired
    public BucketController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping(value = "/create-bucket")
    public ResponseEntity<Object> createBucket(@Valid @RequestBody CreateBucketRequest request) {
        var command = CreateBucketCommand.builder()
           .ownerId(request.getOwnerId())
           .ownerType(request.getOwnerType())
           .build();
        this.commandGateway.send(command);

        return returnCreated();
    }

    @PostMapping("/upload-file")
    public ResponseEntity<Object> uploadFile(@Valid @NotNull @RequestParam("file") MultipartFile file, @RequestParam("ownerId") String ownerId, @RequestParam("posterId") String posterId) {
        var command = UploadFileCommand.builder()
           .file(file)
           .ownerId(ownerId)
           .rewriteName(false)
           .posterId(posterId)
           .build();
        this.commandGateway.send(command);
        return returnCreated();
    }

    @PostMapping("/upload-file-rewrite")
    public ResponseEntity<Object> uploadFileRewrite(@Valid @NotNull @RequestParam("file") MultipartFile file, @RequestParam("ownerId") String ownerId, @RequestParam("posterId") String posterId) {
        var command = UploadFileCommand.builder()
           .file(file)
           .ownerId(ownerId)
           .posterId(posterId)
           .rewriteName(true)
           .build();
        this.commandGateway.send(command);
        return returnCreated();
    }

    @DeleteMapping("/delete-file")
    public ResponseEntity<Object> deleteFile(@RequestParam("bucketId") String bucketId, @RequestParam("fileId") String fileId, @RequestParam("posterId") String posterId) {
        var command = DeleteFileCommand.builder()
           .fileId(fileId)
           .posterId(posterId)
           .build();
        command.setAggregateId(bucketId);
        this.commandGateway.send(command);
        return returnOk();
    }
}

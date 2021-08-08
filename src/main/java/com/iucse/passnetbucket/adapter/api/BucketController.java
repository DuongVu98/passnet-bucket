package com.iucse.passnetbucket.adapter.api;

import com.iucse.passnetbucket.adapter.controller.CommandGateway;
import com.iucse.passnetbucket.domain.command.CreateBucketCommand;
import com.iucse.passnetbucket.domain.request.CreateBucketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

        return returnOk();
    }
}

package com.iucse.passnetbucket.adapter.controller;

import com.iucse.passnetbucket.domain.command.CreateBucketCommand;
import com.iucse.passnetbucket.domain.command.UploadFileCommand;
import com.iucse.passnetbucket.usecase.factory.ExecutorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandGateway {

    private final ExecutorFactory executorFactory;

    @Autowired
    public CommandGateway(ExecutorFactory executorFactory) {
        this.executorFactory = executorFactory;
    }

    public void send(CreateBucketCommand command) {
        var executor = executorFactory.produce(command);
        executor.execute();
    }

    public void send(UploadFileCommand command) {
        var executor = executorFactory.produce(command);
        executor.execute();
    }
}

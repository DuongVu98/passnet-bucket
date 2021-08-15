package com.iucse.passnetbucket.adapter.controller;

import com.iucse.passnetbucket.domain.command.CreateBucketCommand;
import com.iucse.passnetbucket.domain.command.DeleteFileCommand;
import com.iucse.passnetbucket.domain.command.UploadFileCommand;
import com.iucse.passnetbucket.usecase.factory.ExecutorConcernBuilder;
import com.iucse.passnetbucket.usecase.factory.ExecutorFactory;
import com.iucse.passnetbucket.usecase.proxy.PosterProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandGateway {

    private final ExecutorFactory executorFactory;
    private final ExecutorConcernBuilder concernBuilder;

    @Autowired
    public CommandGateway(ExecutorFactory executorFactory, ExecutorConcernBuilder concernBuilder) {
        this.executorFactory = executorFactory;
        this.concernBuilder = concernBuilder;
    }

    public void send(CreateBucketCommand command) {
        var executor = executorFactory.produce(command);
        executor.execute();
    }

    public void send(UploadFileCommand command) {
        var executor = executorFactory.produce(command);
        executor.execute();
    }

    public void send(DeleteFileCommand command) {
        var executor = executorFactory.produce(command);
        executor = concernBuilder.withExecutor(executor)
           .proxy()
           .addProxy(PosterProxy.class)
           .compose();

        executor.execute();
    }
}

package com.iucse.passnetbucket.usecase.proxy;

import com.iucse.passnetbucket.domain.aggregate.entity.SpaceBucket;
import com.iucse.passnetbucket.domain.exception.ExecutorDeniedException;
import com.iucse.passnetbucket.usecase.executor.CommandExecutor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
public abstract class ExecutorProxy extends CommandExecutor {

    protected CommandExecutor executor;

    protected ExecutorProxy(CommandExecutor executor) {
        this.executor = executor;
    }

    protected abstract boolean isExecutable();

    @Override
    public SpaceBucket execute() {
        if (isExecutable()) {
            return executor.execute();
        } else {
            throw new ExecutorDeniedException("This command is forbidden");
        }
    }
}

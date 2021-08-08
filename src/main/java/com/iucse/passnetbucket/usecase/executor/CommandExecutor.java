package com.iucse.passnetbucket.usecase.executor;

import com.iucse.passnetbucket.domain.aggregate.entity.SpaceBucket;
import com.iucse.passnetbucket.domain.command.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public abstract class CommandExecutor {

    protected BaseCommand command;

    public abstract SpaceBucket execute();

}

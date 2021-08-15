package com.iucse.passnetbucket.usecase.executor;

import com.iucse.passnetbucket.domain.aggregate.entity.SpaceBucket;
import com.iucse.passnetbucket.domain.command.BaseCommand;
import lombok.Getter;
import lombok.Setter;


public abstract class CommandExecutor {

    @Getter
    @Setter
    protected BaseCommand command;

    public abstract SpaceBucket execute();
}

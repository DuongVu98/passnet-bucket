package com.iucse.passnetbucket.usecase.feature;


import com.iucse.passnetbucket.domain.command.BaseCommand;

public interface CommandConverter<T> {
    T convertCommand(BaseCommand command);
}

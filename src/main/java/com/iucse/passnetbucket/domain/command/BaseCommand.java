package com.iucse.passnetbucket.domain.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class BaseCommand {
    @Setter
    protected String aggregateId;
}

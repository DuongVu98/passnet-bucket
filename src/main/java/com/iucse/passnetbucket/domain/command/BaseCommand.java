package com.iucse.passnetbucket.domain.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
public class BaseCommand {
    @Setter
    protected String aggregateId;
}

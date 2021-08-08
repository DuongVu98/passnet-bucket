package com.iucse.passnetbucket.usecase.feature;

import com.iucse.passnetbucket.domain.compensating.BaseCompensating;

public interface CompensatingConverter<T, S> extends CommandConverter<T> {
    S convertCompensating(BaseCompensating command);
}

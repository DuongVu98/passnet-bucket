package com.iucse.passnetbucket.domain.aggregate.vo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class GcpFolder {
    private String value;
}

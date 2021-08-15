package com.iucse.passnetbucket.domain.aggregate.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public enum FileType {
    PROFILE_PICTURE(1, true),
    BACKGROUND_PICTURE(1, true),
    DOCUMENTATION(0, false);

    private Integer maxNumber;
    private Boolean isUnique;
}

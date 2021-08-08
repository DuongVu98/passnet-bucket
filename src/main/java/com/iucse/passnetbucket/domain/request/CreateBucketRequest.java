package com.iucse.passnetbucket.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class CreateBucketRequest {

    @NotBlank
    private String ownerId;
    @NotBlank
    private String ownerType;
}

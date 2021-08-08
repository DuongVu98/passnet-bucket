package com.iucse.passnetbucket.domain.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorView {
    @NonNull
    @JsonProperty("message")
    private String message;

    @NonNull
    @JsonProperty("statusCode")
    private String statusCode;

    @JsonProperty("data")
    private Object data;
}

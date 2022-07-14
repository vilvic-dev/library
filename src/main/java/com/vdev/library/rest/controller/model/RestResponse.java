package com.vdev.library.rest.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RestResponse<T> {

    @Schema(description = "response status", example = "OK", implementation = ResponseStatus.class)
    private ResponseStatus responseStatus = ResponseStatus.OK;

    @Schema(description = "payload")
    private T payload;

}

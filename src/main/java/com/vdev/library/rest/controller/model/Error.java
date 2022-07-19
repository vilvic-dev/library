package com.vdev.library.rest.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Error {

    @Schema(description = "Error code", example = "B00000002")
    private String errorCode;

    @Schema(description = "Error message", example = "Name is required")
    private String message;

}

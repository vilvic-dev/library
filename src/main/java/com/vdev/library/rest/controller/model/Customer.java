package com.vdev.library.rest.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Customer {

    @Schema(description = "Customer id", example = "cca49f0-cbda-4c56-a0e5-cd7dcf16c607")
    private String id;

    @Schema(description = "Name", example = "Andrew Smith", required = true)
    private String name;

    @Schema(description = "Address line 1", example = "1 Some street", required = true)
    private String address1;

    @Schema(description = "Address line 2", example = "Some region", required = true)
    private String address2;

    @Schema(description = "Address line 3", example = "Some town")
    private String address3;

    @Schema(description = "Address line 4", example = "Some city")
    private String address4;

    @Schema(description = "Postal code", example = "PC12 1AA", required = true)
    private String postCode;

    @Schema(description = "Telephone", example = "020 100 1000")
    private String telephone;

    @Schema(description = "Email address", example = "andrew.smith@email.com", required = true)
    private String email;

}

package com.vdev.library.rest.controller.model;

import com.vdev.library.rest.controller.model.validation.Email;
import com.vdev.library.rest.controller.model.validation.TelephoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
@Data
public class Customer {

    @Schema(description = "Customer id", example = "cca49f0-cbda-4c56-a0e5-cd7dcf16c607")
    private String id;

    @Schema(description = "Name", example = "Andrew Smith", required = true)
    @Size(max = 100, message = "B00000001")
    @NotEmpty(message = "B00000002")
    private String name;

    @Schema(description = "Address line 1", example = "1 Some street", required = true)
    @Size(max = 100, message = "B00000003")
    @NotEmpty(message = "B00000004")
    private String address1;

    @Schema(description = "Address line 2", example = "Some region", required = true)
    @Size(max = 100, message = "B00000005")
    @NotEmpty(message = "B00000006")
    private String address2;

    @Schema(description = "Address line 3", example = "Some town")
    @Size(max = 100, message = "B00000007")
    private String address3;

    @Schema(description = "Address line 4", example = "Some city")
    @Size(max = 100, message = "B00000008")
    private String address4;

    @Schema(description = "Postal code", example = "PC12 1AA", required = true)
    @Size(max = 16, message = "B00000009")
    @NotEmpty(message = "B00000010")
    private String postCode;

    @Schema(description = "Telephone", example = "020 100 1000")
    @Size(max = 16, message = "B00000011")
    @TelephoneNumber(message = "B00000012")
    private String telephone;

    @Schema(description = "Email address", example = "andrew.smith@email.com", required = true)
    @Size(max = 255, message = "B00000013")
    @Email(message = "B00000014")
    private String email;

}

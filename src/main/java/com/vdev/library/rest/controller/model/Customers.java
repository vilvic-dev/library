package com.vdev.library.rest.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Customers {

    @Schema(description = "Customers")
    private List<Customer> customers;

}

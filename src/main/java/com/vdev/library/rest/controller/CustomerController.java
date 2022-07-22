package com.vdev.library.rest.controller;

import com.vdev.library.rest.controller.model.Customer;
import com.vdev.library.rest.controller.model.Customers;
import com.vdev.library.rest.controller.model.Error;
import com.vdev.library.rest.controller.model.RestResponse;
import com.vdev.library.rest.controller.reponses.RestResponseCustomer;
import com.vdev.library.rest.controller.reponses.RestResponseCustomers;
import com.vdev.library.rest.exception.BadRequestException;
import com.vdev.library.rest.exception.NoContentException;
import com.vdev.library.rest.exception.NotFoundException;
import com.vdev.library.rest.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Locale;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final MessageSource messageSource;

    @Operation(summary = "Get a customer by id", description = "Get a customer by id", tags = {"Customer"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RestResponseCustomer.class))
            }),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<Customer>> getCustomerById(
            @PathVariable(name = "customerId") final String customerId) {

        final var optionalCustomer = customerService.getCustomerById(customerId);
        if (optionalCustomer.isPresent()) {
            final var restResponse = new RestResponse<Customer>();
            restResponse.setPayload(optionalCustomer.get());
            return ResponseEntity.ok().body(restResponse);
        } else {
            throw new NotFoundException();
        }

    }

    @Operation(summary = "Edit customer by id", description = "Edit customer by id", tags = {"Customer"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RestResponseCustomer.class))
            }),
            @ApiResponse(responseCode = "400", description = "Customer edit failed", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @PutMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<Customer>> editCustomerById(
            @PathVariable(name = "customerId") final String customerId,
            @Parameter(description = "Customer model") @Valid @RequestBody final Customer customer,
            final BindingResult bindingResult,
            final Locale locale) {

        final var optionalCustomer = customerService.getCustomerById(customerId);
        if (optionalCustomer.isPresent()) {
            if (bindingResult.hasErrors()) {
                throw new BadRequestException(bindingResult);
            } else {
                final var restResponse = new RestResponse<Customer>();
                if (customerService.doesEmailExistForAnotherCustomer(customerId, customer.getEmail())) {
                    restResponse.getErrors().add(new Error(
                            "B00000015",
                            messageSource.getMessage("B00000015", new String[]{}, locale)));
                    return ResponseEntity.badRequest().body(restResponse);
                } else {
                    final var savedCustomer = customerService.saveEdit(customerId, customer);
                    restResponse.setPayload(savedCustomer);
                    return ResponseEntity.ok().body(restResponse);
                }
            }
        } else {
            throw new NotFoundException();
        }

    }

    @Operation(summary = "Create new customer by id", description = "Create new customer by id", tags = {"Customer"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RestResponseCustomer.class))
            }),
            @ApiResponse(responseCode = "400", description = "Customer create failed", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<Customer>> newCustomer(
            @Parameter(description = "Customer model") @Valid @RequestBody final Customer customer,
            final BindingResult bindingResult,
            final Locale locale) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult);
        } else {
            final var restResponse = new RestResponse<Customer>();
            if (customerService.doesEmailExist(customer.getEmail())) {
                restResponse.getErrors().add(new Error(
                        "B00000015",
                        messageSource.getMessage("B00000015", new String[]{}, locale)));
                return ResponseEntity.badRequest().body(restResponse);
            } else {
                final var savedCustomer = customerService.saveNew(customer);
                restResponse.setPayload(savedCustomer);
                return ResponseEntity.ok().body(restResponse);
            }
        }

    }

    @Operation(summary = "Find customers by name", description = "Find customers by name", tags = {"Customer"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer(s) found", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RestResponseCustomers.class))
            }),
            @ApiResponse(responseCode = "204", description = "No customers found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Query parameter name not provided", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<Customers>> findCustomerByName(
            @RequestParam(name = "name") final String name) {

        final var customersList = customerService.findCustomersByName(name);
        if (customersList.isEmpty()) {
            throw new NoContentException();
        } else {
            final var restResponse = new RestResponse<Customers>();
            final var customers = Customers.builder().customers(customersList).build();
            restResponse.setPayload(customers);
            return ResponseEntity.ok().body(restResponse);
        }

    }

}

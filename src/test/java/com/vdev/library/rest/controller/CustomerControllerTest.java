package com.vdev.library.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vdev.library.rest.controller.model.Customer;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.MessageSource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

import static com.vdev.library.rest.TestConstants.CUSTOMER_ANDREW_SMITH_ID;
import static com.vdev.library.rest.TestConstants.CUSTOMER_LAURA_SMITH_ID;
import static com.vdev.library.rest.TestConstants.CUSTOMER_LEE_JONES_ID;
import static com.vdev.library.rest.TestConstants.ID_INVALID;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/db/schema.sql", "/db/data.sql"})
public class CustomerControllerTest {

    private static final String TEST_URL = "http://localhost:%d/rest/customers/";

    @LocalServerPort
    private int port;

    @Autowired
    private MessageSource messageSource;

    @Test
    void GetCustomerById_CustomerExists_JsonReturned() {

        final var response = given()
                .accept(ContentType.JSON)
                .when()
                .get(String.format(TEST_URL + CUSTOMER_LEE_JONES_ID, port))
                .then()
                .statusCode(OK.value())
                .extract()
                .response();

        System.out.println(response.prettyPrint());

        response.then()
                .body("errors", notNullValue())
                .body("errors.size()", equalTo(0))
                .body("payload", notNullValue())
                .body("payload.id", equalTo(CUSTOMER_LEE_JONES_ID))
                .body("payload.name", equalTo("Lee Jones"))
                .body("payload.address1", equalTo("Address 1"))
                .body("payload.address2", equalTo("Address 2"))
                .body("payload.address3", equalTo("Address 3"))
                .body("payload.address4", equalTo("Address 4"))
                .body("payload.postCode", equalTo("PC12 1AC"))
                .body("payload.telephone", equalTo("020 100 3000"))
                .body("payload.email", equalTo("lee.jones@email.com"))
                .body("payload.version", equalTo(1));

    }

    @Test
    void GetCustomerById_CustomerDoesNotExist_NotFound() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get(String.format(TEST_URL + ID_INVALID, port))
                .then()
                .statusCode(NOT_FOUND.value());
    }

    @Test
    void FindCustomerByName_CustomersExists_JsonReturned() {

        final var response = given()
                .accept(ContentType.JSON)
                .queryParam("name", "Smith")
                .when()
                .get(String.format(TEST_URL, port))
                .then()
                .statusCode(OK.value())
                .extract()
                .response();

        response.then()
                .body("errors", notNullValue())
                .body("errors.size()", equalTo(0))
                .body("payload", notNullValue())
                .body("payload.customers", notNullValue())
                .body("payload.customers.size()", equalTo(2))
                .body("payload.customers.find{it.id=='" + CUSTOMER_ANDREW_SMITH_ID + "'}.id", equalTo(CUSTOMER_ANDREW_SMITH_ID))
                .body("payload.customers.find{it.id=='" + CUSTOMER_ANDREW_SMITH_ID + "'}.name", equalTo("Andrew Smith"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_ANDREW_SMITH_ID + "'}.address1", equalTo("Address 1"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_ANDREW_SMITH_ID + "'}.address2", equalTo("Address 2"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_ANDREW_SMITH_ID + "'}.address3", equalTo("Address 3"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_ANDREW_SMITH_ID + "'}.address4", equalTo("Address 4"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_ANDREW_SMITH_ID + "'}.postCode", equalTo("PC12 1AA"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_ANDREW_SMITH_ID + "'}.telephone", equalTo("020 100 1000"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_ANDREW_SMITH_ID + "'}.email", equalTo("andrew.smith@email.com"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_ANDREW_SMITH_ID + "'}.version", equalTo(1))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.id", equalTo(CUSTOMER_LAURA_SMITH_ID))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.name", equalTo("Laura Smith"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.address1", equalTo("Address 1"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.address2", equalTo("Address 2"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.address3", equalTo("Address 3"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.address4", equalTo("Address 4"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.postCode", equalTo("PC12 1AB"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.telephone", equalTo("020 100 2000"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.email", equalTo("laura.smith@email.com"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.version", equalTo(1));

    }

    @Test
    void FindCustomerByName_CustomerDoesNotExist_BadRequest() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get(String.format(TEST_URL, port))
                .then()
                .statusCode(BAD_REQUEST.value());
    }

    @Test
    void FindCustomerByName_CustomerDoesNotExist_NoContent() {
        given()
                .accept(ContentType.JSON)
                .queryParam("name", ID_INVALID)
                .when()
                .get(String.format(TEST_URL, port))
                .then()
                .statusCode(NO_CONTENT.value());
    }

    @Test
    void PutCustomerById_CustomerDoesNotExist_NotFound() throws JsonProcessingException {

        final var customer = Customer.builder().build();
        final var objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        final var json = objectMapper.writeValueAsString(customer);

        given()
                .accept(ContentType.JSON)
                .contentType(APPLICATION_JSON_VALUE)
                .body(json)
                .when()
                .put(String.format(TEST_URL + ID_INVALID, port))
                .then()
                .statusCode(NOT_FOUND.value())
                .extract()
                .response();

    }

    @Test
    void PutCustomerById_EmptyData_BadRequest() throws JsonProcessingException {

        final var customer = Customer.builder().build();
        final var objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        final var json = objectMapper.writeValueAsString(customer);

        final var response = given()
                .accept(ContentType.JSON)
                .contentType(APPLICATION_JSON_VALUE)
                .body(json)
                .when()
                .put(String.format(TEST_URL + CUSTOMER_LEE_JONES_ID, port))
                .then()
                .statusCode(BAD_REQUEST.value())
                .extract()
                .response();

        response.then()
                .body("payload", nullValue())
                .body("errors", notNullValue())
                .body("errors.size()", equalTo(5))
                .body("errors.find{it.errorCode=='B00000002'}.errorCode", equalTo("B00000002"))
                .body("errors.find{it.errorCode=='B00000002'}.message", equalTo(messageSource.getMessage("B00000002", new String[]{}, Locale.getDefault())))
                .body("errors.find{it.errorCode=='B00000004'}.errorCode", equalTo("B00000004"))
                .body("errors.find{it.errorCode=='B00000004'}.message", equalTo(messageSource.getMessage("B00000004", new String[]{}, Locale.getDefault())))
                .body("errors.find{it.errorCode=='B00000006'}.errorCode", equalTo("B00000006"))
                .body("errors.find{it.errorCode=='B00000006'}.message", equalTo(messageSource.getMessage("B00000006", new String[]{}, Locale.getDefault())))
                .body("errors.find{it.errorCode=='B00000010'}.errorCode", equalTo("B00000010"))
                .body("errors.find{it.errorCode=='B00000010'}.message", equalTo(messageSource.getMessage("B00000010", new String[]{}, Locale.getDefault())))
                .body("errors.find{it.errorCode=='B00000016'}.errorCode", equalTo("B00000016"))
                .body("errors.find{it.errorCode=='B00000016'}.message", equalTo(messageSource.getMessage("B00000016", new String[]{}, Locale.getDefault())));

    }

    @Test
    void PutCustomerById_DuplicateEmail_BadRequest() throws JsonProcessingException {
        final var customer = Customer
                .builder()
                .id(CUSTOMER_ANDREW_SMITH_ID)
                .name("Andrew Smith")
                .address1("Address 1")
                .address2("Address 2")
                .address3("Address 3")
                .address4("Address 4")
                .postCode("PC12 1AA")
                .telephone("020 100 1000")
                .email("lee.jones@email.com")
                .build();
        final var objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        final var json = objectMapper.writeValueAsString(customer);

        final var response = given()
                .accept(ContentType.JSON)
                .contentType(APPLICATION_JSON_VALUE)
                .body(json)
                .when()
                .put(String.format(TEST_URL + CUSTOMER_ANDREW_SMITH_ID, port))
                .then()
                .statusCode(BAD_REQUEST.value())
                .extract()
                .response();

        response.then()
                .body("payload", nullValue())
                .body("errors", notNullValue())
                .body("errors.size()", equalTo(1))
                .body("errors.find{it.errorCode=='B00000015'}.errorCode", equalTo("B00000015"))
                .body("errors.find{it.errorCode=='B00000015'}.message", equalTo(messageSource.getMessage("B00000015", new String[]{}, Locale.getDefault())));

    }

    @Test
    void PutCustomerById_ValidData_Ok() throws JsonProcessingException {
        final var customer = Customer
                .builder()
                .id(CUSTOMER_ANDREW_SMITH_ID)
                .name("Andrew Smith")
                .address1("Address 1")
                .address2("Address 2")
                .address3("Address 3")
                .address4("Address 4")
                .postCode("PC12 1AA")
                .telephone("020 100 1000")
                .email("andy.smith@email.com")
                .version(1)
                .build();
        final var objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        final var json = objectMapper.writeValueAsString(customer);

        final var response = given()
                .accept(ContentType.JSON)
                .contentType(APPLICATION_JSON_VALUE)
                .body(json)
                .when()
                .put(String.format(TEST_URL + CUSTOMER_ANDREW_SMITH_ID, port))
                .then()
                .statusCode(OK.value())
                .extract()
                .response();

        response.then()
                .body("errors", notNullValue())
                .body("errors.size()", equalTo(0))
                .body("payload", notNullValue())
                .body("payload.id", equalTo(CUSTOMER_ANDREW_SMITH_ID))
                .body("payload.name", equalTo("Andrew Smith"))
                .body("payload.address1", equalTo("Address 1"))
                .body("payload.address2", equalTo("Address 2"))
                .body("payload.address3", equalTo("Address 3"))
                .body("payload.address4", equalTo("Address 4"))
                .body("payload.postCode", equalTo("PC12 1AA"))
                .body("payload.telephone", equalTo("020 100 1000"))
                .body("payload.email", equalTo("andy.smith@email.com"))
                .body("payload.version", equalTo(2));

    }

    @Test
    void PostCustomerById_EmptyData_BadRequest() throws JsonProcessingException {

        final var customer = Customer.builder().build();
        final var objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        final var json = objectMapper.writeValueAsString(customer);

        final var response = given()
                .accept(ContentType.JSON)
                .contentType(APPLICATION_JSON_VALUE)
                .body(json)
                .when()
                .post(String.format(TEST_URL, port))
                .then()
                .statusCode(BAD_REQUEST.value())
                .extract()
                .response();

        response.then()
                .body("payload", nullValue())
                .body("errors", notNullValue())
                .body("errors.size()", equalTo(5))
                .body("errors.find{it.errorCode=='B00000002'}.errorCode", equalTo("B00000002"))
                .body("errors.find{it.errorCode=='B00000002'}.message", equalTo(messageSource.getMessage("B00000002", new String[]{}, Locale.getDefault())))
                .body("errors.find{it.errorCode=='B00000004'}.errorCode", equalTo("B00000004"))
                .body("errors.find{it.errorCode=='B00000004'}.message", equalTo(messageSource.getMessage("B00000004", new String[]{}, Locale.getDefault())))
                .body("errors.find{it.errorCode=='B00000006'}.errorCode", equalTo("B00000006"))
                .body("errors.find{it.errorCode=='B00000006'}.message", equalTo(messageSource.getMessage("B00000006", new String[]{}, Locale.getDefault())))
                .body("errors.find{it.errorCode=='B00000010'}.errorCode", equalTo("B00000010"))
                .body("errors.find{it.errorCode=='B00000010'}.message", equalTo(messageSource.getMessage("B00000010", new String[]{}, Locale.getDefault())))
                .body("errors.find{it.errorCode=='B00000016'}.errorCode", equalTo("B00000016"))
                .body("errors.find{it.errorCode=='B00000016'}.message", equalTo(messageSource.getMessage("B00000016", new String[]{}, Locale.getDefault())));

    }

    @Test
    void PostCustomerById_DuplicateEmail_BadRequest() throws JsonProcessingException {
        final var customer = Customer
                .builder()
                .name("Andy Smith")
                .address1("Address 1")
                .address2("Address 2")
                .address3("Address 3")
                .address4("Address 4")
                .postCode("PC12 5AA")
                .telephone("020 100 5000")
                .email("andrew.smith@email.com")
                .build();
        final var objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        final var json = objectMapper.writeValueAsString(customer);

        final var response = given()
                .accept(ContentType.JSON)
                .contentType(APPLICATION_JSON_VALUE)
                .body(json)
                .when()
                .post(String.format(TEST_URL, port))
                .then()
                .statusCode(BAD_REQUEST.value())
                .extract()
                .response();

        response.then()
                .body("payload", nullValue())
                .body("errors", notNullValue())
                .body("errors.size()", equalTo(1))
                .body("errors.find{it.errorCode=='B00000015'}.errorCode", equalTo("B00000015"))
                .body("errors.find{it.errorCode=='B00000015'}.message", equalTo(messageSource.getMessage("B00000015", new String[]{}, Locale.getDefault())));

    }

}

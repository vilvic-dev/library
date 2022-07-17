package com.vdev.library.rest.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.vdev.library.rest.TestConstants.CUSTOMER_ANDREW_SMITH_ID;
import static com.vdev.library.rest.TestConstants.CUSTOMER_LAURA_SMITH_ID;
import static com.vdev.library.rest.TestConstants.CUSTOMER_LEE_JONES_ID;
import static com.vdev.library.rest.TestConstants.ID_INVALID;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/db/schema.sql", "/db/data.sql"})
public class CustomerControllerTest {

    private static final String TEST_URL = "http://localhost:%d/rest/customers/";

    @LocalServerPort
    private int port;

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

        response.then()
                .body("responseStatus", equalTo("OK"))
                .body("payload", notNullValue())
                .body("payload.id", equalTo(CUSTOMER_LEE_JONES_ID))
                .body("payload.name", equalTo("Lee Jones"))
                .body("payload.address1", equalTo("Address 1"))
                .body("payload.address2", equalTo("Address 2"))
                .body("payload.address3", equalTo("Address 3"))
                .body("payload.address4", equalTo("Address 4"))
                .body("payload.postCode", equalTo("PC12 1AC"))
                .body("payload.telephone", equalTo("020 100 3000"))
                .body("payload.email", equalTo("lee.jones@email.com"));

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
                .body("responseStatus", equalTo("OK"))
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
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.id", equalTo(CUSTOMER_LAURA_SMITH_ID))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.name", equalTo("Laura Smith"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.address1", equalTo("Address 1"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.address2", equalTo("Address 2"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.address3", equalTo("Address 3"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.address4", equalTo("Address 4"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.postCode", equalTo("PC12 1AB"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.telephone", equalTo("020 100 2000"))
                .body("payload.customers.find{it.id=='" + CUSTOMER_LAURA_SMITH_ID + "'}.email", equalTo("laura.smith@email.com"));

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

}

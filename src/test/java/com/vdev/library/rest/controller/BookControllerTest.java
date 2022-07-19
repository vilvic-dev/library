package com.vdev.library.rest.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.vdev.library.rest.TestConstants.ID_INVALID;
import static com.vdev.library.rest.TestConstants.STEPHEN_KING_THE_INSTITUTE_ID;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/db/schema.sql", "/db/data.sql"})
class BookControllerTest {

    private static final String TEST_URL = "http://localhost:%d/rest/books/";

    @LocalServerPort
    private int port;

    @Test
    void GetBookById_BookExists_JsonReturned() {

        final var response = given()
                .accept(ContentType.JSON)
                .when()
                .get(String.format(TEST_URL + STEPHEN_KING_THE_INSTITUTE_ID, port))
                .then()
                .statusCode(OK.value())
                .extract()
                .response();

        response.then()
                .body("payload", notNullValue())
                .body("payload.title", equalTo("The Institute"))
                .body("payload.description", equalTo("NO ONE HAS EVER ESCAPED FROM THE INSTITUTE."))
                .body("payload.isbn", equalTo("9781529355390"))
                .body("payload.author", equalTo("Stephen King"))
                .body("payload.category", equalTo("Horror"));
    }

    @Test
    void GetBookById_BookDoesNotExist_NotFound() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get(String.format(TEST_URL + ID_INVALID, port))
                .then()
                .statusCode(NOT_FOUND.value());
    }

}

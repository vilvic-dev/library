package com.vdev.library.rest.controller.model.validation;

import lombok.Builder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmailValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void beforeAll() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        assertNotNull(validator);
    }

    @ParameterizedTest(name = "value = {0}, violations = {1}")
    @CsvSource({
            "NULL, 0",
            "'', 0",
            "'test', 1",
            "'test@test.com', 0",
    })
    void emailValidation(String value, final int violations) {

        if (value.equals("NULL")) {
            value = null;
        }

        final var object = EmailObject.builder().email(value).build();
        final var constraintValidations = validator.validate(object);
        assertEquals(violations, constraintValidations.size());

    }

    @Builder
    static class EmailObject {

        @Email
        private String email;

    }

}

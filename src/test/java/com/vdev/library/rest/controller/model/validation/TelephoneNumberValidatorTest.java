package com.vdev.library.rest.controller.model.validation;

import lombok.Builder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TelephoneNumberValidatorTest {

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
            "ABC, 1",
            "+44 020 100 10000, 0",
            "*020 100 10000, 0",
            "(020) 100 10000, 0",
            "[020] 100 10000, 1",
    })
    void telephoneNumberValidation(String value, final int violations) {

        if (value.equals("NULL")) {
            value = null;
        }

        final var object = TelephoneNumberObject.builder().telephoneNumber(value).build();
        final var constraintValidations = validator.validate(object);
        assertEquals(violations, constraintValidations.size());

    }

    @Builder
    static class TelephoneNumberObject {

        @TelephoneNumber
        private String telephoneNumber;

    }

}

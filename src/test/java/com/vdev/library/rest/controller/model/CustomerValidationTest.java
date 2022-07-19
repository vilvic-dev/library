package com.vdev.library.rest.controller.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static com.vdev.library.rest.TestConstants.STRING_101;
import static com.vdev.library.rest.TestConstants.STRING_17;
import static com.vdev.library.rest.TestConstants.STRING_256;
import static com.vdev.library.rest.utils.ValidatorUtils.errorExists;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerValidationTest {

    private static Validator validator;

    @BeforeAll
    static void beforeAll() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        assertNotNull(validator);
    }

    @Test
    void Validate_CorrectData_Success() {

        final var customer = Customer
                .builder()
                .name("Andrew Smith")
                .address1("Address 1")
                .address2("Address 2")
                .address3("Address 3")
                .address4("Address 4")
                .postCode("PC12 1AA")
                .telephone("020 100 1000")
                .email("andrew.smith@email.com")
                .build();

        final var constraintViolations = validator.validate(customer);
        assertEquals(0, constraintViolations.size());

    }

    @Test
    void Validate_Empty_Failure() {

        final var customer = Customer
                .builder()
                .build();

        final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(customer);
        assertEquals(4, constraintViolations.size());
        assertTrue(errorExists(constraintViolations, "name", "B00000002"));
        assertTrue(errorExists(constraintViolations, "address1", "B00000004"));
        assertTrue(errorExists(constraintViolations, "address2", "B00000006"));
        assertTrue(errorExists(constraintViolations, "postCode", "B00000010"));
    }

    @Test
    void Validate_InvalidEmail_Failure() {

        final var customer = Customer
                .builder()
                .name("Andrew Smith")
                .address1("Address 1")
                .address2("Address 2")
                .address3("Address 3")
                .address4("Address 4")
                .postCode("PC12 1AA")
                .telephone("020 100 1000")
                .email("andrew.smith")
                .build();

        final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(customer);
        assertEquals(1, constraintViolations.size());
        assertTrue(errorExists(constraintViolations, "email", "B00000014"));

    }

    @Test
    void Validate_InvalidTelephone_Failure() {

        final var customer = Customer
                .builder()
                .name("Andrew Smith")
                .address1("Address 1")
                .address2("Address 2")
                .address3("Address 3")
                .address4("Address 4")
                .postCode("PC12 1AA")
                .telephone("ABC")
                .email("andrew.smith@email.com")
                .build();

        final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(customer);
        assertEquals(1, constraintViolations.size());
        assertTrue(errorExists(constraintViolations, "telephone", "B00000012"));

    }

    @Test
    void Validate_TooLong_Failure() {

        final var customer = Customer
                .builder()
                .name(STRING_101)
                .address1(STRING_101)
                .address2(STRING_101)
                .address3(STRING_101)
                .address4(STRING_101)
                .postCode(STRING_17)
                .telephone(STRING_17)
                .email(STRING_256)
                .build();

        final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(customer);
        assertEquals(9, constraintViolations.size());
        assertTrue(errorExists(constraintViolations, "name", "B00000001"));
        assertTrue(errorExists(constraintViolations, "address1", "B00000003"));
        assertTrue(errorExists(constraintViolations, "address2", "B00000005"));
        assertTrue(errorExists(constraintViolations, "address3", "B00000007"));
        assertTrue(errorExists(constraintViolations, "address4", "B00000008"));
        assertTrue(errorExists(constraintViolations, "postCode", "B00000009"));
        assertTrue(errorExists(constraintViolations, "telephone", "B00000011"));
        assertTrue(errorExists(constraintViolations, "email", "B00000013"));
        assertTrue(errorExists(constraintViolations, "email", "B00000014"));

    }

}

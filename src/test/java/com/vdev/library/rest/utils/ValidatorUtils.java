package com.vdev.library.rest.utils;

import javax.validation.ConstraintViolation;
import java.util.Set;

public final class ValidatorUtils {

    private ValidatorUtils() {
    }

    public static boolean errorExists(final Set<ConstraintViolation<Object>> constraintViolations, final String field, final String errorMessage) {
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            if (field != null && field.equals(constraintViolation.getPropertyPath().toString())) {
                if (errorMessage != null && errorMessage.equals(constraintViolation.getMessage())) {
                    return true;
                }
            }
        }
        return false;
    }

}

package com.vdev.library.rest.controller.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public void initialize(Email email) {
        ConstraintValidator.super.initialize(email);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email != null) {
            if (email.isEmpty()) {
                return true;
            } else {
                return org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email);
            }
        } else {
            return true;
        }
    }

}

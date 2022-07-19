package com.vdev.library.rest.controller.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class TelephoneNumberValidator implements ConstraintValidator<TelephoneNumber, String> {

    private static final Pattern TELEPHONE_REGEX_PATTERN = Pattern.compile("[0-9*+() -]*");

    @Override
    public void initialize(TelephoneNumber telephoneNumber) {
        ConstraintValidator.super.initialize(telephoneNumber);
    }

    @Override
    public boolean isValid(String telephoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (telephoneNumber != null) {
            if (telephoneNumber.isEmpty()) {
                return true;
            } else {
                final var matcher = TELEPHONE_REGEX_PATTERN.matcher(telephoneNumber);
                return matcher.matches();
            }
        } else {
            return true;
        }
    }

}

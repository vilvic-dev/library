package com.vdev.library.rest.exception;

import org.springframework.validation.BindingResult;

public class BadRequestException extends RuntimeException {

    private BindingResult bindingResult;

    public BadRequestException(final BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

}

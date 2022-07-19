package com.vdev.library.rest.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
public class RestResponse<T> {

    @Schema(description = "payload")
    private T payload;

    @Schema(description = "Error code/messages", implementation = Error.class)
    private final List<Error> errors = new ArrayList<>();

    public final void addErrors(final BindingResult bindingResult, final MessageSource messageSource) {

        if (bindingResult != null && bindingResult.hasErrors()) {
            for (ObjectError fieldError : bindingResult.getAllErrors()) {
                if (fieldError.getDefaultMessage() != null) {
                    errors.add(new Error(
                            fieldError.getDefaultMessage(),
                            messageSource.getMessage(fieldError.getDefaultMessage(), new String[]{}, Locale.getDefault())));
                }
            }
        }
    }

}

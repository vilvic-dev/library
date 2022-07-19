package com.vdev.library.rest.controller;

import com.vdev.library.rest.controller.model.RestResponse;
import com.vdev.library.rest.exception.BadRequestException;
import com.vdev.library.rest.exception.NoContentException;
import com.vdev.library.rest.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@ControllerAdvice
public class RestControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<RestResponse<String>> notFoundException(final NotFoundException notFoundException, final HttpServletRequest httpServletRequest) {
        log.info("Exception {} caused by {}", notFoundException, httpServletRequest.getRequestURI());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NoContentException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public ResponseEntity<RestResponse<String>> noContentException(final NoContentException noContentException, final HttpServletRequest httpServletRequest) {
        log.info("Exception {} caused by {}", noContentException, httpServletRequest.getRequestURI());
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public ResponseEntity<RestResponse<String>> badRequestException(final BadRequestException badRequestException, final HttpServletRequest httpServletRequest) {
        final var bindingResult = badRequestException.getBindingResult();
        String error = null;
        final var restResponse = new RestResponse<String>();

        if (badRequestException != null) {
            error = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
            restResponse.addErrors(bindingResult, messageSource);
        }
        log.info("Exception {} caused by {} error {}", badRequestException, httpServletRequest.getRequestURI(), error);
        return ResponseEntity.badRequest().body(restResponse);
    }

}

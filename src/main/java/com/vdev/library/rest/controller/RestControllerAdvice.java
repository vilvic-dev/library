package com.vdev.library.rest.controller;

import com.vdev.library.rest.controller.model.RestResponse;
import com.vdev.library.rest.exception.NoContentException;
import com.vdev.library.rest.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class RestControllerAdvice {

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

}

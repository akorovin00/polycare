package de.fraunhofer.polycare.controllers.advice;

import de.fraunhofer.polycare.exception.ResourceNotFoundException;
import de.fraunhofer.polycare.models.RestErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by aw3s0 on 8/26/2017.
 * Rest Handler with custom exception handlers
 * that intercepts all exceptions and returns a custom exception response
 */
@Slf4j
@ControllerAdvice
public class AdviceRestHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ DataIntegrityViolationException.class, PropertyValueException.class })
    @ResponseBody
    public RestErrorInfo handleDataStoreException(Exception ex, WebRequest request, HttpServletResponse response) {
        log.info("Violation of hibernate constraints : " + ex.getMessage());

        return new RestErrorInfo(ex, "The data is not valid");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public RestErrorInfo handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request, HttpServletResponse response) {
        log.info("ResourceNotFoundException handler:" + ex.getMessage());

        return new RestErrorInfo(ex, "Sorry the resource was not found.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ DuplicateKeyException.class, EntityExistsException.class })
    @ResponseBody
    public RestErrorInfo handleDuplicateKeyException(Exception ex, WebRequest request, HttpServletResponse response) {
        log.info("DuplicateKeyException handler:" + ex.getMessage());

        return new RestErrorInfo(ex, "Sorry the resource already exists.");
    }
}

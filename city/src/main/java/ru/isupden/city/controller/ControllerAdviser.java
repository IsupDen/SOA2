package ru.isupden.city.controller;

import java.util.List;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.isupden.city.controller.dto.Error;
import ru.isupden.city.controller.dto.FieldValidationError;
import ru.isupden.city.exception.EntityNotFoundException;

@Slf4j
@ControllerAdvice
public class ControllerAdviser {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class,
            PropertyReferenceException.class,
            DataAccessException.class,
    })
    public ResponseEntity<Error> handleValidationExceptions(Throwable ex) {
        log.error(ex.getMessage(), ex);
        Error error;
        if (ex instanceof MethodArgumentNotValidException notValidException) {
            error = Error.newBuilder()
                    .setMessage("Validation failed")
                    .setFields(notValidException.getBindingResult().getAllErrors().stream()
                            .map(e -> new FieldValidationError(((FieldError) e).getField(), e.getDefaultMessage()))
                            .toList())
                    .build();
        }
        else if (ex instanceof ConstraintViolationException violationException) {
            error = Error.newBuilder()
                    .setMessage("Validation failed")
                    .setFields(violationException.getConstraintViolations().stream()
                            .map(e -> {
                                var fields = e.getPropertyPath().toString().split("\\.");
                                return new FieldValidationError(fields[fields.length - 1], e.getMessage());
                            })
                            .toList())
                    .build();
        }
        else if (ex instanceof MethodArgumentTypeMismatchException typeMismatchException) {
            error = Error.newBuilder()
                    .setMessage("Validation failed")
                    .setFields(List.of(new FieldValidationError(typeMismatchException.getName(), "Invalid type")))
                    .build();
        }
        else if (ex instanceof PropertyReferenceException propertyReferenceException) {
            error = Error.newBuilder()
                    .setMessage("Validation failed")
                    .setFields(List.of(new FieldValidationError(propertyReferenceException.getPropertyName(), propertyReferenceException.getMessage())))
                    .build();
        }
        else if (ex instanceof DataAccessException) {
            error = Error.newBuilder()
                    .setMessage(((DataAccessException) ex).getMostSpecificCause().getMessage())
                    .build();
        }
        else {
            error = Error.newBuilder()
                    .setMessage(ex.getMessage())
                    .build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_XML).body(error);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Error> handleNotFoundExceptions(EntityNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Spring-Source", "Spring-Service").contentType(MediaType.APPLICATION_XML).body(
                Error.newBuilder().setMessage(ex.getMessage()).build()
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Error> handleDefaultExceptions(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_XML).body(
                Error.newBuilder().setMessage("Unexpected error").build()
        );
    }
}

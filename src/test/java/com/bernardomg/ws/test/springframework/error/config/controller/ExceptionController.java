
package com.bernardomg.ws.test.springframework.error.config.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.exception.MissingIdException;
import com.bernardomg.validation.domain.exception.FieldFailureException;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.ws.test.springframework.error.config.model.ErrorTestObject;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ExceptionController.PATH)
public class ExceptionController {

    public static final String PATH                            = "/exception";

    public static final String PATH_FIELD_EXCEPTION_VALIDATION = PATH + "/field";

    public static final String PATH_ILLEGAL_ARGUMENT           = PATH + "/illegalArgument";

    public static final String PATH_METHOD_ARG                 = PATH + "/methodArg";

    public static final String PATH_MISSING_ID                 = PATH + "/missingId";

    public static final String PATH_RUNTIME                    = PATH + "/runtime";

    public static final String PATH_TYPE_MISMATCH              = PATH + "/typeMismatch";

    public static final String PATH_UNHANDLED_SPRING           = PATH + "/unhandledSpring";

    public ExceptionController() {
        super();
    }

    @GetMapping(path = "/field", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exceptionFieldValidation() {
        final FieldFailure             failure;
        final Collection<FieldFailure> failures;

        failure = new FieldFailure("code", "field", "Error message", "value");

        failures = new ArrayList<>();
        failures.add(failure);

        throw new FieldFailureException("", failures);
    }

    @PostMapping(path = "/methodArg", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorTestObject exceptionMethodArg(@Valid @RequestBody final ErrorTestObject arg) {
        return arg;
    }

    @GetMapping(path = "/illegalArgument", produces = MediaType.APPLICATION_JSON_VALUE)
    public void illegalArgument() {
        throw new IllegalArgumentException();
    }

    @GetMapping(path = "/missingId", produces = MediaType.APPLICATION_JSON_VALUE)
    public void missingId() {
        throw new MissingIdException("source", 1L);
    }

    @GetMapping(path = "/runtime", produces = MediaType.APPLICATION_JSON_VALUE)
    public void runtimeException() {
        throw new RuntimeException("Error message");
    }

    @GetMapping(path = "/typeMismatch", produces = MediaType.APPLICATION_JSON_VALUE)
    public void typeMismatch() {
        throw new TypeMismatchException("abc", Long.class);
    }

    @GetMapping(path = "/unhandledSpring", produces = MediaType.APPLICATION_JSON_VALUE)
    public void unhandledSpring() {
        throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

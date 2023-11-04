
package com.bernardomg.web.error.test.util.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;
import com.bernardomg.web.error.test.util.model.ErrorTestObject;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ExceptionController.PATH)
public class ExceptionController {

    public static final String PATH                            = "/test/exception";

    public static final String PATH_FIELD_EXCEPTION_VALIDATION = PATH + "/field";

    public static final String PATH_METHOD_ARG                 = PATH + "/methodArg";

    public static final String PATH_RUNTIME                    = PATH + "/runtime";

    public ExceptionController() {
        super();
    }

    @GetMapping(path = "/field", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> exceptionFieldValidation() {
        final FieldFailure             failure;
        final Collection<FieldFailure> failures;

        failure = FieldFailure.of("Error message", "field", "code", "value");

        failures = new ArrayList<>();
        failures.add(failure);

        throw new FieldFailureException(failures);
    }

    @PostMapping(path = "/methodArg", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorTestObject exceptionMethodArg(@Valid @RequestBody final ErrorTestObject arg) {
        return arg;
    }

    @GetMapping(path = "/runtime", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> runtimeException() {
        throw new RuntimeException("Error message");
    }

}

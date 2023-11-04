
package com.bernardomg.web.error.test.util.controller;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public final class TestExceptionRequest {

    public static final RequestBuilder fieldValidation() {
        return MockMvcRequestBuilders.get(ExceptionController.PATH_FIELD_EXCEPTION_VALIDATION)
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder methodArgument() {
        return MockMvcRequestBuilders.post(ExceptionController.PATH_METHOD_ARG)
            .content("{}")
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder runtime() {
        return MockMvcRequestBuilders.get(ExceptionController.PATH_RUNTIME)
            .contentType(MediaType.APPLICATION_JSON);
    }

    private TestExceptionRequest() {
        super();
    }

}

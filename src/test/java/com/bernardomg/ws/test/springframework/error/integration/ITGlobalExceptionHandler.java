/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023-2025 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.ws.test.springframework.error.integration;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bernardomg.ws.test.springframework.error.config.ExceptionHandlerTestConfig;
import com.bernardomg.ws.test.springframework.error.config.controller.ExceptionController;
import com.bernardomg.ws.test.springframework.error.config.controller.TestExceptionRequest;

@WebMvcTest(ExceptionController.class)
@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ExceptionHandlerTestConfig.class)
@DisplayName("Global exception handler")
class ITGlobalExceptionHandler {

    @Autowired
    private MockMvc mockMvc;

    public ITGlobalExceptionHandler() {
        super();
    }

    @Test
    @DisplayName("With a field validation exception it returns a failures list")
    void testErrorHandling_FieldValidationError() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestExceptionRequest.fieldValidation());

        // THEN

        // The operation was rejected
        result.andExpect(status().isBadRequest());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.failures", aMapWithSize(1)));
        result.andExpect(jsonPath("$.failures", hasKey("field")));
        result.andExpect(jsonPath("$.failures['field']", hasSize(1)));
        result.andExpect(jsonPath("$.failures['field'][0].message", equalTo("Error message")));
        result.andExpect(jsonPath("$.failures['field'][0].field", equalTo("field")));
        result.andExpect(jsonPath("$.failures['field'][0].code", equalTo("code")));
        result.andExpect(jsonPath("$.failures['field'][0].value", equalTo("value")));

        // The response contains no generic error fields
        result.andExpect(jsonPath("$.code", equalTo("400")));
        result.andExpect(jsonPath("$.message", equalTo("Field validation failure")));

        // The response contains no content field
        result.andExpect(jsonPath("$.content").doesNotExist());
    }

    @Test
    @DisplayName("With an illegal argument exception it returns the generic error response")
    void testErrorHandling_IllegalArgument() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestExceptionRequest.illegalArgument());

        // THEN

        // The value was not found
        result.andExpect(status().isBadRequest());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.code", equalTo("400")));
        result.andExpect(jsonPath("$.message", equalTo("Bad request")));

        // The response contains no content field
        result.andExpect(jsonPath("$.content").doesNotExist());

        // The response contains no failures attribute
        result.andExpect(jsonPath("$.failures").doesNotExist());
    }

    @Test
    @DisplayName("With a method argument exception it returns the failures response")
    void testErrorHandling_MethodArgumentError() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestExceptionRequest.methodArgument());

        // THEN

        // The operation was rejected
        result.andExpect(status().isBadRequest());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.failures", aMapWithSize(1)));
        result.andExpect(jsonPath("$.failures", hasKey("name")));
        result.andExpect(jsonPath("$.failures['name']", hasSize(1)));
        result.andExpect(jsonPath("$.failures['name'][0].field", equalTo("name")));
        result.andExpect(jsonPath("$.failures['name'][0].value").doesNotExist());
        result.andExpect(jsonPath("$.failures['name'][0].code", equalTo("empty")));
        result.andExpect(jsonPath("$.failures['name'][0].message", equalTo("must not be null")));

        // The response contains no generic error fields
        result.andExpect(jsonPath("$.code", equalTo("400")));
        result.andExpect(jsonPath("$.message", equalTo("Field validation failure")));

        // The response contains no content field
        result.andExpect(jsonPath("$.content").doesNotExist());
    }

    @Test
    @DisplayName("With a missing id exception it returns the generic error response")
    void testErrorHandling_MissingId() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestExceptionRequest.missingId());

        // THEN

        // The value was not found
        result.andExpect(status().isNotFound());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.code", equalTo("404")));
        result.andExpect(jsonPath("$.message", equalTo("Id 1 not found")));

        // The response contains no content field
        result.andExpect(jsonPath("$.content").doesNotExist());

        // The response contains no failures attribute
        result.andExpect(jsonPath("$.failures").doesNotExist());
    }

    @Test
    @DisplayName("With a runtime exception it returns the generic error response")
    void testErrorHandling_RuntimeException() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestExceptionRequest.runtime());

        // THEN

        // The operation was rejected
        result.andExpect(status().isInternalServerError());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.code", equalTo("500")));
        result.andExpect(jsonPath("$.message", equalTo("Internal error")));

        // The response contains no content field
        result.andExpect(jsonPath("$.content").doesNotExist());

        // The response contains no failures attribute
        result.andExpect(jsonPath("$.failures").doesNotExist());
    }

    @Test
    @DisplayName("With a type mismatch exception it returns the generic error response")
    void testErrorHandling_TypeMismatch() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestExceptionRequest.typeMismatch());

        // THEN

        // The value was not found
        result.andExpect(status().isBadRequest());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.code", equalTo("400")));
        result.andExpect(jsonPath("$.message", equalTo("Bad request")));

        // The response contains no content field
        result.andExpect(jsonPath("$.content").doesNotExist());

        // The response contains no failures attribute
        result.andExpect(jsonPath("$.failures").doesNotExist());
    }

    @Test
    @DisplayName("With an unhandled Spring exception it returns the generic error response")
    void testErrorHandling_UnhandledSpring() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestExceptionRequest.unhandledSpring());

        // THEN

        // The operation was rejected
        result.andExpect(status().isInternalServerError());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.code", equalTo("500")));
        result.andExpect(jsonPath("$.message", equalTo("Server error. Contact admin.")));

        // The response contains no content field
        result.andExpect(jsonPath("$.content").doesNotExist());

        // The response contains no failures attribute
        result.andExpect(jsonPath("$.failures").doesNotExist());
    }

}

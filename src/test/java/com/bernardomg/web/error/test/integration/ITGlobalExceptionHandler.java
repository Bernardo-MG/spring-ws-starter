/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.web.error.test.integration;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bernardomg.test.config.annotation.MvcIntegrationTest;
import com.bernardomg.web.error.test.util.controller.TestExceptionRequest;

@MvcIntegrationTest
@AutoConfigureMockMvc
@DisplayName("Global exception handler")
class ITGlobalExceptionHandler {

    @Autowired
    private MockMvc mockMvc;

    public ITGlobalExceptionHandler() {
        super();
    }

    @Test
    @DisplayName("With a field validation exception it returns a failures list")
    void testErrorHandling_FieldValidationError_Response() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(TestExceptionRequest.fieldValidation());

        // The operation was rejected
        result.andExpect(MockMvcResultMatchers.status()
            .isBadRequest());

        // The response contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures", Matchers.aMapWithSize(1)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures", Matchers.hasKey("field")));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures['field']", Matchers.hasSize(1)));
        result.andExpect(
            MockMvcResultMatchers.jsonPath("$.failures['field'][0].message", Matchers.equalTo("Error message")));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures['field'][0].field", Matchers.equalTo("field")));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures['field'][0].code", Matchers.equalTo("code")));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures['field'][0].value", Matchers.equalTo("value")));

        // The response contains no generic error fields
        result.andExpect(MockMvcResultMatchers.jsonPath("$.code")
            .doesNotExist());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.message")
            .doesNotExist());

        // The response contains no content field
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content")
            .doesNotExist());
    }

    @Test
    @DisplayName("With an illegal argument exception it returns the generic error response")
    void testErrorHandling_IllegalArgument_Response() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(TestExceptionRequest.illegalArgument());

        // The value was not found
        result.andExpect(MockMvcResultMatchers.status()
            .isBadRequest());

        // The response contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.equalTo("Bad request")));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo("Bad request")));

        // The response contains no content field
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content")
            .doesNotExist());

        // The response contains no failures attribute
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures")
            .doesNotExist());
    }

    @Test
    @DisplayName("With a method argument exception it returns the failures response")
    void testErrorHandling_MethodArgumentError_Response() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(TestExceptionRequest.methodArgument());

        // The operation was rejected
        result.andExpect(MockMvcResultMatchers.status()
            .isBadRequest());

        // The response contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures", Matchers.aMapWithSize(1)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures", Matchers.hasKey("name")));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures['name']", Matchers.hasSize(1)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures['name'][0].field", Matchers.equalTo("name")));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures['name'][0].value")
            .doesNotExist());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures['name'][0].code", Matchers.equalTo("empty")));
        result.andExpect(
            MockMvcResultMatchers.jsonPath("$.failures['name'][0].message", Matchers.equalTo("must not be null")));

        // The response contains no generic error fields
        result.andExpect(MockMvcResultMatchers.jsonPath("$.code")
            .doesNotExist());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.message")
            .doesNotExist());

        // The response contains no content field
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content")
            .doesNotExist());
    }

    @Test
    @DisplayName("With a missing id exception it returns the generic error response")
    void testErrorHandling_MissingId_Response() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(TestExceptionRequest.missingId());

        // The value was not found
        result.andExpect(MockMvcResultMatchers.status()
            .isNotFound());

        // The response contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.equalTo("idNotFound")));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo("Id 1 not found")));

        // The response contains no content field
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content")
            .doesNotExist());

        // The response contains no failures attribute
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures")
            .doesNotExist());
    }

    @Test
    @DisplayName("With a runtime exception it returns the generic error response")
    void testErrorHandling_RuntimeException_Response() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(TestExceptionRequest.runtime());

        // The operation was rejected
        result.andExpect(MockMvcResultMatchers.status()
            .isInternalServerError());

        // The response contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.equalTo("Internal error")));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo("Internal error")));

        // The response contains no content field
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content")
            .doesNotExist());

        // The response contains no failures attribute
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures")
            .doesNotExist());
    }

    @Test
    @DisplayName("With a type mismatch exception it returns the generic error response")
    void testErrorHandling_TypeMismatch_Response() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(TestExceptionRequest.typeMismatch());

        // The value was not found
        result.andExpect(MockMvcResultMatchers.status()
            .isBadRequest());

        // The response contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.equalTo("Bad request")));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo("Bad request")));

        // The response contains no content field
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content")
            .doesNotExist());

        // The response contains no failures attribute
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures")
            .doesNotExist());
    }

    @Test
    @DisplayName("With an unhandled Spring exception it returns the generic error response")
    void testErrorHandling_UnhandledSpring_Response() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(TestExceptionRequest.unhandledSpring());

        // The operation was rejected
        result.andExpect(MockMvcResultMatchers.status()
            .isInternalServerError());

        // The response contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.equalTo("500")));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo("Server error. Contact admin.")));

        // The response contains no content field
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content")
            .doesNotExist());

        // The response contains no failures attribute
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures")
            .doesNotExist());
    }

}

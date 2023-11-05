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

package com.bernardomg.ws.test.response.integration;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bernardomg.ws.test.config.annotation.IntegrationTest;
import com.bernardomg.ws.test.response.util.controller.TestResponseRequest;

@IntegrationTest
@AutoConfigureMockMvc
@DisplayName("Response wrapping handler")
class ITResponseWrappingHandler {

    @Autowired
    private MockMvc mockMvc;

    public ITResponseWrappingHandler() {
        super();
    }

    @Test
    @DisplayName("With an error response wrapper it doesn't change")
    void testResponseWrapping_ErrorResponse() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(TestResponseRequest.errorResponse());

        // The value was not found
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The response contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.equalTo("code")));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo("message")));
    }

    @Test
    @DisplayName("With a failure response wrapper it doesn't change")
    void testResponseWrapping_FailureResponse() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(TestResponseRequest.failureResponse());

        // The value was not found
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The response contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures", Matchers.aMapWithSize(1)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures", Matchers.hasKey("field")));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures['field']", Matchers.hasSize(1)));
        result.andExpect(
            MockMvcResultMatchers.jsonPath("$.failures['field'][0].message", Matchers.equalTo("Error message")));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures['field'][0].field", Matchers.equalTo("field")));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures['field'][0].code", Matchers.equalTo("code")));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.failures['field'][0].value", Matchers.equalTo("value")));
    }

    @Test
    @DisplayName("With a null response it gets wrapped into a response structure")
    @Disabled("This doesn't work")
    void testResponseWrapping_Null() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(TestResponseRequest.nullResponse());

        // The value was not found
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The response contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.equalTo("")));
    }

    @Test
    @DisplayName("With a response wrapper it doesn't change")
    @Disabled("Something is misconfigured")
    void testResponseWrapping_Response() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(TestResponseRequest.response());

        // The value was not found
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The response contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.equalTo("abc")));
    }

    @Test
    @DisplayName("With a spring page response it gets wrapped into a response structure")
    @Disabled("Something is misconfigured")
    void testResponseWrapping_SpringPage() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(TestResponseRequest.springPage());

        // The value was not found
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The response contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.equalTo(List.of("abc"))));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.elementsInPage", Matchers.equalTo(1)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.page", Matchers.equalTo(0)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.size", Matchers.equalTo(1)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.equalTo(1)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.totalPages", Matchers.equalTo(1)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.first", Matchers.equalTo(true)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.last", Matchers.equalTo(true)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.sort", Matchers.equalTo(List.of())));

    }

    @Test
    @DisplayName("With a string response it gets wrapped into a response structure")
    @Disabled("This doesn't work")
    void testResponseWrapping_String() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(TestResponseRequest.string());

        // The value was not found
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The response contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.equalTo("abc")));
    }

}

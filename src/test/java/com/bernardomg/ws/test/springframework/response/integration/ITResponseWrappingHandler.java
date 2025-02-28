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

package com.bernardomg.ws.test.springframework.response.integration;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bernardomg.ws.test.springframework.response.config.ResponseWrappingTestConfig;
import com.bernardomg.ws.test.springframework.response.config.controller.ResponseController;
import com.bernardomg.ws.test.springframework.response.config.controller.TestResponseRequest;

@WebMvcTest(ResponseController.class)
@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ResponseWrappingTestConfig.class)
@DisplayName("Response wrapping handler")
class ITResponseWrappingHandler {

    @Autowired
    private MockMvc mockMvc;

    public ITResponseWrappingHandler() {
        super();
    }

    @Test
    @DisplayName("With an error response wrapper, it doesn't wrap the response")
    void testResponseWrapping_ErrorResponse() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestResponseRequest.errorResponse());

        // THEN
        result.andExpect(status().isOk());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.code", equalTo("code")));
        result.andExpect(jsonPath("$.message", equalTo("message")));
    }

    @Test
    @DisplayName("With a failure response wrapper, it doesn't wrap the response")
    void testResponseWrapping_FailureResponse() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestResponseRequest.failureResponse());

        // THEN
        result.andExpect(status().isOk());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.failures", aMapWithSize(1)));
        result.andExpect(jsonPath("$.failures", hasKey("field")));
        result.andExpect(jsonPath("$.failures['field']", hasSize(1)));
        result.andExpect(jsonPath("$.failures['field'][0].message", equalTo("Error message")));
        result.andExpect(jsonPath("$.failures['field'][0].field", equalTo("field")));
        result.andExpect(jsonPath("$.failures['field'][0].code", equalTo("code")));
        result.andExpect(jsonPath("$.failures['field'][0].value", equalTo("value")));
    }

    @Test
    @DisplayName("With a null response, nothing is returned")
    void testResponseWrapping_Null() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestResponseRequest.nullResponse());

        // THEN
        result.andExpect(status().isOk());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.content", nullValue()));
    }

    @Test
    @DisplayName("With an object response, it gets wrapped into a response structure")
    void testResponseWrapping_Object() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestResponseRequest.object());

        // THEN
        result.andExpect(status().isOk());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.content.name", equalTo("name")));
    }

    @Test
    @DisplayName("With a paginated response wrapper, it doesn't wrap the response")
    void testResponseWrapping_PaginatedResponse() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestResponseRequest.paginatedResponse());

        // THEN
        result.andExpect(status().isOk());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.content", equalTo("abc")));
    }

    @Test
    @DisplayName("With a resource, it doesn't wrap the response")
    void testResponseWrapping_Resource() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestResponseRequest.resource());

        // THEN
        result.andExpect(status().isOk());
    }

    @Test
    @DisplayName("With a response wrapper, it doesn't wrap the response")
    void testResponseWrapping_Response() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestResponseRequest.response());

        // THEN
        result.andExpect(status().isOk());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.content", equalTo("abc")));
    }

    @Test
    @DisplayName("With a response entity, it doesn't wrap the response")
    @Disabled("Check why this is not working")
    void testResponseWrapping_ResponseEntity() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestResponseRequest.responseEntity());

        // THEN
        result.andExpect(status().isOk());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.content", equalTo("abc")));
    }

    @Test
    @DisplayName("With a spring page response, it gets wrapped into a response structure")
    void testResponseWrapping_SpringPage() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestResponseRequest.springPage());

        // THEN
        result.andExpect(status().isOk());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.content", equalTo(List.of("abc"))));
        result.andExpect(jsonPath("$.elementsInPage", equalTo(1)));
        result.andExpect(jsonPath("$.page", equalTo(1)));
        result.andExpect(jsonPath("$.size", equalTo(1)));
        result.andExpect(jsonPath("$.totalElements", equalTo(1)));
        result.andExpect(jsonPath("$.totalPages", equalTo(1)));
        result.andExpect(jsonPath("$.first", equalTo(true)));
        result.andExpect(jsonPath("$.last", equalTo(true)));
        result.andExpect(jsonPath("$.sort.properties", equalTo(List.of())));
    }

    @Test
    @DisplayName("With a spring page response which is sorted, it gets wrapped into a response structure")
    void testResponseWrapping_SpringPageSorted() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestResponseRequest.springPageSorted());

        // THEN
        result.andExpect(status().isOk());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.content", equalTo(List.of("abc"))));
        result.andExpect(jsonPath("$.elementsInPage", equalTo(1)));
        result.andExpect(jsonPath("$.page", equalTo(1)));
        result.andExpect(jsonPath("$.size", equalTo(1)));
        result.andExpect(jsonPath("$.totalElements", equalTo(1)));
        result.andExpect(jsonPath("$.totalPages", equalTo(1)));
        result.andExpect(jsonPath("$.first", equalTo(true)));
        result.andExpect(jsonPath("$.last", equalTo(true)));
        result.andExpect(jsonPath("$.sort.properties", hasSize(1)));
        result.andExpect(jsonPath("$.sort.properties[0].name", equalTo("field")));
        result.andExpect(jsonPath("$.sort.properties[0].direction", equalTo("ASC")));
    }

    @Test
    @DisplayName("With a string response, it gets wrapped into a response structure")
    @Disabled("Check why this is not working")
    void testResponseWrapping_String() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestResponseRequest.string());

        // THEN
        result.andExpect(status().isOk());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.content", equalTo("abc")));
    }

    @Test
    @DisplayName("With a void response, nothing is returned")
    void testResponseWrapping_Void() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestResponseRequest.voidResponse());

        // THEN
        result.andExpect(status().isOk());

        // The response contains the expected attributes
        result.andExpect(jsonPath("$.content", nullValue()));
    }

}

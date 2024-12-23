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

package com.bernardomg.ws.test.springframework.request.integration;

import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bernardomg.ws.test.config.annotation.IntegrationTest;
import com.bernardomg.ws.test.springframework.request.config.controller.PaginationController.PaginationReceiver;
import com.bernardomg.ws.test.springframework.request.config.controller.TestPaginationRequest;

@IntegrationTest
@AutoConfigureMockMvc
@DisplayName("Pagination argument resolver")
class ITPaginationArgumentResolver {

    @Autowired
    private MockMvc            mockMvc;

    @Autowired
    private PaginationReceiver paginationReceiver;

    public ITPaginationArgumentResolver() {
        super();
    }

    @Test
    @DisplayName("With the pagination params are negative, it parses the default pagination")
    void testPagination_NegativePagination() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestPaginationRequest.negativePagination());

        // THEN
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // Received the pagination
        verify(paginationReceiver).receive(assertArg(p -> SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(p.page())
                .as("page")
                .isEqualTo(TestPaginationRequest.DEFAULT_PAGE);
            soft.assertThat(p.size())
                .as("size")
                .isEqualTo(TestPaginationRequest.DEFAULT_SIZE);
        })));
    }

    @Test
    @DisplayName("With no pagination params, it parses the default pagination")
    void testPagination_NoPagination() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestPaginationRequest.noPagination());

        // THEN
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // Received the pagination
        verify(paginationReceiver).receive(assertArg(p -> SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(p.page())
                .as("page")
                .isEqualTo(TestPaginationRequest.DEFAULT_PAGE);
            soft.assertThat(p.size())
                .as("size")
                .isEqualTo(TestPaginationRequest.DEFAULT_SIZE);
        })));
    }

    @Test
    @DisplayName("With a pagination request, it parses the pagination")
    void testPagination_Pagination() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestPaginationRequest.pagination());

        // THEN
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // Received the pagination
        verify(paginationReceiver).receive(assertArg(p -> SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(p.page())
                .as("page")
                .isEqualTo(TestPaginationRequest.PAGE);
            soft.assertThat(p.size())
                .as("size")
                .isEqualTo(TestPaginationRequest.SIZE);
        })));
    }

}

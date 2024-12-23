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

import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ws.test.config.annotation.IntegrationTest;
import com.bernardomg.ws.test.springframework.request.config.controller.SortingController.SortingReceiver;
import com.bernardomg.ws.test.springframework.request.config.controller.TestSortingRequest;

@IntegrationTest
@AutoConfigureMockMvc
@DisplayName("Sorting argument resolver")
class ITSortingArgumentResolver {

    @Autowired
    private MockMvc         mockMvc;

    @Autowired
    private SortingReceiver sortingReceiver;

    @Test
    @DisplayName("With a sorting request containing multiple fields, it parses the sorting")
    void testSorting_MultipleSorting() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestSortingRequest.multipleSorting());

        // THEN
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // Received the pagination
        verify(sortingReceiver).receive(assertArg(s -> SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(s.properties())
                .as("properties")
                .containsExactly(new Property("field", Direction.ASC), new Property("field2", Direction.DESC));
        })));
    }

    @Test
    @DisplayName("With no sorting params, it parses no sorting")
    void testSorting_NoSorting() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestSortingRequest.noSorting());

        // THEN
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // Received the pagination
        verify(sortingReceiver).receive(assertArg(s -> SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(s.properties())
                .as("properties")
                .isEmpty();
        })));
    }

    @Test
    @DisplayName("With a sorting request, it parses the sorting")
    void testSorting_Sorting() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestSortingRequest.singleSorting());

        // THEN
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // Received the pagination
        verify(sortingReceiver).receive(assertArg(s -> SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(s.properties())
                .as("properties")
                .containsExactly(new Property("field", Direction.ASC));
        })));
    }

    @Test
    @DisplayName("With a sorting request with invalid direction, it parses the sorting")
    void testSorting_SortingInvalidDirection() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestSortingRequest.invalidDirection());

        // THEN
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // Received the pagination
        verify(sortingReceiver).receive(assertArg(s -> SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(s.properties())
                .as("properties")
                .containsExactly(new Property("field", Direction.ASC));
        })));
    }

    @Test
    @DisplayName("With a sorting request missing direction, it parses nothing")
    void testSorting_SortingMissingDirection() throws Exception {
        final ResultActions result;

        // WHEN
        result = mockMvc.perform(TestSortingRequest.missingDirection());

        // THEN
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // Received the pagination
        verify(sortingReceiver).receive(assertArg(s -> SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(s.properties())
                .as("properties")
                .isEmpty();
        })));
    }

}

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

package com.bernardomg.data.test.springframework.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringSorting;

@DisplayName("Spring sorting")
class TestSpringSorting {

    @Test
    @DisplayName("With ascending sorting, it creates an ascending sort")
    void testToSort_Ascending() throws Exception {
        final Sorting sorting;
        final Sort    sort;

        // GIVEN
        sorting = Sorting.asc("field");

        // WHEN
        sort = SpringSorting.toSort(sorting);

        // THEN
        Assertions.assertThat(sort)
            .as("sort")
            .containsExactly(Order.asc("field"));
    }

    @Test
    @DisplayName("With empty sorting, it creates an empty sort")
    void testToSort_Empty() throws Exception {
        final Sorting sorting;
        final Sort    sort;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        sort = SpringSorting.toSort(sorting);

        // THEN
        Assertions.assertThat(sort)
            .as("sort")
            .isEmpty();
    }

}

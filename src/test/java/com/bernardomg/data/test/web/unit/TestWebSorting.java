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

package com.bernardomg.data.test.web.unit;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.data.web.WebSorting;

@DisplayName("Web sorting tools")
class TestWebSorting {

    @Test
    @DisplayName("With ascending sort, it creates an ascending sorting")
    void testToSort_Ascending() {
        final Sorting      sorting;
        final List<String> sort;

        // GIVEN
        sort = List.of("field|asc");

        // WHEN
        sorting = WebSorting.toSorting(sort);

        // THEN
        Assertions.assertThat(sorting)
            .as("sorting")
            .extracting(Sorting::properties)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Property.asc("field"));
    }

    @Test
    @DisplayName("With ascending sort, it creates a descending sorting")
    void testToSort_Descending() {
        final Sorting      sorting;
        final List<String> sort;

        // GIVEN
        sort = List.of("field|desc");

        // WHEN
        sorting = WebSorting.toSorting(sort);

        // THEN
        Assertions.assertThat(sorting)
            .as("sorting")
            .extracting(Sorting::properties)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Property.desc("field"));
    }

    @Test
    @DisplayName("With no sort, it creates an empty")
    void testToSort_NoSort() {
        final Sorting      sorting;
        final List<String> sort;

        // GIVEN
        sort = List.of();

        // WHEN
        sorting = WebSorting.toSorting(sort);

        // THEN
        Assertions.assertThat(sorting)
            .as("sorting")
            .extracting(Sorting::properties)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}

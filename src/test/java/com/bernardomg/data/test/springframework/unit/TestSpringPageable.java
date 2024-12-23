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

package com.bernardomg.data.test.springframework.unit;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@DisplayName("Spring pageable")
class TestSpringPageable {

    @Test
    @DisplayName("With pagination, it creates a pageable")
    void testToPageable_Pagination() throws Exception {
        final Pagination pagination;
        final Pageable   pageable;

        // GIVEN
        pagination = new Pagination(1, 10);

        // WHEN
        pageable = SpringPagination.toPageable(pagination);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(pageable)
                .as("pageable")
                .extracting(Pageable::getPageSize)
                .as("page size")
                .isEqualTo(10);
            softly.assertThat(pageable)
                .as("pageable")
                .extracting(Pageable::getPageNumber)
                .as("page number")
                .isEqualTo(0);
        });
    }

    @Test
    @DisplayName("With pagination and sorting, it creates a pageable")
    void testToPageable_Sorting() throws Exception {
        final Pagination pagination;
        final Pageable   pageable;
        final Sorting    sorting;

        // GIVEN
        sorting = Sorting.asc("field");
        pagination = new Pagination(1, 10);

        // WHEN
        pageable = SpringPagination.toPageable(pagination, sorting);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(pageable)
                .as("pageable")
                .extracting(Pageable::getPageSize)
                .as("page size")
                .isEqualTo(10);
            softly.assertThat(pageable)
                .as("pageable")
                .extracting(Pageable::getPageNumber)
                .as("page number")
                .isEqualTo(0);
            softly.assertThat(pageable)
                .as("pageable")
                .extracting(Pageable::getSort)
                .as("sort")
                .isEqualTo(Sort.by("field"));
        });
    }

}

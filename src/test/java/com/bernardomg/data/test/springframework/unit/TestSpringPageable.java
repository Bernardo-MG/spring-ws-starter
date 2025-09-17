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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.data.springframework.SpringPagination;

@DisplayName("Spring pageable tools")
class TestSpringPageable {

    @Test
    @DisplayName("With a Spring page, it creates a page with the correct pagination")
    void testToPage_Pagination() throws Exception {
        final Page<String>                                 page;
        final Pageable                                     pageable;
        final org.springframework.data.domain.Page<String> springPage;

        // GIVEN
        pageable = Pageable.ofSize(10);
        springPage = new PageImpl<>(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), pageable, 50);

        // WHEN
        page = SpringPagination.toPage(springPage);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(page)
                .extracting(Page::size)
                .as("page size")
                .isEqualTo(10);
            softly.assertThat(page)
                .extracting(Page::page)
                .as("page number")
                .isEqualTo(1);
            softly.assertThat(page)
                .extracting(Page::totalElements)
                .as("total elements")
                .isEqualTo(50L);
            softly.assertThat(page)
                .extracting(Page::totalPages)
                .as("total pages")
                .isEqualTo(5L);
            softly.assertThat(page)
                .extracting(Page::elementsInPage)
                .as("elements in page")
                .isEqualTo(10);
            softly.assertThat(page)
                .extracting(Page::first)
                .as("first")
                .isEqualTo(true);
            softly.assertThat(page)
                .extracting(Page::last)
                .as("last")
                .isEqualTo(false);
        });
    }

    @Test
    @DisplayName("With a sorted Spring page, it creates a page with the correct sorting")
    void testToPage_Sorting() throws Exception {
        final Page<String>                                 page;
        final Pageable                                     pageable;
        final org.springframework.data.domain.Page<String> springPage;

        // GIVEN
        pageable = PageRequest.of(0, 10, org.springframework.data.domain.Sort.Direction.ASC, "field");
        springPage = new PageImpl<>(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), pageable, 50);

        // WHEN
        page = SpringPagination.toPage(springPage);

        // THEN
        assertThat(page).as("page")
            .extracting(Page::sort)
            .extracting(Sorting::properties)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .hasSize(1)
            .first()
            .asInstanceOf(InstanceOfAssertFactories.type(Property.class))
            .as("field")
            .returns("field", Property::name)
            .as("direction")
            .extracting(Property::direction)
            .isEqualTo(Direction.ASC);
    }

    @Test
    @DisplayName("With pagination, it creates a pageable with the correct pagination")
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
                .extracting(Pageable::getPageSize)
                .as("page size")
                .isEqualTo(10);
            softly.assertThat(pageable)
                .extracting(Pageable::getPageNumber)
                .as("page number")
                .isEqualTo(0);
        });
    }

    @Test
    @DisplayName("With pagination and sorting, it creates a pageable with the correct sorting")
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
                .extracting(Pageable::getSort)
                .as("sort")
                .isEqualTo(Sort.by("field"));
        });
    }

}

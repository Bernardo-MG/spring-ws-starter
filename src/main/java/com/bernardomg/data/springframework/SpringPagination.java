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

package com.bernardomg.data.springframework;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.domain.Sorting.Property;

/**
 * Utilities to transform {@link Pagination} into {@link Pageable}.
 * <p>
 * Spring pages start with index 0, so the page number has to be corrected.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class SpringPagination {

    public static final <T> Page<T> toPage(final org.springframework.data.domain.Page<T> page) {
        List<Property> properties;

        properties = page.getSort()
            .stream()
            .map(SpringSorting::toProperty)
            .toList();
        return new Page<>(page.getContent(), page.getSize(), page.getNumber() + 1, page.getTotalElements(),
            page.getTotalPages(), page.getNumberOfElements(), page.isFirst(), page.isLast(), new Sorting(properties));
    }

    public static final Pageable toPageable(final Pagination pagination) {
        final Pageable pageable;

        if (pagination.paged()) {
            pageable = PageRequest.of(pagination.page() - 1, pagination.size());
        } else {
            pageable = Pageable.unpaged();
        }

        return pageable;
    }

    public static final Pageable toPageable(final Pagination pagination, final Sorting sorting) {
        final Sort     sort;
        final Pageable pageable;

        sort = SpringSorting.toSort(sorting);
        if (pagination.paged()) {
            pageable = PageRequest.of(pagination.page() - 1, pagination.size(), sort);
        } else {
            pageable = Pageable.unpaged(sort);
        }

        return pageable;
    }

    private SpringPagination() {
        super();
    }

}

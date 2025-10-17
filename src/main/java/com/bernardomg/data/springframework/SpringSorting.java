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

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;

/**
 * Utilities to transform {@link Sorting} into {@link Sort}.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class SpringSorting {

    public static final Property toProperty(final Order order) {
        Direction direction;

        if (order.isAscending()) {
            direction = Direction.ASC;
        } else {
            direction = Direction.DESC;
        }
        return new Property(order.getProperty(), direction);
    }

    public static final Sort toSort(final Sorting sorting) {
        return Sort.by(sorting.properties()
            .stream()
            .map(SpringSorting::toOrder)
            .toList());
    }

    private static final Order toOrder(final Property property) {
        final Order order;

        if (Direction.ASC.equals(property.direction())) {
            order = Order.asc(property.name());
        } else {
            order = Order.desc(property.name());
        }

        return order;
    }

    private SpringSorting() {
        super();
    }

}

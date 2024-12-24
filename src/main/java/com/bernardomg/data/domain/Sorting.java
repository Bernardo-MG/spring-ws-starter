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

package com.bernardomg.data.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Sorting to apply when reading data.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public record Sorting(Collection<Property> properties) {

    /**
     * Unsorted sorting.
     *
     * @return an unsorted sorting
     */
    public static final Sorting unsorted() {
        return new Sorting(List.of());
    }

    /**
     * Ascending sorting.
     *
     * @param property
     *            property to sort by
     * @return ascending sorting
     */
    public static final Sorting asc(final String property) {
        return new Sorting(List.of(Property.asc(property)));
    }

    /**
     * Descending sorting.
     *
     * @param property
     *            property to sort by
     * @return descending sorting
     */
    public static final Sorting desc(final String property) {
        return new Sorting(List.of(Property.desc(property)));
    }

    /**
     * Ascending sorting.
     *
     * @param properties
     *            properties to sort by
     * @return ascending sorting
     */
    public static final Sorting asc(final String... properties) {
        return new Sorting(Arrays.asList(properties)
            .stream()
            .map(Property::asc)
            .toList());
    }

    /**
     * Property to sort by.
     */
    public record Property(String name, Direction direction) {

        /**
         * Property with ascending direction.
         *
         * @param property
         *            property to sort by
         * @return ascending property
         */
        public static final Property asc(final String property) {
            return new Property(property, Direction.ASC);
        }

        /**
         * Property with descending direction.
         *
         * @param property
         *            property to sort by
         * @return descending property
         */
        public static final Property desc(final String property) {
            return new Property(property, Direction.DESC);
        }
    }

    /**
     * Direction to sort by.
     */
    public enum Direction {
        ASC, DESC
    }

}

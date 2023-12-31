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

package com.bernardomg.web.response.model;

import java.util.List;
import java.util.Map;

import com.bernardomg.validation.failure.FieldFailure;

/**
 * Response to the frontend.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 * @param <T>
 *            response content type
 */
public interface Response<T> {

    public static <T> Response<T> empty() {
        return ImmutableResponse.<T> builder()
            .build();
    }

    public static ErrorResponse error(final String code) {
        return ImmutableErrorResponse.builder()
            .code(code)
            .message(code)
            .build();
    }

    public static ErrorResponse error(final String message, final String code) {
        return ImmutableErrorResponse.builder()
            .code(code)
            .message(message)
            .build();
    }

    public static FailureResponse failure(final Map<String, List<FieldFailure>> failures) {
        return ImmutableFailureResponse.builder()
            .failures(failures)
            .build();
    }

    public static <T> Response<T> of(final T content) {
        return ImmutableResponse.<T> builder()
            .content(content)
            .build();
    }

    /**
     * Returns the response content.
     *
     * @return the response content
     */
    public T getContent();

}

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

package com.bernardomg.web.response.domain.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.bernardomg.validation.domain.model.FieldFailure;

import lombok.Data;

/**
 * Response to the frontend.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 * @param <T>
 *            response content type
 */
@Data
public class Response<T> {

    public static <T> Response<T> empty() {
        return new Response<>();
    }

    public static ErrorResponse error(final String code) {
        return ErrorResponse.builder()
            .withCode(code)
            .withMessage(code)
            .build();
    }

    public static ErrorResponse error(final String message, final String code) {
        return ErrorResponse.builder()
            .withCode(code)
            .withMessage(message)
            .build();
    }

    public static FailureResponse failure(final Map<String, List<FieldFailure>> failures) {
        return FailureResponse.builder()
            .withFailures(failures)
            .build();
    }

    public static <T> Response<T> of(final T content) {
        return new Response<>(content);
    }

    /**
     * Response content.
     */
    private final T content;

    public Response() {
        super();

        content = null;
    }

    public Response(final T cnt) {
        super();

        content = Objects.requireNonNull(cnt);
    }

}

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

package com.bernardomg.ws.response;

import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.bernardomg.web.response.domain.model.ErrorResponse;
import com.bernardomg.web.response.domain.model.FailureResponse;
import com.bernardomg.web.response.domain.model.PaginatedResponse;
import com.bernardomg.web.response.domain.model.PropertySort;
import com.bernardomg.web.response.domain.model.Response;

import lombok.extern.slf4j.Slf4j;

/**
 * Advice to wrap all the responses into an instance of {@link Response}. Unless it is already an instance of
 * {@code Response}, or the Spring {@link ResponseEntity}.
 * <h2>Pagination</h2>
 * <p>
 * Paginated data will be wrapped into an instance of {@link PaginatedResponse}.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
// TODO: This path should be parameterized
@ControllerAdvice("com.bernardomg")
@Slf4j
public final class ResponseWrappingHandler implements ResponseBodyAdvice<Object> {

    /**
     * Default constructor.
     */
    public ResponseWrappingHandler() {
        super();
    }

    @Override
    public final Object beforeBodyWrite(final Object body, final MethodParameter returnType,
            final MediaType selectedContentType, final Class<? extends HttpMessageConverter<?>> selectedConverterType,
            final ServerHttpRequest request, final ServerHttpResponse response) {
        final Object result;

        log.trace("Received {} as response body", body);
        if ((body instanceof ResponseEntity<?>) || (body instanceof Response) || (body instanceof ErrorResponse)
                || (body instanceof FailureResponse)) {
            // Avoid wrapping wrapped responses
            result = body;
        } else if (body instanceof Resource) {
            // Avoid wrapping resources
            result = body;
        } else if (body instanceof Page<?>) {
            // Spring pagination
            result = toPaginated((Page<?>) body);
        } else if (body == null) {
            log.debug("Received null as response body");
            result = Response.empty();
        } else {
            result = Response.of(body);
        }

        return result;
    }

    private final PropertySort getPropertySort(final Order order) {
        final String direction;

        if (order.isAscending()) {
            direction = "asc";
        } else {
            direction = "desc";
        }

        return new PropertySort(order.getProperty(), direction);
    }

    @Override
    public final boolean supports(final MethodParameter returnType,
            final Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    private final <T> PaginatedResponse<Iterable<T>> toPaginated(final Page<T> page) {
        final Iterable<PropertySort> sort;

        sort = page.getSort()
            .stream()
            .map(this::getPropertySort)
            .toList();

        return PaginatedResponse.<Iterable<T>> builder()
            .withContent(page.getContent())
            .withSort(sort)
            .withElementsInPage(page.getNumberOfElements())
            .withFirst(page.isFirst())
            .withLast(page.isLast())
            .withPage(page.getNumber())
            .withSize(page.getSize())
            .withTotalElements(page.getTotalElements())
            .withTotalPages(page.getTotalPages())
            .build();
    }

}

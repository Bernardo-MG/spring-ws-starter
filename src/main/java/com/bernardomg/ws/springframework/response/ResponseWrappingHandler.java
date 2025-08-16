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

package com.bernardomg.ws.springframework.response;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ws.response.domain.model.ErrorResponse;
import com.bernardomg.ws.response.domain.model.FailureResponse;
import com.bernardomg.ws.response.domain.model.Response;

/**
 * Advice to wrap all the responses into an instance of {@link Response}. Unless it is already an instance of
 * {@code Response}, or the Spring {@link ResponseEntity}.
 * <h2>Pagination</h2>
 * <p>
 * Paginated data will be wrapped into an instance of {@link Page}.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
// TODO: This path should be parameterized
@ControllerAdvice("com.bernardomg")
public final class ResponseWrappingHandler implements ResponseBodyAdvice<Object> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(ResponseWrappingHandler.class);

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
        } else if ((body instanceof Page) || (body instanceof Resource)) {
            // Avoid wrapping pages
            result = body;
        } else if (body instanceof org.springframework.data.domain.Page<?>) {
            // Spring pagination
            result = toPaginated((org.springframework.data.domain.Page<?>) body);
        } else if (body == null) {
            log.debug("Received null as response body");
            result = Response.empty();
        } else {
            result = new Response<>(body);
        }

        return result;
    }

    @Override
    public final boolean supports(final MethodParameter returnType,
            final Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    private final Property getProperty(final Order order) {
        final Direction direction;

        if (order.isAscending()) {
            direction = Direction.ASC;
        } else {
            direction = Direction.DESC;
        }

        return new Property(order.getProperty(), direction);
    }

    private final <T> Page<T> toPaginated(final org.springframework.data.domain.Page<T> page) {
        final Sorting              sorting;
        final Collection<Property> properties;
        final Integer              pageNumber;

        properties = page.getSort()
            .stream()
            .map(this::getProperty)
            .toList();
        sorting = new Sorting(properties);

        // Spring starts pages by 0
        pageNumber = page.getNumber() + 1;
        return new Page<>(page.getContent(), page.getSize(), pageNumber, page.getTotalElements(), page.getTotalPages(),
            page.getNumberOfElements(), page.isFirst(), page.isLast(), sorting);
    }

}

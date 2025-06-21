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

package com.bernardomg.ws.springframework.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bernardomg.data.domain.Pagination;

/**
 * Argument resolver to acquire a {@link Pagination} from the request parameters. In case these parameters are missing,
 * it applies default values.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class PaginationArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int    DEFAULT_PAGE = 1;

    private static final int    DEFAULT_SIZE = 10;

    /**
     * Logger for the class.
     */
    private static final Logger log          = LoggerFactory.getLogger(PaginationArgumentResolver.class);

    /**
     * Default constructor.
     */
    public PaginationArgumentResolver() {
        super();
    }

    @Override
    public final Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final String pageParam;
        final String sizeParam;
        final int    parsedPage;
        final int    page;
        final int    parsedSize;
        final int    size;

        // Parse page
        pageParam = webRequest.getParameter("page");
        if (pageParam == null) {
            page = DEFAULT_PAGE;
            log.debug("Received no page, changed to {}", page);
        } else {
            parsedPage = Integer.parseInt(pageParam);
            if (parsedPage > 0) {
                page = parsedPage;
            } else {
                page = DEFAULT_PAGE;
                log.warn("Received page {}, changed to {}", parsedPage, page);
            }
        }

        // Parse size
        sizeParam = webRequest.getParameter("size");
        if (sizeParam == null) {
            size = DEFAULT_SIZE;
            log.debug("Received no size, changed to {}", size);
        } else {
            parsedSize = Integer.parseInt(sizeParam);
            if (parsedSize > 0) {
                size = parsedSize;
            } else {
                size = DEFAULT_SIZE;
                log.warn("Received size {}, changed to {}", parsedSize, size);
            }
        }

        return new Pagination(page, size);
    }

    @Override
    public final boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType()
            .equals(Pagination.class);
    }

}

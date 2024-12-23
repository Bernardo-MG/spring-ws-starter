
package com.bernardomg.ws.springframework.request;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bernardomg.data.domain.Pagination;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PaginationArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int DEFAULT_PAGE = 1;

    private static final int DEFAULT_SIZE = 10;

    @Override
    public final Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final String pageParam;
        final String sizeParam;
        final int    parsedPage;
        final int    page;
        final int    parsedSize;
        final int    size;

        pageParam = webRequest.getParameter("page");
        if (pageParam == null) {
            page = DEFAULT_PAGE;
        } else {
            parsedPage = Integer.parseInt(pageParam);
            if (parsedPage > 0) {
                page = parsedPage;
            } else {
                page = DEFAULT_PAGE;
                log.warn("Received page {}, changed to {}", parsedPage, page);
            }
        }

        sizeParam = webRequest.getParameter("size");
        if (sizeParam == null) {
            size = DEFAULT_SIZE;
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

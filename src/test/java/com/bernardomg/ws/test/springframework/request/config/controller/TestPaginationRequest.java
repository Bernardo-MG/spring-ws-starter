
package com.bernardomg.ws.test.springframework.request.config.controller;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public final class TestPaginationRequest {

    public static final int DEFAULT_PAGE = 1;

    public static final int DEFAULT_SIZE = 10;

    public static final int PAGE         = 2;

    public static final int SIZE         = 20;

    public static final RequestBuilder noPagination() {
        return MockMvcRequestBuilders.get(PaginationController.PATH)
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder pagination() {
        return MockMvcRequestBuilders.get(PaginationController.PATH)
            .param("page", String.valueOf(PAGE))
            .param("size", String.valueOf(SIZE))
            .contentType(MediaType.APPLICATION_JSON);
    }

    private TestPaginationRequest() {
        super();
    }

}

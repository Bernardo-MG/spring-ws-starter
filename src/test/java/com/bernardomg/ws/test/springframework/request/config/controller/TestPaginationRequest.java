
package com.bernardomg.ws.test.springframework.request.config.controller;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public final class TestPaginationRequest {

    public static int PAGE = 1;

    public static int SIZE = 10;

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

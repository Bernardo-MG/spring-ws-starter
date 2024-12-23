
package com.bernardomg.ws.test.springframework.request.config.controller;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public final class TestSortingRequest {

    public static final RequestBuilder invalidDirection() {
        return MockMvcRequestBuilders.get(SortingController.PATH)
            .param("sort", "field,abc")
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder missingDirection() {
        return MockMvcRequestBuilders.get(SortingController.PATH)
            .param("sort", "field")
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder multipleSorting() {
        return MockMvcRequestBuilders.get(SortingController.PATH)
            .param("sort", "field,asc", "field2,desc")
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder noSorting() {
        return MockMvcRequestBuilders.get(SortingController.PATH)
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder singleSorting() {
        return MockMvcRequestBuilders.get(SortingController.PATH)
            .param("sort", "field,asc")
            .contentType(MediaType.APPLICATION_JSON);
    }

    private TestSortingRequest() {
        super();
    }

}

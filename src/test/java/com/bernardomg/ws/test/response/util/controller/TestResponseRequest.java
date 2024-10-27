
package com.bernardomg.ws.test.response.util.controller;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public final class TestResponseRequest {

    public static final RequestBuilder errorResponse() {
        return MockMvcRequestBuilders.get(ResponseController.PATH_ERROR_RESPONSE)
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder failureResponse() {
        return MockMvcRequestBuilders.get(ResponseController.PATH_FAILURE_RESPONSE)
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder nullResponse() {
        return MockMvcRequestBuilders.get(ResponseController.PATH_NULL)
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder object() {
        return MockMvcRequestBuilders.get(ResponseController.PATH_OBJECT)
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder resource() {
        return MockMvcRequestBuilders.get(ResponseController.PATH_RESOURCE)
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder response() {
        return MockMvcRequestBuilders.get(ResponseController.PATH_RESPONSE)
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder paginatedResponse() {
        return MockMvcRequestBuilders.get(ResponseController.PATH_PAGINATED_RESPONSE)
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder responseEntity() {
        return MockMvcRequestBuilders.get(ResponseController.PATH_RESPONSE_ENTITY)
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder springPage() {
        return MockMvcRequestBuilders.get(ResponseController.PATH_SPRING_PAGE)
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder springPageSorted() {
        return MockMvcRequestBuilders.get(ResponseController.PATH_SPRING_PAGE_SORTED)
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder string() {
        return MockMvcRequestBuilders.get(ResponseController.PATH_STRING)
            .contentType(MediaType.APPLICATION_JSON);
    }

    public static final RequestBuilder voidResponse() {
        return MockMvcRequestBuilders.get(ResponseController.PATH_VOID)
            .contentType(MediaType.APPLICATION_JSON);
    }

    private TestResponseRequest() {
        super();
    }

}

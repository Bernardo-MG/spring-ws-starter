
package com.bernardomg.ws.test.response.util.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.web.response.model.ErrorResponse;
import com.bernardomg.web.response.model.FailureResponse;
import com.bernardomg.web.response.model.Response;

@RestController
@RequestMapping(ResponseController.PATH)
public class ResponseController {

    public static final String PATH                  = "/response";

    public static final String PATH_ERROR_RESPONSE   = PATH + "/errorResponse";

    public static final String PATH_FAILURE_RESPONSE = PATH + "/failureResponse";

    public static final String PATH_NULL             = PATH + "/null";

    public static final String PATH_RESPONSE         = PATH + "/response";

    public static final String PATH_RESPONSE_ENTITY  = PATH + "/responseEntity";

    public static final String PATH_SPRING_PAGE      = PATH + "/springPage";

    public static final String PATH_STRING           = PATH + "/string";

    public ResponseController() {
        super();
    }

    @GetMapping(path = "/errorResponse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorResponse errorResponse() {
        return Response.error("message", "code");
    }

    @GetMapping(path = "/string", produces = MediaType.APPLICATION_JSON_VALUE)
    public String exceptionFieldValidation() {
        return "abc";
    }

    @GetMapping(path = "/failureResponse", produces = MediaType.APPLICATION_JSON_VALUE)
    public FailureResponse failures() {
        final FieldFailure                    failure;
        final List<FieldFailure>              failuresList;
        final Map<String, List<FieldFailure>> failures;

        failure = FieldFailure.of("Error message", "field", "code", "value");

        failuresList = new ArrayList<>();
        failuresList.add(failure);

        failures = new HashMap<>();
        failures.put("field", failuresList);

        return Response.failure(failures);
    }

    @GetMapping(path = "/null", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<String> nullResponse() {
        return null;
    }

    @GetMapping(path = "/response", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> response() {
        return Response.of("abc");
    }

    @GetMapping(path = "/responseEntity", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> responseEntity() {
        return ResponseEntity.of(Optional.of("abc"));
    }

    @GetMapping(path = "/springPage", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageImpl<String> springPage() {
        return new PageImpl<>(List.of("abc"));
    }

}

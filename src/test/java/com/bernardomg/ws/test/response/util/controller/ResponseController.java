
package com.bernardomg.ws.test.response.util.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.web.response.domain.model.ErrorResponse;
import com.bernardomg.web.response.domain.model.FailureResponse;
import com.bernardomg.web.response.domain.model.Response;

@RestController
@RequestMapping(ResponseController.PATH)
public class ResponseController {

    public static final String PATH                    = "/response";

    public static final String PATH_ERROR_RESPONSE     = PATH + "/errorResponse";

    public static final String PATH_FAILURE_RESPONSE   = PATH + "/failureResponse";

    public static final String PATH_NULL               = PATH + "/null";

    public static final String PATH_OBJECT             = PATH + "/object";

    public static final String PATH_RESPONSE           = PATH + "/response";

    public static final String PATH_RESPONSE_ENTITY    = PATH + "/responseEntity";

    public static final String PATH_SPRING_PAGE        = PATH + "/springPage";

    public static final String PATH_SPRING_PAGE_SORTED = PATH + "/springPageSorted";

    public static final String PATH_STRING             = PATH + "/string";

    public static final String PATH_VOID               = PATH + "/void";

    public ResponseController() {
        super();
    }

    @GetMapping(path = "/errorResponse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorResponse errorResponse() {
        return new ErrorResponse("code", "message");
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

        return new FailureResponse("400", "Failure", failures);
    }

    @GetMapping(path = "/null", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<String> nullResponse() {
        return null;
    }

    @GetMapping(path = "/object", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReturnedObject object() {
        return ReturnedObject.builder()
            .withName("name")
            .build();
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

    @GetMapping(path = "/springPageSorted", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageImpl<String> springPageSorted() {
        final Pageable pageable;
        final Sort     sort;

        sort = Sort.by("field");
        pageable = Pageable.unpaged(sort);
        return new PageImpl<>(List.of("abc"), pageable, 1);
    }

    @GetMapping(path = "/void", produces = MediaType.APPLICATION_JSON_VALUE)
    public void voidResponse() {}

}


package com.bernardomg.ws.test.springframework.request.config.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.data.domain.Pagination;

@RestController
@RequestMapping(PaginationController.PATH)
public class PaginationController {

    public interface PaginationReceiver {

        public void receive(final Pagination pagination);

    }

    public static final String       PATH = "/pagination";

    private final PaginationReceiver paginationReceiver;

    public PaginationController(final PaginationReceiver receiver) {
        super();

        paginationReceiver = receiver;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void pagination(final Pagination pagination) {
        paginationReceiver.receive(pagination);
    }

}

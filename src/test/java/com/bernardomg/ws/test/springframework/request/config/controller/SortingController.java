
package com.bernardomg.ws.test.springframework.request.config.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.data.domain.Sorting;

@RestController
@RequestMapping(SortingController.PATH)
public class SortingController {

    public interface SortingReceiver {

        public void receive(final Sorting sorting);

    }

    public static final String    PATH = "/sorting";

    private final SortingReceiver sortingReceiver;

    public SortingController(final SortingReceiver receiver) {
        super();

        sortingReceiver = receiver;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void pagination(final Sorting sorting) {
        sortingReceiver.receive(sorting);
    }

}
